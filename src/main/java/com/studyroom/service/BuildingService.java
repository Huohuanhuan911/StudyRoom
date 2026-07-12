package com.studyroom.service;

import com.studyroom.dto.request.BuildingRequest;
import com.studyroom.entity.Building;

import java.util.List;

public interface BuildingService {

    List<Building> getAllBuildings();

    Building getBuildingById(Long id);

    Building createBuilding(BuildingRequest request);

    Building updateBuilding(Long id, BuildingRequest request);

    Building deleteBuilding(Long id);

    Building restoreBuilding(Long id);

    void batchDelete(List<Long> ids);

    void batchRestore(List<Long> ids);

    List<Building> getDeletedBuildings();

}