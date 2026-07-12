package com.studyroom.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    @NotNull(message = "教室ID不能为空")
    private Long classroomId;

    @NotBlank(message = "座位编号不能为空")
    @JsonProperty("no")
    private String seatNumber;

    private Integer row;

    private Integer col;

    @JsonProperty("hasSocket")
    private Boolean hasSocket;

}