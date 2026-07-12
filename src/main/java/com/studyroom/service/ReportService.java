package com.studyroom.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportService {

    Map<String, Object> getReportData(LocalDate startDate, LocalDate endDate);

    byte[] exportReport(LocalDate startDate, LocalDate endDate);

}