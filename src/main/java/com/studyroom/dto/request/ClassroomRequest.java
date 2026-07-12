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
public class ClassroomRequest {

    @NotNull(message = "楼栋ID不能为空")
    private Long buildingId;

    @NotBlank(message = "教室名称不能为空")
    private String name;

    private Integer floor;

    private Integer capacity;

    @JsonProperty("hasAirConditioner")
    private Boolean hasAirConditioning;

    @JsonProperty("open")
    private Boolean open;

    private String description;

}