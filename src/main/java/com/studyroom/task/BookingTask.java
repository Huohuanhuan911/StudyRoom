package com.studyroom.task;

import com.studyroom.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingTask {

    private final BookingService bookingService;

    @Scheduled(cron = "0 * * * * ?")
    public void processViolations() {
        log.info("Processing violations...");
        bookingService.processViolations();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void processFinishedBookings() {
        log.info("Processing finished bookings...");
        bookingService.processFinishedBookings();
    }

}