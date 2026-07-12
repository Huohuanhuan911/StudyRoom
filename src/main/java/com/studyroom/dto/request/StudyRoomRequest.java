package com.studyroom.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "自习室请求")
public class StudyRoomRequest {

    @NotNull(message = "楼层ID不能为空")
    @Schema(description = "楼层ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long floorId;

    @NotBlank(message = "自习室名称不能为空")
    @Size(max = 100, message = "自习室名称长度不能超过100")
    @Schema(description = "自习室名称", example = "301室", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roomName;

    @Schema(description = "座位容量", example = "40")
    private Integer capacity;

    @Schema(description = "状态：0-正常，1-停用", example = "0")
    private Integer status;

    @Schema(description = "是否有空调", example = "true")
    private Boolean hasAirConditioning;

    @Schema(description = "教室描述", example = "带空调教室")
    private String description;

}