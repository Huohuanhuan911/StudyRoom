package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "预约查询请求")
public class ReservationQueryRequest {

    @Schema(description = "状态筛选：0-待签到，1-已签到，2-已取消，3-爽约，4-已结束")
    private Integer status;

    @Schema(description = "页码（从0开始）", example = "0")
    private Integer page = 0;

    @Schema(description = "每页数量", example = "10")
    private Integer size = 10;

}