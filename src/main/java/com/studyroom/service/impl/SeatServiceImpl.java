package com.studyroom.service.impl;

import com.studyroom.common.BusinessException;
import com.studyroom.dto.request.SeatRequest;
import com.studyroom.entity.Seat;
import com.studyroom.repository.SeatRepository;
import com.studyroom.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findByDeletedFalse();
    }

    @Override
    public List<Seat> getSeatsByClassroom(Long classroomId) {
        return seatRepository.findByClassroomIdAndDeletedFalse(classroomId);
    }

    @Override
    public Seat getSeatById(Long id) {
        return seatRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException("座位不存在"));
    }

    @Override
    @Transactional
    public Seat createSeat(SeatRequest request) {
        Seat seat = Seat.builder()
                .classroomId(request.getClassroomId())
                .seatNumber(request.getSeatNumber())
                .row(request.getRow())
                .col(request.getCol())
                .hasSocket(request.getHasSocket() != null ? request.getHasSocket() : false)
                .status("AVAILABLE")
                .deleted(false)
                .build();
        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public Seat updateSeat(Long id, SeatRequest request) {
        Seat seat = getSeatById(id);
        seat.setClassroomId(request.getClassroomId());
        seat.setSeatNumber(request.getSeatNumber());
        seat.setRow(request.getRow());
        seat.setCol(request.getCol());
        seat.setHasSocket(request.getHasSocket() != null ? request.getHasSocket() : false);
        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public Seat deleteSeat(Long id) {
        Seat seat = getSeatById(id);
        seat.setDeleted(true);
        seat.setDeletedAt(LocalDateTime.now());
        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public Seat restoreSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new BusinessException("座位不存在"));
        seat.setDeleted(false);
        seat.setDeletedAt(null);
        return seatRepository.save(seat);
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        List<Seat> seats = seatRepository.findByIdIn(ids);
        seats.forEach(s -> {
            s.setDeleted(true);
            s.setDeletedAt(LocalDateTime.now());
        });
        seatRepository.saveAll(seats);
    }

    @Override
    @Transactional
    public void batchRestore(List<Long> ids) {
        List<Seat> seats = seatRepository.findByIdIn(ids);
        seats.forEach(s -> {
            s.setDeleted(false);
            s.setDeletedAt(null);
        });
        seatRepository.saveAll(seats);
    }

    @Override
    public List<Seat> getDeletedSeats() {
        return seatRepository.findByDeletedTrue();
    }

    @Override
    public List<Seat> getDeletedSeatsByClassroom(Long classroomId) {
        return seatRepository.findByClassroomIdAndDeletedFalse(classroomId);
    }

}