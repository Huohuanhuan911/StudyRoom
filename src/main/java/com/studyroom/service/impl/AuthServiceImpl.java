package com.studyroom.service.impl;

import com.studyroom.common.BusinessException;
import com.studyroom.dto.request.LoginRequest;
import com.studyroom.dto.response.LoginResponse;
import com.studyroom.dto.response.UserInfoResponse;
import com.studyroom.entity.User;
import com.studyroom.repository.UserRepository;
import com.studyroom.service.AuthService;
import com.studyroom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        if (user.isBlacklisted()) {
            throw new BusinessException("您已被列入黑名单，禁止登录");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .user(UserInfoResponse.fromEntity(user))
                .build();
    }

    @Override
    public void logout(String token) {
        jwtUtil.invalidateToken(token);
    }

    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        return UserInfoResponse.fromEntity(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("未登录");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("用户不存在"));
    }

    @Override
    public User updateUserInfo(Long userId, java.util.Map<String, String> request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        
        if (request.containsKey("phone")) {
            user.setPhone(request.get("phone"));
        }
        if (request.containsKey("email")) {
            user.setEmail(request.get("email"));
        }
        
        return userRepository.save(user);
    }

}