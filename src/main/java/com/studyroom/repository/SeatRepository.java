package com.studyroom.repository;

import com.studyroom.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByDeletedFalse();

    List<Seat> findByDeletedTrue();

    List<Seat> findByClassroomIdAndDeletedFalse(Long classroomId);

    Optional<Seat> findByIdAndDeletedFalse(Long id);

    List<Seat> findByIdIn(List<Long> ids);

    List<Seat> findByClassroomIdAndStatusAndDeletedFalse(Long classroomId, String status);

}