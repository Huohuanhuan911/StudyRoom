package com.studyroom.service;

import com.studyroom.dto.request.ClassroomRequest;
import com.studyroom.entity.Classroom;

import java.util.List;

public interface ClassroomService {

    List<Classroom> getAllClassrooms();

    List<Classroom> getClassroomsByBuilding(Long buildingId);

    Classroom getClassroomById(Long id);

    Classroom createClassroom(ClassroomRequest request);

    Classroom updateClassroom(Long id, ClassroomRequest request);

    Classroom deleteClassroom(Long id);

    Classroom restoreClassroom(Long id);

    void batchDelete(List<Long> ids);

    void batchRestore(List<Long> ids);

    List<Classroom> getDeletedClassrooms();

}