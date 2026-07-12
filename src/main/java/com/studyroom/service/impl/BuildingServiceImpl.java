package com.studyroom.service.impl;

import com.studyroom.common.BusinessException;
import com.studyroom.dto.request.BuildingRequest;
import com.studyroom.entity.Building;
import com.studyroom.entity.Classroom;
import com.studyroom.entity.Seat;
import com.studyroom.repository.BuildingRepository;
import com.studyroom.repository.ClassroomRepository;
import com.studyroom.repository.SeatRepository;
import com.studyroom.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final ClassroomRepository classroomRepository;
    private final SeatRepository seatRepository;

    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.findByDeletedFalse();
    }

    @Override
    public Building getBuildingById(Long id) {
        return buildingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException("楼栋不存在"));
    }

    @Override
    @Transactional
    public Building createBuilding(BuildingRequest request) {
        Building building = Building.builder()
                .name(request.getName())
                .location(request.getLocation())
                .description(request.getDescription())
                .deleted(false)
                .build();
        return buildingRepository.save(building);
    }

    @Override
    @Transactional
    public Building updateBuilding(Long id, BuildingRequest request) {
        Building building = getBuildingById(id);
        building.setName(request.getName());
        building.setLocation(request.getLocation());
        building.setDescription(request.getDescription());
        return buildingRepository.save(building);
    }

    @Override
    @Transactional
    public Building deleteBuilding(Long id) {
        Building building = getBuildingById(id);
        building.setDeleted(true);
        building.setDeletedAt(LocalDateTime.now());
        buildingRepository.save(building);
        
        List<Classroom> classrooms = classroomRepository.findByBuildingIdAndDeletedFalse(id);
        for (Classroom classroom : classrooms) {
            classroom.setDeleted(true);
            classroom.setDeletedAt(LocalDateTime.now());
            classroomRepository.save(classroom);
            
            List<Seat> seats = seatRepository.findByClassroomIdAndDeletedFalse(classroom.getId());
            for (Seat seat : seats) {
                seat.setDeleted(true);
                seat.setDeletedAt(LocalDateTime.now());
            }
            seatRepository.saveAll(seats);
        }
        
        return building;
    }

    @Override
    @Transactional
    public Building restoreBuilding(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("楼栋不存在"));
        building.setDeleted(false);
        building.setDeletedAt(null);
        return buildingRepository.save(building);
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        List<Building> buildings = buildingRepository.findByIdIn(ids);
        buildings.forEach(b -> {
            b.setDeleted(true);
            b.setDeletedAt(LocalDateTime.now());
        });
        buildingRepository.saveAll(buildings);
    }

    @Override
    @Transactional
    public void batchRestore(List<Long> ids) {
        List<Building> buildings = buildingRepository.findByIdIn(ids);
        buildings.forEach(b -> {
            b.setDeleted(false);
            b.setDeletedAt(null);
        });
        buildingRepository.saveAll(buildings);
    }

    @Override
    public List<Building> getDeletedBuildings() {
        return buildingRepository.findByDeletedTrue();
    }

}