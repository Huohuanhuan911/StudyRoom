package com.studyroom.service;

import com.studyroom.dto.request.SeatRequest;
import com.studyroom.entity.Seat;

import java.util.List;

public interface SeatService {

    List<Seat> getAllSeats();

    List<Seat> getSeatsByClassroom(Long classroomId);

    Seat getSeatById(Long id);

    Seat createSeat(SeatRequest request);

    Seat updateSeat(Long id, SeatRequest request);

    Seat deleteSeat(Long id);

    Seat restoreSeat(Long id);

    void batchDelete(List<Long> ids);

    void batchRestore(List<Long> ids);

    List<Seat> getDeletedSeats();

    List<Seat> getDeletedSeatsByClassroom(Long classroomId);

}