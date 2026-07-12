package com.studyroom.repository;

import com.studyroom.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findByDeletedFalse();

    List<Classroom> findByDeletedTrue();

    List<Classroom> findByBuildingIdAndDeletedFalse(Long buildingId);

    Optional<Classroom> findByIdAndDeletedFalse(Long id);

    List<Classroom> findByIdIn(List<Long> ids);

}