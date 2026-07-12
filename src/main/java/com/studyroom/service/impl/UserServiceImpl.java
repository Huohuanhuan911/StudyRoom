package com.studyroom.service.impl;

import com.studyroom.common.BusinessException;
import com.studyroom.dto.request.UserRequest;
import com.studyroom.dto.request.UserUpdateRequest;
import com.studyroom.entity.User;
import com.studyroom.repository.UserRepository;
import com.studyroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findByDeletedFalse();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在"));
    }

    @Override
    @Transactional
    public User createUser(UserRequest request) {
        String username = request.getUsername();
        if (username == null || username.isEmpty()) {
            username = request.getStudentId();
        }
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException("用户名已存在");
        }

        String pwd = request.getPassword() != null && !request.getPassword().isEmpty() 
                ? request.getPassword() : "123456";
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(pwd))
                .name(request.getName())
                .studentId(request.getStudentId())
                .role(request.getRole() != null ? request.getRole() : "STUDENT")
                .creditScore(request.getCreditScore() != null ? request.getCreditScore() : 100)
                .violationCount(0)
                .phone(request.getPhone())
                .email(request.getEmail())
                .college(request.getCollege())
                .major(request.getMajor())
                .grade(request.getGrade())
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserUpdateRequest request) {
        User user = getUserById(id);

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getCollege() != null) {
            user.setCollege(request.getCollege());
        }
        if (request.getMajor() != null) {
            user.setMajor(request.getMajor());
        }
        if (request.getGrade() != null) {
            user.setGrade(request.getGrade());
        }
        if (request.getInBlacklist() != null) {
            user.setBlacklistExpire(request.getInBlacklist() ? (request.getBlacklistExpireAt() != null ? request.getBlacklistExpireAt() : LocalDateTime.now().plusYears(100)) : null);
        }
        if (request.getBlacklistReason() != null) {
            user.setBlacklistReason(request.getBlacklistReason());
        }
        if (request.getViolationCount() != null) {
            user.setViolationCount(request.getViolationCount());
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {
        User user = getUserById(id);
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public List<User> getBlacklistedUsers() {
        return userRepository.findBlacklistedUsers(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void removeFromBlacklist(Long id) {
        User user = getUserById(id);
        user.setBlacklistExpire(null);
        user.setViolationCount(0);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void batchImportUsers(List<UserRequest> users) {
        for (UserRequest request : users) {
            if (!userRepository.existsByUsername(request.getUsername())) {
                User user = User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword() != null ? request.getPassword() : "123456"))
                        .name(request.getName())
                        .studentId(request.getStudentId())
                        .role(request.getRole() != null ? request.getRole() : "STUDENT")
                        .creditScore(request.getCreditScore() != null ? request.getCreditScore() : 100)
                        .violationCount(0)
                        .phone(request.getPhone())
                        .email(request.getEmail())
                        .college(request.getCollege())
                        .major(request.getMajor())
                        .grade(request.getGrade())
                        .build();
                userRepository.save(user);
            }
        }
    }

    @Override
    @Transactional
    public User addToBlacklist(Long id, String reason) {
        User user = getUserById(id);
        user.setBlacklistExpire(LocalDateTime.now().plusYears(100));
        user.setBlacklistReason(reason);
        user.setCreditScore(0);
        user.setViolationCount(3);
        return userRepository.save(user);
    }

}