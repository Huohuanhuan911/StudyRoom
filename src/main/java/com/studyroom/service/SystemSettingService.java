package com.studyroom.service;

import com.studyroom.dto.request.SettingsRequest;
import com.studyroom.dto.response.SettingsResponse;

public interface SystemSettingService {

    SettingsResponse getAllSettings();

    SettingsResponse updateSettings(SettingsRequest settings);

}
