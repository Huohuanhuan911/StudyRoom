package com.studyroom.controller;

import com.studyroom.common.Result;
import com.studyroom.dto.request.BookingRequest;
import com.studyroom.dto.response.BookingResponse;
import com.studyroom.dto.response.ViolationResponse;
import com.studyroom.entity.Booking;
import com.studyroom.entity.User;
import com.studyroom.service.AuthService;
import com.studyroom.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/booking", "/booking"})
@RequiredArgsConstructor
@Tag(name = "预约接口", description = "预约管理")
public class BookingController {

    private final BookingService bookingService;
    private final AuthService authService;

    @GetMapping("/list")
    @Operation(summary = "预约列表", description = "获取所有预约（管理员）")
    public Result<List<BookingResponse>> getAllBookings() {
        return Result.success(bookingService.getAllBookings());
    }

    @GetMapping("/my")
    @Operation(summary = "我的预约", description = "获取当前用户的预约")
    public Result<List<BookingResponse>> getMyBookings() {
        User user = authService.getCurrentUser();
        return Result.success(bookingService.getMyBookings(user.getId()));
    }

    @PostMapping("/create")
    @Operation(summary = "创建预约", description = "创建新预约")
    public Result<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        User user = authService.getCurrentUser();
        Booking booking = bookingService.createBooking(user.getId(), request);
        return Result.success(BookingResponse.fromEntity(booking));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "取消预约", description = "取消预约")
    public Result<Void> cancelBooking(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        bookingService.cancelBooking(id, user.getId());
        return Result.success();
    }

    @PostMapping("/{id}/signin")
    @Operation(summary = "签到", description = "签到")
    public Result<Void> signIn(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        bookingService.signIn(id, user.getId());
        return Result.success();
    }

    @PostMapping("/{id}/release")
    @Operation(summary = "释放座位", description = "释放座位")
    public Result<Void> releaseSeat(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        bookingService.releaseSeat(id, user.getId());
        return Result.success();
    }

    @GetMapping("/my-violations")
    @Operation(summary = "我的违约记录", description = "获取当前用户的违约记录")
    public Result<List<ViolationResponse>> getMyViolations() {
        User user = authService.getCurrentUser();
        return Result.success(bookingService.getMyViolations(user.getId()));
    }

}