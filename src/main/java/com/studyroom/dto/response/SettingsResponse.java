package com.studyroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsResponse {

    private Integer advanceMinutes;

    private Integer signinTimeout;

    private Integer violationLimit;

    private Integer violationBanDays;

    private String openTime;

    private String closeTime;

}
