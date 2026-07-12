package com.studyroom.controller;

import com.studyroom.common.Result;
import com.studyroom.dto.request.LoginRequest;
import com.studyroom.dto.response.LoginResponse;
import com.studyroom.dto.response.UserInfoResponse;
import com.studyroom.entity.User;
import com.studyroom.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/auth", "/auth"})
@RequiredArgsConstructor
@Tag(name = "认证接口", description = "登录、登出、用户信息")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "登录", description = "用户名密码登录，返回token和用户信息")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    @Operation(summary = "登出", description = "退出登录")
    public Result<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            authService.logout(token.substring(7));
        }
        return Result.success();
    }

    @GetMapping("/userinfo")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户信息")
    public Result<UserInfoResponse> getUserInfo() {
        User user = authService.getCurrentUser();
        UserInfoResponse response = authService.getUserInfo(user.getId());
        return Result.success(response);
    }

    @PutMapping("/userinfo")
    @Operation(summary = "更新个人信息", description = "学生端更新自己的个人信息")
    public Result<UserInfoResponse> updateUserInfo(@RequestBody java.util.Map<String, String> request) {
        User user = authService.getCurrentUser();
        User updatedUser = authService.updateUserInfo(user.getId(), request);
        UserInfoResponse response = authService.getUserInfo(updatedUser.getId());
        return Result.success(response);
    }

}