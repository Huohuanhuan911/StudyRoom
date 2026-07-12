package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统计查询请求")
public class StatisticsQueryRequest {

    @Schema(description = "自习室ID（可选，为空时查询全部）", example = "1")
    private Long studyRoomId;

    @NotNull(message = "开始日期不能为空")
    @Schema(description = "开始日期", example = "2024-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate startDate;

    @NotNull(message = "结束日期不能为空")
    @Schema(description = "结束日期", example = "2024-01-31", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate endDate;

}