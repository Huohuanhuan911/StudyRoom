package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "批量创建座位请求（按行列）")
public class SeatBatchRequest {

    @NotNull(message = "自习室ID不能为空")
    @Schema(description = "自习室ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long studyRoomId;

    @NotNull(message = "行数不能为空")
    @Positive(message = "行数必须大于0")
    @Schema(description = "行数", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer rows;

    @NotNull(message = "列数不能为空")
    @Positive(message = "列数必须大于0")
    @Schema(description = "列数", example = "8", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer columns;

}