package com.studyroom.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDeleteRequest {

    @NotEmpty(message = "ID列表不能为空")
    private List<Long> ids;

}