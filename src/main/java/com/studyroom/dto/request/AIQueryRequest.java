package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI查询请求")
public class AIQueryRequest {

    @NotBlank(message = "问题不能为空")
    @Schema(description = "自然语言问题", example = "今天下午3点A区有哪些空教室", requiredMode = Schema.RequiredMode.REQUIRED)
    private String question;

}