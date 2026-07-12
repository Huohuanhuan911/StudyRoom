package com.studyroom.controller;

import com.studyroom.common.Result;
import com.studyroom.dto.request.*;
import com.studyroom.dto.response.*;
import com.studyroom.entity.Building;
import com.studyroom.entity.Classroom;
import com.studyroom.entity.Seat;
import com.studyroom.entity.User;
import com.studyroom.service.BuildingService;
import com.studyroom.service.ClassroomService;
import com.studyroom.service.ReportService;
import com.studyroom.service.SeatService;
import com.studyroom.service.SystemSettingService;
import com.studyroom.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/admin", "/admin"})
@RequiredArgsConstructor
@Tag(name = "管理接口", description = "用户管理、楼栋管理、教室管理、座位管理、系统设置、数据报表")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final BuildingService buildingService;
    private final ClassroomService classroomService;
    private final SeatService seatService;
    private final ReportService reportService;
    private final SystemSettingService systemSettingService;

    @GetMapping("/users")
    @Operation(summary = "用户列表", description = "获取所有用户")
    public Result<List<UserResponse>> getUsers() {
        return Result.success(userService.getAllUsers().stream()
                .map(UserResponse::fromEntity)
                .toList());
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "用户详情", description = "获取用户详情")
    public Result<UserResponse> getUserById(@PathVariable Long id) {
        return Result.success(UserResponse.fromEntity(userService.getUserById(id)));
    }

    @PostMapping("/users")
    @Operation(summary = "创建用户", description = "创建新用户")
    public Result<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return Result.success(UserResponse.fromEntity(userService.createUser(request)));
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "编辑用户", description = "编辑用户信息")
    public Result<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return Result.success(UserResponse.fromEntity(userService.updateUser(id, request)));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "删除用户", description = "软删除用户")
    public Result<UserResponse> deleteUser(@PathVariable Long id) {
        User user = userService.deleteUser(id);
        return Result.success(UserResponse.fromEntity(user));
    }

    @PostMapping("/users/import")
    @Operation(summary = "批量导入用户", description = "批量导入学生用户")
    public Result<Map<String, Object>> importUsers(@RequestBody List<UserImportRequest> users) {
        int success = 0;
        int failed = 0;
        for (UserImportRequest request : users) {
            try {
                UserRequest userRequest = new UserRequest();
                userRequest.setUsername(request.getStudentId());
                userRequest.setPassword("123456");
                userRequest.setName(request.getName());
                userRequest.setRole("STUDENT");
                userRequest.setPhone(request.getPhone());
                userRequest.setEmail(request.getEmail());
                userRequest.setCollege(request.getCollege());
                userRequest.setMajor(request.getMajor());
                userRequest.setGrade(request.getGrade());
                userRequest.setCreditScore(request.getCreditScore() != null ? request.getCreditScore() : 100);
                userService.createUser(userRequest);
                success++;
            } catch (Exception e) {
                failed++;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("failed", failed);
        return Result.success(result);
    }

    @GetMapping("/blacklist")
    @Operation(summary = "黑名单列表", description = "获取黑名单用户")
    public Result<List<UserResponse>> getBlacklist() {
        return Result.success(userService.getBlacklistedUsers().stream()
                .map(UserResponse::fromEntity)
                .toList());
    }

    @PostMapping("/users/{id}/remove-blacklist")
    @Operation(summary = "解除黑名单", description = "解除用户黑名单")
    public Result<Void> removeFromBlacklist(@PathVariable Long id) {
        userService.removeFromBlacklist(id);
        return Result.success();
    }

    @GetMapping("/buildings")
    @Operation(summary = "楼栋列表", description = "获取所有楼栋")
    public Result<List<BuildingResponse>> getBuildings() {
        return Result.success(buildingService.getAllBuildings().stream()
                .map(BuildingResponse::fromEntity)
                .toList());
    }

    @GetMapping("/buildings/{id}")
    @Operation(summary = "楼栋详情", description = "获取楼栋详情")
    public Result<BuildingResponse> getBuildingById(@PathVariable Long id) {
        return Result.success(BuildingResponse.fromEntity(buildingService.getBuildingById(id)));
    }

    @PostMapping("/buildings")
    @Operation(summary = "创建楼栋", description = "创建新楼栋")
    public Result<BuildingResponse> createBuilding(@Valid @RequestBody BuildingRequest request) {
        return Result.success(BuildingResponse.fromEntity(buildingService.createBuilding(request)));
    }

    @PutMapping("/buildings/{id}")
    @Operation(summary = "编辑楼栋", description = "编辑楼栋信息")
    public Result<BuildingResponse> updateBuilding(@PathVariable Long id, @Valid @RequestBody BuildingRequest request) {
        return Result.success(BuildingResponse.fromEntity(buildingService.updateBuilding(id, request)));
    }

    @DeleteMapping("/buildings/{id}")
    @Operation(summary = "删除楼栋", description = "软删除楼栋")
    public Result<BuildingResponse> deleteBuilding(@PathVariable Long id) {
        Building building = buildingService.deleteBuilding(id);
        return Result.success(BuildingResponse.fromEntity(building));
    }

    @PostMapping("/buildings/{id}/restore")
    @Operation(summary = "恢复楼栋", description = "恢复已删除楼栋")
    public Result<BuildingResponse> restoreBuilding(@PathVariable Long id) {
        Building building = buildingService.restoreBuilding(id);
        return Result.success(BuildingResponse.fromEntity(building));
    }

    @PostMapping("/buildings/batch-delete")
    @Operation(summary = "批量删除楼栋", description = "批量软删除楼栋")
    public Result<Void> batchDeleteBuildings(@RequestBody Map<String, List<Long>> body) {
        buildingService.batchDelete(body.get("ids"));
        return Result.success();
    }

    @PostMapping("/buildings/batch-restore")
    @Operation(summary = "批量恢复楼栋", description = "批量恢复楼栋")
    public Result<Void> batchRestoreBuildings(@RequestBody Map<String, List<Long>> body) {
        buildingService.batchRestore(body.get("ids"));
        return Result.success();
    }

    @GetMapping("/classrooms")
    @Operation(summary = "教室列表", description = "获取所有教室")
    public Result<List<ClassroomResponse>> getClassrooms(
            @RequestParam(required = false) Long buildingId) {
        if (buildingId != null) {
            return Result.success(classroomService.getClassroomsByBuilding(buildingId).stream()
                    .map(ClassroomResponse::fromEntity)
                    .toList());
        }
        return Result.success(classroomService.getAllClassrooms().stream()
                .map(ClassroomResponse::fromEntity)
                .toList());
    }

    @GetMapping("/classrooms/{id}")
    @Operation(summary = "教室详情", description = "获取教室详情")
    public Result<ClassroomResponse> getClassroomById(@PathVariable Long id) {
        return Result.success(ClassroomResponse.fromEntity(classroomService.getClassroomById(id)));
    }

    @PostMapping("/classrooms")
    @Operation(summary = "创建教室", description = "创建新教室")
    public Result<ClassroomResponse> createClassroom(@Valid @RequestBody ClassroomRequest request) {
        return Result.success(ClassroomResponse.fromEntity(classroomService.createClassroom(request)));
    }

    @PutMapping("/classrooms/{id}")
    @Operation(summary = "编辑教室", description = "编辑教室信息")
    public Result<ClassroomResponse> updateClassroom(@PathVariable Long id, @Valid @RequestBody ClassroomRequest request) {
        return Result.success(ClassroomResponse.fromEntity(classroomService.updateClassroom(id, request)));
    }

    @DeleteMapping("/classrooms/{id}")
    @Operation(summary = "删除教室", description = "软删除教室")
    public Result<ClassroomResponse> deleteClassroom(@PathVariable Long id) {
        Classroom classroom = classroomService.deleteClassroom(id);
        return Result.success(ClassroomResponse.fromEntity(classroom));
    }

    @PostMapping("/classrooms/{id}/restore")
    @Operation(summary = "恢复教室", description = "恢复已删除教室")
    public Result<ClassroomResponse> restoreClassroom(@PathVariable Long id) {
        Classroom classroom = classroomService.restoreClassroom(id);
        return Result.success(ClassroomResponse.fromEntity(classroom));
    }

    @PostMapping("/classrooms/batch-delete")
    @Operation(summary = "批量删除教室", description = "批量软删除教室")
    public Result<Void> batchDeleteClassrooms(@RequestBody Map<String, List<Long>> body) {
        classroomService.batchDelete(body.get("ids"));
        return Result.success();
    }

    @PostMapping("/classrooms/batch-restore")
    @Operation(summary = "批量恢复教室", description = "批量恢复教室")
    public Result<Void> batchRestoreClassrooms(@RequestBody Map<String, List<Long>> body) {
        classroomService.batchRestore(body.get("ids"));
        return Result.success();
    }

    @GetMapping("/seats")
    @Operation(summary = "座位列表", description = "获取所有座位")
    public Result<List<SeatResponse>> getSeats(
            @RequestParam(required = false) Long classroomId) {
        if (classroomId != null) {
            return Result.success(seatService.getSeatsByClassroom(classroomId).stream()
                    .map(SeatResponse::fromEntity)
                    .toList());
        }
        return Result.success(seatService.getAllSeats().stream()
                .map(SeatResponse::fromEntity)
                .toList());
    }

    @GetMapping("/seats/{id}")
    @Operation(summary = "座位详情", description = "获取座位详情")
    public Result<SeatResponse> getSeatById(@PathVariable Long id) {
        return Result.success(SeatResponse.fromEntity(seatService.getSeatById(id)));
    }

    @PostMapping("/seats")
    @Operation(summary = "创建座位", description = "创建新座位")
    public Result<SeatResponse> createSeat(@Valid @RequestBody SeatRequest request) {
        return Result.success(SeatResponse.fromEntity(seatService.createSeat(request)));
    }

    @PutMapping("/seats/{id}")
    @Operation(summary = "编辑座位", description = "编辑座位信息")
    public Result<SeatResponse> updateSeat(@PathVariable Long id, @Valid @RequestBody SeatRequest request) {
        return Result.success(SeatResponse.fromEntity(seatService.updateSeat(id, request)));
    }

    @DeleteMapping("/seats/{id}")
    @Operation(summary = "删除座位", description = "软删除座位")
    public Result<SeatResponse> deleteSeat(@PathVariable Long id) {
        Seat seat = seatService.deleteSeat(id);
        return Result.success(SeatResponse.fromEntity(seat));
    }

    @PostMapping("/seats/{id}/restore")
    @Operation(summary = "恢复座位", description = "恢复已删除座位")
    public Result<SeatResponse> restoreSeat(@PathVariable Long id) {
        Seat seat = seatService.restoreSeat(id);
        return Result.success(SeatResponse.fromEntity(seat));
    }

    @PostMapping("/seats/import")
    @Operation(summary = "批量导入座位", description = "批量导入座位")
    public Result<Map<String, Object>> importSeats(
            @RequestParam Long classroomId,
            @RequestBody List<SeatImportRequest> seats) {
        int success = 0;
        int failed = 0;
        for (SeatImportRequest request : seats) {
            try {
                SeatRequest seatRequest = new SeatRequest();
                seatRequest.setClassroomId(classroomId);
                seatRequest.setSeatNumber(request.getSeatNumber());
                Integer row = request.getRow();
                Integer col = request.getCol();
                if (row != null && col != null) {
                    seatRequest.setRow(row);
                    seatRequest.setCol(col);
                } else {
                    String seatNo = request.getSeatNumber();
                    if (seatNo != null && seatNo.contains("-")) {
                        String[] parts = seatNo.split("-");
                        if (parts.length == 2) {
                            try {
                                int rowVal = parts[0].charAt(0) - 'A' + 1;
                                int colVal = Integer.parseInt(parts[1]);
                                seatRequest.setRow(rowVal);
                                seatRequest.setCol(colVal);
                            } catch (Exception e) {
                                seatRequest.setRow(1);
                                seatRequest.setCol(1);
                            }
                        }
                    } else {
                        seatRequest.setRow(1);
                        seatRequest.setCol(1);
                    }
                }
                seatRequest.setHasSocket(request.getHasSocket() != null && request.getHasSocket());
                seatService.createSeat(seatRequest);
                success++;
            } catch (Exception e) {
                failed++;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("failed", failed);
        return Result.success(result);
    }

    @PostMapping("/seats/batch-delete")
    @Operation(summary = "批量删除座位", description = "批量软删除座位")
    public Result<Void> batchDeleteSeats(@RequestBody Map<String, List<Long>> body) {
        seatService.batchDelete(body.get("ids"));
        return Result.success();
    }

    @PostMapping("/seats/batch-restore")
    @Operation(summary = "批量恢复座位", description = "批量恢复座位")
    public Result<Void> batchRestoreSeats(@RequestBody Map<String, List<Long>> body) {
        seatService.batchRestore(body.get("ids"));
        return Result.success();
    }

    @GetMapping("/reports")
    @Operation(summary = "报表数据", description = "获取统计数据")
    public Result<Map<String, Object>> getReports(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (startDate == null) startDate = LocalDate.now().minusDays(7);
        if (endDate == null) endDate = LocalDate.now();
        return Result.success(reportService.getReportData(startDate, endDate));
    }

    @GetMapping("/reports/export")
    @Operation(summary = "导出报表", description = "导出报表（CSV）")
    public ResponseEntity<byte[]> exportReports(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (startDate == null) startDate = LocalDate.now().minusDays(7);
        if (endDate == null) endDate = LocalDate.now();

        byte[] data = reportService.exportReport(startDate, endDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(data);
    }

    @PostMapping("/users/{id}/blacklist")
    @Operation(summary = "加入黑名单", description = "将用户加入黑名单")
    public Result<UserResponse> addToBlacklist(@PathVariable Long id, @RequestBody(required = false) Map<String, Object> body) {
        User user = userService.addToBlacklist(id, body != null ? (String) body.get("reason") : null);
        return Result.success(UserResponse.fromEntity(user));
    }

    @GetMapping("/buildings/deleted")
    @Operation(summary = "已删除楼栋", description = "获取已删除的楼栋列表")
    public Result<List<BuildingResponse>> getDeletedBuildings() {
        return Result.success(buildingService.getDeletedBuildings().stream()
                .map(BuildingResponse::fromEntity)
                .toList());
    }

    @GetMapping("/classrooms/deleted")
    @Operation(summary = "已删除教室", description = "获取已删除的教室列表")
    public Result<List<ClassroomResponse>> getDeletedClassrooms() {
        return Result.success(classroomService.getDeletedClassrooms().stream()
                .map(ClassroomResponse::fromEntity)
                .toList());
    }

    @GetMapping("/seats/deleted")
    @Operation(summary = "已删除座位", description = "获取已删除的座位列表")
    public Result<List<SeatResponse>> getDeletedSeats(@RequestParam(required = false) Long classroomId) {
        if (classroomId != null) {
            return Result.success(seatService.getDeletedSeatsByClassroom(classroomId).stream()
                    .map(SeatResponse::fromEntity)
                    .toList());
        }
        return Result.success(seatService.getDeletedSeats().stream()
                .map(SeatResponse::fromEntity)
                .toList());
    }

    @GetMapping("/settings")
    @Operation(summary = "系统设置", description = "获取系统设置")
    public Result<com.studyroom.dto.response.SettingsResponse> getSettings() {
        return Result.success(systemSettingService.getAllSettings());
    }

    @PutMapping("/settings")
    @Operation(summary = "保存系统设置", description = "保存系统设置")
    public Result<com.studyroom.dto.response.SettingsResponse> updateSettings(@RequestBody com.studyroom.dto.request.SettingsRequest settings) {
        return Result.success(systemSettingService.updateSettings(settings));
    }

}