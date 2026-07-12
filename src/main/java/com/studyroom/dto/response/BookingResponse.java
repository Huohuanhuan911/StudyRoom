package com.studyroom.dto.response;

import com.studyroom.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private Long seatId;
    private Long studentId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private LocalDateTime signinTime;
    private LocalDateTime releaseTime;
    private LocalDateTime createdAt;
    private String seatNo;
    private String roomName;

    public static BookingResponse fromEntity(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .seatId(booking.getSeatId())
                .studentId(booking.getStudentId())
                .date(booking.getDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .signinTime(booking.getSigninTime())
                .releaseTime(booking.getReleaseTime())
                .createdAt(booking.getCreatedAt())
                .build();
    }

}