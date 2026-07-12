package com.studyroom.service;

import com.studyroom.dto.request.LoginRequest;
import com.studyroom.dto.response.LoginResponse;
import com.studyroom.dto.response.UserInfoResponse;
import com.studyroom.entity.User;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout(String token);

    UserInfoResponse getUserInfo(Long userId);

    User getCurrentUser();

    User updateUserInfo(Long userId, java.util.Map<String, String> request);

}