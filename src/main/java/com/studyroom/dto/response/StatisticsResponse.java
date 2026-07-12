package com.studyroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统计响应")
public class StatisticsResponse {

    @Schema(description = "总预约次数", example = "100")
    private Long totalReservations;

    @Schema(description = "已完成预约次数", example = "80")
    private Long completedReservations;

    @Schema(description = "爽约次数", example = "5")
    private Long noShowReservations;

    @Schema(description = "日均使用率（0-1）", example = "0.6")
    private Double dailyUsageRate;

    @Schema(description = "每小时预约次数统计")
    private Map<String, Long> hourlyReservationCount;

    @Schema(description = "各自习室使用情况")
    private List<RoomUsage> roomUsages;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "自习室使用情况")
    public static class RoomUsage {
        @Schema(description = "自习室ID", example = "1")
        private Long roomId;
        @Schema(description = "自习室名称", example = "301室")
        private String roomName;
        @Schema(description = "预约次数", example = "20")
        private Long reservationCount;
        @Schema(description = "使用率（0-1）", example = "0.5")
        private Double usageRate;
    }

}