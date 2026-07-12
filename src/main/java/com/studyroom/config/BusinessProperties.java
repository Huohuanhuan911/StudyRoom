package com.studyroom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "business")
public class BusinessProperties {

    private Reservation reservation = new Reservation();
    private Blacklist blacklist = new Blacklist();

    @Data
    public static class Reservation {
        private int maxDailyTimes = 3;
        private int advanceMinutes = 30;
        private int maxDurationHours = 4;
        private int cancelBeforeMinutes = 15;
        private int checkinLateMinutes = 15;
        private int checkinEarlyMinutes = 30;
    }

    @Data
    public static class Blacklist {
        private int defaultDays = 7;
        private int triggerCount = 3;
    }

}