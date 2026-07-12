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
@Schema(description = "分页响应")
public class PageResponse<T> {

    @Schema(description = "数据列表")
    private List<T> items;

    @Schema(description = "页码（从0开始）", example = "0")
    private Integer page;

    @Schema(description = "每页数量", example = "10")
    private Integer size;

    @Schema(description = "总记录数", example = "100")
    private Long totalElements;

    @Schema(description = "总页数", example = "10")
    private Integer totalPages;

}