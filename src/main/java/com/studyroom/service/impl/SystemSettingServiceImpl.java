package com.studyroom.service.impl;

import com.studyroom.dto.request.SettingsRequest;
import com.studyroom.dto.response.SettingsResponse;
import com.studyroom.entity.SystemSetting;
import com.studyroom.repository.SystemSettingRepository;
import com.studyroom.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    private static final String KEY_ADVANCE_MINUTES = "advance_minutes";
    private static final String KEY_LATE_MINUTES = "late_minutes";
    private static final String KEY_VIOLATION_LIMIT = "violation_limit";
    private static final String KEY_BAN_DAYS = "ban_days";
    private static final String KEY_OPEN_TIME = "open_time";
    private static final String KEY_CLOSE_TIME = "close_time";

    @Override
    public SettingsResponse getAllSettings() {
        return SettingsResponse.builder()
                .advanceMinutes(getIntSetting(KEY_ADVANCE_MINUTES, 30))
                .signinTimeout(getIntSetting(KEY_LATE_MINUTES, 15))
                .violationLimit(getIntSetting(KEY_VIOLATION_LIMIT, 3))
                .violationBanDays(getIntSetting(KEY_BAN_DAYS, 7))
                .openTime(getStringSetting(KEY_OPEN_TIME, "06:00"))
                .closeTime(getStringSetting(KEY_CLOSE_TIME, "23:59"))
                .build();
    }

    @Override
    @Transactional
    public SettingsResponse updateSettings(SettingsRequest settings) {
        if (settings.getAdvanceMinutes() != null) {
            saveSetting(KEY_ADVANCE_MINUTES, settings.getAdvanceMinutes().toString());
        }
        if (settings.getSigninTimeout() != null) {
            saveSetting(KEY_LATE_MINUTES, settings.getSigninTimeout().toString());
        }
        if (settings.getViolationLimit() != null) {
            saveSetting(KEY_VIOLATION_LIMIT, settings.getViolationLimit().toString());
        }
        if (settings.getViolationBanDays() != null) {
            saveSetting(KEY_BAN_DAYS, settings.getViolationBanDays().toString());
        }
        if (settings.getOpenTime() != null) {
            saveSetting(KEY_OPEN_TIME, settings.getOpenTime());
        }
        if (settings.getCloseTime() != null) {
            saveSetting(KEY_CLOSE_TIME, settings.getCloseTime());
        }
        
        return getAllSettings();
    }

    private int getIntSetting(String key, int defaultValue) {
        return systemSettingRepository.findByKey(key)
                .map(s -> Integer.parseInt(s.getValue()))
                .orElse(defaultValue);
    }

    private String getStringSetting(String key, String defaultValue) {
        return systemSettingRepository.findByKey(key)
                .map(SystemSetting::getValue)
                .orElse(defaultValue);
    }

    private void saveSetting(String key, String value) {
        SystemSetting setting = systemSettingRepository.findByKey(key)
                .orElse(SystemSetting.builder()
                        .key(key)
                        .build());
        setting.setValue(value);
        setting.setUpdatedAt(LocalDateTime.now());
        systemSettingRepository.save(setting);
    }

}
