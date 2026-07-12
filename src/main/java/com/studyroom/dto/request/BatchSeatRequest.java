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
@Schema(description = "批量新增座位请求（按数量）")
public class BatchSeatRequest {

    @NotNull(message = "自习室ID不能为空")
    @Schema(description = "自习室ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long studyRoomId;

    @NotNull(message = "座位数量不能为空")
    @Schema(description = "座位数量", example = "40", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer count;

}