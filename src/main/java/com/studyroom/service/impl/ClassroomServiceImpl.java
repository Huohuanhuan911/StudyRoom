package com.studyroom.service.impl;

import com.studyroom.common.BusinessException;
import com.studyroom.dto.request.ClassroomRequest;
import com.studyroom.entity.Classroom;
import com.studyroom.entity.Seat;
import com.studyroom.repository.ClassroomRepository;
import com.studyroom.repository.SeatRepository;
import com.studyroom.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final SeatRepository seatRepository;

    @Override
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findByDeletedFalse();
    }

    @Override
    public List<Classroom> getClassroomsByBuilding(Long buildingId) {
        return classroomRepository.findByBuildingIdAndDeletedFalse(buildingId);
    }

    @Override
    public Classroom getClassroomById(Long id) {
        return classroomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException("教室不存在"));
    }

    @Override
    @Transactional
    public Classroom createClassroom(ClassroomRequest request) {
        Classroom classroom = Classroom.builder()
                .buildingId(request.getBuildingId())
                .name(request.getName())
                .floor(request.getFloor())
                .capacity(request.getCapacity())
                .hasAirConditioning(request.getHasAirConditioning() != null ? request.getHasAirConditioning() : false)
                .open(request.getOpen() != null ? request.getOpen() : true)
                .description(request.getDescription())
                .deleted(false)
                .build();
        return classroomRepository.save(classroom);
    }

    @Override
    @Transactional
    public Classroom updateClassroom(Long id, ClassroomRequest request) {
        Classroom classroom = getClassroomById(id);
        classroom.setBuildingId(request.getBuildingId());
        classroom.setName(request.getName());
        classroom.setFloor(request.getFloor());
        classroom.setCapacity(request.getCapacity());
        classroom.setHasAirConditioning(request.getHasAirConditioning() != null ? request.getHasAirConditioning() : false);
        classroom.setOpen(request.getOpen() != null ? request.getOpen() : classroom.getOpen());
        classroom.setDescription(request.getDescription());
        return classroomRepository.save(classroom);
    }

    @Override
    @Transactional
    public Classroom deleteClassroom(Long id) {
        Classroom classroom = getClassroomById(id);
        classroom.setDeleted(true);
        classroom.setDeletedAt(LocalDateTime.now());
        classroomRepository.save(classroom);
        
        List<Seat> seats = seatRepository.findByClassroomIdAndDeletedFalse(id);
        for (Seat seat : seats) {
            seat.setDeleted(true);
            seat.setDeletedAt(LocalDateTime.now());
        }
        seatRepository.saveAll(seats);
        
        return classroom;
    }

    @Override
    @Transactional
    public Classroom restoreClassroom(Long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("教室不存在"));
        classroom.setDeleted(false);
        classroom.setDeletedAt(null);
        return classroomRepository.save(classroom);
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        List<Classroom> classrooms = classroomRepository.findByIdIn(ids);
        classrooms.forEach(c -> {
            c.setDeleted(true);
            c.setDeletedAt(LocalDateTime.now());
        });
        classroomRepository.saveAll(classrooms);
    }

    @Override
    @Transactional
    public void batchRestore(List<Long> ids) {
        List<Classroom> classrooms = classroomRepository.findByIdIn(ids);
        classrooms.forEach(c -> {
            c.setDeleted(false);
            c.setDeletedAt(null);
        });
        classroomRepository.saveAll(classrooms);
    }

    @Override
    public List<Classroom> getDeletedClassrooms() {
        return classroomRepository.findByDeletedTrue();
    }

}