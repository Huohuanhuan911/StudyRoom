package com.studyroom.dto.response;

import com.studyroom.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse {

    private Long id;
    private String name;
    private String location;
    private String description;
    private Boolean deleted;
    private LocalDateTime deletedAt;

    public static BuildingResponse fromEntity(Building building) {
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .location(building.getLocation())
                .description(building.getDescription())
                .deleted(building.getDeleted())
                .deletedAt(building.getDeletedAt())
                .build();
    }

}