package com.studyroom.service.impl;

import com.studyroom.repository.BookingRepository;
import com.studyroom.repository.ClassroomRepository;
import com.studyroom.repository.SeatRepository;
import com.studyroom.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final BookingRepository bookingRepository;
    private final ClassroomRepository classroomRepository;
    private final SeatRepository seatRepository;

    @Override
    public Map<String, Object> getReportData(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();

        long totalBookings = bookingRepository.count();
        long totalClassrooms = classroomRepository.findByDeletedFalse().size();
        long totalSeats = seatRepository.findByDeletedFalse().size();
        long availableSeats = seatRepository.findByClassroomIdAndStatusAndDeletedFalse(0L, "AVAILABLE").size();

        report.put("totalBookings", totalBookings);
        report.put("totalClassrooms", totalClassrooms);
        report.put("totalSeats", totalSeats);
        report.put("availableSeats", availableSeats);

        return report;
    }

    @Override
    public byte[] exportReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> data = getReportData(startDate, endDate);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            writer.println("指标,数值");
            writer.println("总预约数," + data.get("totalBookings"));
            writer.println("总教室数," + data.get("totalClassrooms"));
            writer.println("总座位数," + data.get("totalSeats"));
            writer.println("可用座位数," + data.get("availableSeats"));
            writer.flush();
        }

        return outputStream.toByteArray();
    }

}