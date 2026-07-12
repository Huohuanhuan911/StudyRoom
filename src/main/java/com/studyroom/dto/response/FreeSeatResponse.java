package com.studyroom.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "空闲座位响应")
public class FreeSeatResponse {

    @Schema(description = "座位ID", example = "1")
    private Long seatId;

    @Schema(description = "座位编号", example = "A-301-01")
    private String seatNumber;

    @Schema(description = "座位状态：0-空闲，1-已预约，2-已占用", example = "0")
    private Integer seatStatus;

    @Schema(description = "自习室ID", example = "1")
    private Long studyRoomId;

    @Schema(description = "自习室名称", example = "301室")
    private String studyRoomName;

    @Schema(description = "自习室容量", example = "40")
    private Integer studyRoomCapacity;

    @Schema(description = "楼层ID", example = "1")
    private Long floorId;

    @Schema(description = "楼层号", example = "3")
    private Integer floorNumber;

    @Schema(description = "楼栋ID", example = "1")
    private Long buildingId;

    @Schema(description = "楼栋名称", example = "A栋")
    private String buildingName;

}