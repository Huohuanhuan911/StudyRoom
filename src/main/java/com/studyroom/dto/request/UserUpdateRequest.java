package com.studyroom.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String name;

    private String phone;

    private String email;

    private String college;

    private String major;

    private String grade;

    @JsonProperty("inBlacklist")
    private Boolean inBlacklist;

    @JsonProperty("blacklistReason")
    private String blacklistReason;

    @JsonProperty("blacklistExpireAt")
    private LocalDateTime blacklistExpireAt;

    @JsonProperty("violationCount")
    private Integer violationCount;

}