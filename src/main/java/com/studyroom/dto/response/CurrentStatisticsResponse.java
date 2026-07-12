package com.studyroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "实时统计响应")
public class CurrentStatisticsResponse {

    @Schema(description = "全校当前总使用人数", example = "150")
    private Long totalUsers;

    @Schema(description = "全校总座位数", example = "500")
    private Long totalSeats;

    @Schema(description = "总体使用率（0-1）", example = "0.3")
    private Double overallOccupancyRate;

    @Schema(description = "各自习室统计列表")
    private List<RoomStatistics> roomStatistics;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "自习室统计")
    public static class RoomStatistics {
        @Schema(description = "自习室ID", example = "1")
        private Long roomId;
        @Schema(description = "自习室名称", example = "301室")
        private String roomName;
        @Schema(description = "当前使用人数", example = "20")
        private Long currentUsers;
        @Schema(description = "空闲座位数", example = "20")
        private Long freeSeats;
        @Schema(description = "总座位数", example = "40")
        private Long totalSeats;
        @Schema(description = "使用率（0-1）", example = "0.5")
        private Double occupancyRate;
    }

}