package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "空闲座位查询请求")
public class FreeSeatRequest {

    @Schema(description = "楼栋ID（可选）", example = "1")
    private Long buildingId;

    @Schema(description = "楼层ID（可选）", example = "1")
    private Long floorId;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "预约开始时间", example = "2024-01-15 14:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "预约结束时间", example = "2024-01-15 16:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

}