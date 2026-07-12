package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "预约请求（日期时间分离格式）")
public class ReservationRequest {

    @NotNull(message = "座位ID不能为空")
    @Schema(description = "座位ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long seatId;

    @NotNull(message = "自习室ID不能为空")
    @Schema(description = "自习室ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long studyRoomId;

    @NotNull(message = "预约日期不能为空")
    @Schema(description = "预约日期", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate date;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "开始时间", example = "14:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime startTime;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "结束时间", example = "16:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime endTime;

}