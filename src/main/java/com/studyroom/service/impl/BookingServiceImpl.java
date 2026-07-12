package com.studyroom.service.impl;

import com.studyroom.common.BusinessException;
import com.studyroom.dto.request.BookingRequest;
import com.studyroom.dto.response.BookingResponse;
import com.studyroom.dto.response.ViolationResponse;
import com.studyroom.entity.Booking;
import com.studyroom.entity.Classroom;
import com.studyroom.entity.Seat;
import com.studyroom.entity.User;
import com.studyroom.repository.BookingRepository;
import com.studyroom.repository.ClassroomRepository;
import com.studyroom.repository.SeatRepository;
import com.studyroom.repository.UserRepository;
import com.studyroom.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    private static final int VIOLATION_POINTS = -5;
    private static final int BLACKLIST_THRESHOLD = 3;
    private static final int BLACKLIST_DAYS = 7;

    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getMyBookings(Long studentId) {
        List<Booking> bookings = bookingRepository.findByStudentIdOrderByDateDescStartTimeDesc(studentId);
        return bookings.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Booking createBooking(Long studentId, BookingRequest request) {
        System.out.println("=== 创建预约 ===");
        System.out.println("studentId: " + studentId);
        System.out.println("seatId: " + request.getSeatId());
        System.out.println("date: " + request.getDate());
        System.out.println("startTime: " + request.getStartTime());
        System.out.println("endTime: " + request.getEndTime());

        Seat seat = seatRepository.findByIdAndDeletedFalse(request.getSeatId())
                .orElseThrow(() -> new BusinessException("座位不存在"));
        System.out.println("座位状态: " + seat.getStatus());

        if (!"AVAILABLE".equals(seat.getStatus())) {
            throw new BusinessException("座位不可用");
        }

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        System.out.println("用户信息: name=" + user.getName() + ", creditScore=" + user.getCreditScore() + ", blacklistExpire=" + user.getBlacklistExpire());

        if (user.isBlacklisted()) {
            System.out.println("用户在黑名单中");
            throw new BusinessException("您已被列入黑名单，无法预约");
        }
        System.out.println("用户不在黑名单");

        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                request.getSeatId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime()
        );
        System.out.println("重叠预约数量: " + overlapping.size());

        if (!overlapping.isEmpty()) {
            throw new BusinessException("该时间段已被预约");
        }

        Booking booking = Booking.builder()
                .seatId(request.getSeatId())
                .studentId(studentId)
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status("pending")
                .build();

        booking = bookingRepository.save(booking);

        seat.setStatus("RESERVED");
        seatRepository.save(seat);

        return booking;
    }

    @Override
    @Transactional
    public void cancelBooking(Long id, Long studentId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("预约不存在"));

        if (!booking.getStudentId().equals(studentId)) {
            throw new BusinessException("无权取消此预约");
        }

        if (!"pending".equals(booking.getStatus())) {
            throw new BusinessException("只能取消待签到的预约");
        }

        booking.setStatus("cancelled");
        booking.setReleaseTime(LocalDateTime.now());
        bookingRepository.save(booking);

        Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
        if (seat != null) {
            seat.setStatus("AVAILABLE");
            seatRepository.save(seat);
        }
    }

    @Override
    @Transactional
    public void signIn(Long id, Long studentId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("预约不存在"));

        if (!booking.getStudentId().equals(studentId)) {
            throw new BusinessException("无权签到此预约");
        }

        if (!"pending".equals(booking.getStatus())) {
            throw new BusinessException("只能对待签到的预约进行签到");
        }

        if (booking.getDate().isBefore(LocalDate.now()) ||
                (booking.getDate().isEqual(LocalDate.now()) && booking.getStartTime().isBefore(LocalTime.now().minusMinutes(15)))) {
            throw new BusinessException("签到已超时");
        }

        booking.setStatus("in_progress");
        booking.setSigninTime(LocalDateTime.now());
        bookingRepository.save(booking);

        Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
        if (seat != null) {
            seat.setStatus("OCCUPIED");
            seatRepository.save(seat);
        }
    }

    @Override
    @Transactional
    public void releaseSeat(Long id, Long studentId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("预约不存在"));

        if (!booking.getStudentId().equals(studentId)) {
            throw new BusinessException("无权释放此座位");
        }

        if (!"in_progress".equals(booking.getStatus())) {
            throw new BusinessException("只能释放已签到的座位");
        }

        booking.setStatus("finished");
        booking.setReleaseTime(LocalDateTime.now());
        bookingRepository.save(booking);

        Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
        if (seat != null) {
            seat.setStatus("AVAILABLE");
            seatRepository.save(seat);
        }
    }

    @Override
    public List<ViolationResponse> getMyViolations(Long studentId) {
        List<Booking> violations = bookingRepository.findViolatedByStudentId(studentId);
        return violations.stream().map(this::convertToViolationResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void processViolations() {
        LocalTime currentTime = LocalTime.now();
        LocalTime timeMinus15Minutes = currentTime.minusMinutes(15);
        LocalDate today = LocalDate.now();

        List<Booking> pendingBookings = bookingRepository.findViolatedBookings(LocalDate.now().plusDays(365), timeMinus15Minutes);

        for (Booking booking : pendingBookings) {
            booking.setStatus("violated");
            booking.setReleaseTime(LocalDateTime.now());
            bookingRepository.save(booking);

            Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
            if (seat != null) {
                seat.setStatus("AVAILABLE");
                seatRepository.save(seat);
            }

            User user = userRepository.findById(booking.getStudentId()).orElse(null);
            if (user != null) {
                int newCreditScore = (user.getCreditScore() != null ? user.getCreditScore() : 100) + VIOLATION_POINTS;
                user.setCreditScore(Math.max(0, newCreditScore));

                int newViolationCount = (user.getViolationCount() != null ? user.getViolationCount() : 0) + 1;
                user.setViolationCount(newViolationCount);

                if (newViolationCount >= BLACKLIST_THRESHOLD) {
                    user.setBlacklistExpire(LocalDateTime.now().plusDays(BLACKLIST_DAYS));
                }

                userRepository.save(user);
            }
        }
    }

    @Override
    @Transactional
    public void processFinishedBookings() {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<Booking> finishedBookings = bookingRepository.findFinishedBookings(today, currentTime);

        for (Booking booking : finishedBookings) {
            booking.setStatus("finished");
            booking.setReleaseTime(LocalDateTime.now());
            bookingRepository.save(booking);

            Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
            if (seat != null) {
                seat.setStatus("AVAILABLE");
                seatRepository.save(seat);
            }
        }
    }

    private BookingResponse convertToResponse(Booking booking) {
        BookingResponse response = BookingResponse.fromEntity(booking);
        
        Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
        if (seat != null) {
            response.setSeatNo(seat.getSeatNumber());
            
            Classroom classroom = classroomRepository.findById(seat.getClassroomId()).orElse(null);
            if (classroom != null) {
                response.setRoomName(classroom.getName());
            }
        }
        
        return response;
    }

    private ViolationResponse convertToViolationResponse(Booking booking) {
        Seat seat = seatRepository.findById(booking.getSeatId()).orElse(null);
        String seatNumber = seat != null ? seat.getSeatNumber() : "";

        return ViolationResponse.builder()
                .date(booking.getDate())
                .reason("预约座位 " + seatNumber + " 后未按时签到，迟到超过15分钟")
                .points(VIOLATION_POINTS)
                .build();
    }

}