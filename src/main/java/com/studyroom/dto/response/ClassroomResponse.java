package com.studyroom.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studyroom.entity.Classroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResponse {

    private Long id;
    private Long buildingId;
    private String name;
    private Integer floor;
    private Integer capacity;
    private String description;
    @JsonProperty("open")
    private Boolean isOpen;
    @JsonProperty("hasAirConditioner")
    private Boolean hasAirConditioning;
    private Boolean deleted;
    private LocalDateTime deletedAt;

    public static ClassroomResponse fromEntity(Classroom classroom) {
        return ClassroomResponse.builder()
                .id(classroom.getId())
                .buildingId(classroom.getBuildingId())
                .name(classroom.getName())
                .floor(classroom.getFloor())
                .capacity(classroom.getCapacity())
                .description(classroom.getDescription())
                .isOpen(classroom.getOpen())
                .hasAirConditioning(classroom.getHasAirConditioning())
                .deleted(classroom.getDeleted())
                .deletedAt(classroom.getDeletedAt())
                .build();
    }

}