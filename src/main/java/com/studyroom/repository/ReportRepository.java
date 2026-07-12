package com.studyroom.repository;

import com.studyroom.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Report> findByClassroomIdAndDateBetween(Long classroomId, LocalDate startDate, LocalDate endDate);

}