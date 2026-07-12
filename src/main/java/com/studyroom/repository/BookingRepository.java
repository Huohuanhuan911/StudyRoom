package com.studyroom.repository;

import com.studyroom.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStudentIdOrderByDateDescStartTimeDesc(Long studentId);

    List<Booking> findByStudentIdAndStatusOrderByDateDescStartTimeDesc(Long studentId, String status);

    List<Booking> findByStatusOrderByDateDescStartTimeDesc(String status);

    @Query("SELECT b FROM Booking b WHERE b.status = 'pending' AND b.date = :date AND b.startTime <= :endTime AND b.endTime >= :startTime AND b.seatId = :seatId")
    List<Booking> findOverlappingBookings(Long seatId, LocalDate date, LocalTime startTime, LocalTime endTime);

    @Query("SELECT b.seatId FROM Booking b WHERE b.status = 'pending' AND b.date = :date AND b.startTime <= :endTime AND b.endTime >= :startTime")
    Set<Long> findOverlappingSeatIds(LocalDate date, LocalTime startTime, LocalTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.status = 'pending' AND b.date = :date AND b.startTime <= :currentTime")
    List<Booking> findPendingBookingsForCheckIn(LocalDate date, LocalTime currentTime);

    @Query("SELECT b FROM Booking b WHERE b.status = 'in_progress' AND b.date = :date AND b.endTime <= :currentTime")
    List<Booking> findFinishedBookings(LocalDate date, LocalTime currentTime);

    @Query("SELECT b FROM Booking b WHERE b.status = 'pending' AND b.date <= :date AND b.startTime < :timeMinus15Minutes")
    List<Booking> findViolatedBookings(LocalDate date, LocalTime timeMinus15Minutes);

    @Query("SELECT b FROM Booking b WHERE b.studentId = :studentId AND b.status = 'violated' ORDER BY b.date DESC")
    List<Booking> findViolatedByStudentId(Long studentId);

}