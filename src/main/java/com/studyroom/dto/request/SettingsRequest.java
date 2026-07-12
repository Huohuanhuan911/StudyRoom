package com.studyroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingsRequest {

    private Integer advanceMinutes;

    private Integer signinTimeout;

    private Integer violationLimit;

    private Integer violationBanDays;

    private String openTime;

    private String closeTime;

}
