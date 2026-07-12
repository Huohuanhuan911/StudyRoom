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
@Schema(description = "签到请求")
public class CheckInRequest {

    @NotNull(message = "预约ID不能为空")
    @Schema(description = "预约ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long reservationId;

}