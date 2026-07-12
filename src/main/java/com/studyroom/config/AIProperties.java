package com.studyroom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AIProperties {

    private String provider = "deepseek";
    private String apiKey;
    private String apiUrl = "https://api.deepseek.com/v1/chat/completions";
    private String model = "deepseek-chat";
    private Integer timeout = 30000;
    private boolean enabled = false;
    private boolean mockMode = true;

}