package com.studyroom.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {

    @NotBlank(message = "楼栋名称不能为空")
    private String name;

    private String location;

    private String description;

}