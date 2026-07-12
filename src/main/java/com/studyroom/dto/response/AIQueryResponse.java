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
@Schema(description = "AI查询响应")
public class AIQueryResponse {

    @Schema(description = "生成的SQL语句")
    private String sql;

    @Schema(description = "查询结果")
    private List<Map<String, Object>> result;

    @Schema(description = "自然语言回答")
    private String naturalLanguage;

    @Schema(description = "查询是否成功", example = "true")
    private boolean success;

    @Schema(description = "错误信息（失败时返回）")
    private String errorMessage;

}