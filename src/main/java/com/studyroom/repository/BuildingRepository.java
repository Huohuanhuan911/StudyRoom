package com.studyroom.repository;

import com.studyroom.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findByDeletedFalse();

    List<Building> findByDeletedTrue();

    Optional<Building> findByIdAndDeletedFalse(Long id);

    List<Building> findByIdIn(List<Long> ids);

}