package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "楼层请求")
public class FloorRequest {

    @NotNull(message = "楼栋ID不能为空")
    @Schema(description = "楼栋ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long buildingId;

    @NotNull(message = "楼层号不能为空")
    @Schema(description = "楼层号", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer floorNumber;

}