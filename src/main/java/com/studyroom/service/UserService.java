package com.studyroom.service;

import com.studyroom.dto.request.UserRequest;
import com.studyroom.dto.request.UserUpdateRequest;
import com.studyroom.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(UserRequest request);

    User updateUser(Long id, UserUpdateRequest request);

    User deleteUser(Long id);

    List<User> getBlacklistedUsers();

    void removeFromBlacklist(Long id);

    void batchImportUsers(List<UserRequest> users);

    User addToBlacklist(Long id, String reason);

}