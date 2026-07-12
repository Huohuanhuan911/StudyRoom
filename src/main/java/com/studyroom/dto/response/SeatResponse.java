package com.studyroom.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studyroom.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {

    private Long id;
    private Long classroomId;
    @JsonProperty("seatNumber")
    private String seatNumber;
    @JsonProperty("no")
    private String no;
    private String status;
    private Integer row;
    private Integer col;
    @JsonProperty("hasSocket")
    private Boolean hasSocket;
    private Boolean deleted;
    private LocalDateTime deletedAt;
    private Boolean occupied;

    public static SeatResponse fromEntity(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .classroomId(seat.getClassroomId())
                .seatNumber(seat.getSeatNumber())
                .no(seat.getSeatNumber())
                .status(seat.getStatus())
                .row(seat.getRow())
                .col(seat.getCol())
                .hasSocket(seat.getHasSocket())
                .deleted(seat.getDeleted())
                .deletedAt(seat.getDeletedAt())
                .occupied(false)
                .build();
    }

}