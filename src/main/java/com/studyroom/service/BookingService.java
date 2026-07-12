package com.studyroom.service;

import com.studyroom.dto.request.BookingRequest;
import com.studyroom.dto.response.BookingResponse;
import com.studyroom.dto.response.ViolationResponse;
import com.studyroom.entity.Booking;

import java.util.List;

public interface BookingService {

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getMyBookings(Long studentId);

    Booking createBooking(Long studentId, BookingRequest request);

    void cancelBooking(Long id, Long studentId);

    void signIn(Long id, Long studentId);

    void releaseSeat(Long id, Long studentId);

    List<ViolationResponse> getMyViolations(Long studentId);

    void processViolations();

    void processFinishedBookings();

}