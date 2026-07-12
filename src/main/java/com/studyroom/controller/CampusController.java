package com.studyroom.controller;

import com.studyroom.common.Result;
import com.studyroom.dto.request.BatchDeleteRequest;
import com.studyroom.dto.request.BuildingRequest;
import com.studyroom.dto.request.ClassroomRequest;
import com.studyroom.dto.request.SeatRequest;
import com.studyroom.dto.response.BuildingResponse;
import com.studyroom.dto.response.ClassroomResponse;
import com.studyroom.dto.response.SeatResponse;
import com.studyroom.entity.Building;
import com.studyroom.entity.Classroom;
import com.studyroom.entity.Seat;
import com.studyroom.repository.BookingRepository;
import com.studyroom.repository.ClassroomRepository;
import com.studyroom.repository.SeatRepository;
import com.studyroom.service.BuildingService;
import com.studyroom.service.ClassroomService;
import com.studyroom.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/campus", "/campus"})
@RequiredArgsConstructor
@Tag(name = "校园资源接口", description = "楼栋、教室、座位管理")
public class CampusController {

    private final BuildingService buildingService;
    private final ClassroomService classroomService;
    private final SeatService seatService;
    private final ClassroomRepository classroomRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    @GetMapping("/buildings")
    @Operation(summary = "楼栋列表", description = "获取所有楼栋")
    public Result<List<BuildingResponse>> getBuildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        List<BuildingResponse> responses = buildings.stream()
                .map(BuildingResponse::fromEntity)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    @GetMapping("/buildings/{id}")
    @Operation(summary = "楼栋详情", description = "获取楼栋详情")
    public Result<BuildingResponse> getBuildingById(@PathVariable Long id) {
        Building building = buildingService.getBuildingById(id);
        return Result.success(BuildingResponse.fromEntity(building));
    }

    @PostMapping("/buildings")
    @Operation(summary = "创建楼栋", description = "创建新楼栋")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<BuildingResponse> createBuilding(@Valid @RequestBody BuildingRequest request) {
        Building building = buildingService.createBuilding(request);
        return Result.success(BuildingResponse.fromEntity(building));
    }

    @PutMapping("/buildings/{id}")
    @Operation(summary = "编辑楼栋", description = "编辑楼栋信息")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<BuildingResponse> updateBuilding(@PathVariable Long id, @Valid @RequestBody BuildingRequest request) {
        Building building = buildingService.updateBuilding(id, request);
        return Result.success(BuildingResponse.fromEntity(building));
    }

    @GetMapping("/classrooms")
    @Operation(summary = "教室列表", description = "按楼栋筛选")
    public Result<List<ClassroomResponse>> getClassrooms(@RequestParam(required = false) Long buildingId) {
        List<Classroom> classrooms;
        if (buildingId != null) {
            classrooms = classroomService.getClassroomsByBuilding(buildingId);
        } else {
            classrooms = classroomService.getAllClassrooms();
        }
        List<ClassroomResponse> responses = classrooms.stream()
                .map(ClassroomResponse::fromEntity)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    @GetMapping("/classrooms/{id}")
    @Operation(summary = "教室详情", description = "获取教室详情")
    public Result<ClassroomResponse> getClassroomById(@PathVariable Long id) {
        Classroom classroom = classroomService.getClassroomById(id);
        return Result.success(ClassroomResponse.fromEntity(classroom));
    }

    @PostMapping("/classrooms")
    @Operation(summary = "创建教室", description = "创建新教室")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<ClassroomResponse> createClassroom(@Valid @RequestBody ClassroomRequest request) {
        Classroom classroom = classroomService.createClassroom(request);
        return Result.success(ClassroomResponse.fromEntity(classroom));
    }

    @PutMapping("/classrooms/{id}")
    @Operation(summary = "编辑教室", description = "编辑教室信息")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<ClassroomResponse> updateClassroom(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Classroom classroom = classroomService.getClassroomById(id);
        
        if (request.containsKey("name")) {
            classroom.setName((String) request.get("name"));
        }
        if (request.containsKey("buildingId")) {
            classroom.setBuildingId(((Number) request.get("buildingId")).longValue());
        }
        if (request.containsKey("floor")) {
            classroom.setFloor(((Number) request.get("floor")).intValue());
        }
        if (request.containsKey("capacity")) {
            classroom.setCapacity(((Number) request.get("capacity")).intValue());
        }
        if (request.containsKey("hasAirConditioner")) {
            classroom.setHasAirConditioning((Boolean) request.get("hasAirConditioner"));
        }
        if (request.containsKey("open")) {
            classroom.setOpen((Boolean) request.get("open"));
        }
        if (request.containsKey("description")) {
            classroom.setDescription((String) request.get("description"));
        }
        
        classroom = classroomRepository.save(classroom);
        return Result.success(ClassroomResponse.fromEntity(classroom));
    }

    @GetMapping("/seats")
    @Operation(summary = "座位列表", description = "按教室筛选")
    public Result<List<SeatResponse>> getSeats(
            @RequestParam(required = false) Long classroomId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        List<Seat> seats;
        if (classroomId != null) {
            seats = seatService.getSeatsByClassroom(classroomId);
        } else {
            seats = seatService.getAllSeats();
        }
        
        Set<Long> occupiedIds = findOccupiedSeatIds(date, startTime, endTime);
        
        List<SeatResponse> responses = seats.stream()
                .map(seat -> {
                    SeatResponse response = SeatResponse.fromEntity(seat);
                    response.setOccupied(occupiedIds.contains(seat.getId()));
                    return response;
                })
                .collect(Collectors.toList());
        return Result.success(responses);
    }
    
    private Set<Long> findOccupiedSeatIds(String date, String startTime, String endTime) {
        if (date == null || startTime == null || endTime == null) {
            return Set.of();
        }
        try {
            LocalDate bookingDate = LocalDate.parse(date);
            LocalTime bookingStartTime = LocalTime.parse(startTime);
            LocalTime bookingEndTime = LocalTime.parse(endTime);
            return bookingRepository.findOverlappingSeatIds(
                    bookingDate, bookingStartTime, bookingEndTime);
        } catch (Exception e) {
            return Set.of();
        }
    }

    @GetMapping("/seats/{id}")
    @Operation(summary = "座位详情", description = "获取座位详情")
    public Result<SeatResponse> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.getSeatById(id);
        return Result.success(SeatResponse.fromEntity(seat));
    }

    @PostMapping("/seats")
    @Operation(summary = "创建座位", description = "创建新座位")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SeatResponse> createSeat(@Valid @RequestBody SeatRequest request) {
        Seat seat = seatService.createSeat(request);
        return Result.success(SeatResponse.fromEntity(seat));
    }

    @PutMapping("/seats/{id}")
    @Operation(summary = "编辑座位", description = "编辑座位信息")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SeatResponse> updateSeat(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Seat seat = seatService.getSeatById(id);
        
        if (request.containsKey("no")) {
            seat.setSeatNumber((String) request.get("no"));
        }
        if (request.containsKey("seatNumber")) {
            seat.setSeatNumber((String) request.get("seatNumber"));
        }
        if (request.containsKey("hasSocket")) {
            seat.setHasSocket((Boolean) request.get("hasSocket"));
        }
        if (request.containsKey("row")) {
            seat.setRow(((Number) request.get("row")).intValue());
        }
        if (request.containsKey("col")) {
            seat.setCol(((Number) request.get("col")).intValue());
        }
        
        seat = seatRepository.save(seat);
        return Result.success(SeatResponse.fromEntity(seat));
    }

    @PostMapping("/admin/buildings/batch-delete")
    @Operation(summary = "批量删除楼栋", description = "批量软删除楼栋")
    public Result<Void> batchDeleteBuildings(@Valid @RequestBody BatchDeleteRequest request) {
        buildingService.batchDelete(request.getIds());
        return Result.success();
    }

    @PostMapping("/admin/buildings/batch-restore")
    @Operation(summary = "批量恢复楼栋", description = "批量恢复楼栋")
    public Result<Void> batchRestoreBuildings(@Valid @RequestBody BatchDeleteRequest request) {
        buildingService.batchRestore(request.getIds());
        return Result.success();
    }

    @PostMapping("/admin/classrooms/batch-delete")
    @Operation(summary = "批量删除教室", description = "批量软删除教室")
    public Result<Void> batchDeleteClassrooms(@Valid @RequestBody BatchDeleteRequest request) {
        classroomService.batchDelete(request.getIds());
        return Result.success();
    }

    @PostMapping("/admin/classrooms/batch-restore")
    @Operation(summary = "批量恢复教室", description = "批量恢复教室")
    public Result<Void> batchRestoreClassrooms(@Valid @RequestBody BatchDeleteRequest request) {
        classroomService.batchRestore(request.getIds());
        return Result.success();
    }

    @PostMapping("/admin/seats/batch-delete")
    @Operation(summary = "批量删除座位", description = "批量软删除座位")
    public Result<Void> batchDeleteSeats(@Valid @RequestBody BatchDeleteRequest request) {
        seatService.batchDelete(request.getIds());
        return Result.success();
    }

    @PostMapping("/admin/seats/batch-restore")
    @Operation(summary = "批量恢复座位", description = "批量恢复座位")
    public Result<Void> batchRestoreSeats(@Valid @RequestBody BatchDeleteRequest request) {
        seatService.batchRestore(request.getIds());
        return Result.success();
    }

}