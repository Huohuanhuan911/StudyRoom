package com.studyroom.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studyroom.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    @JsonProperty("studentId")
    private String studentId;

    private String name;

    private String role;

    private Integer creditScore;

    private Integer violationCount;

    @JsonProperty("inBlacklist")
    private Boolean inBlacklist;

    @JsonProperty("blacklistReason")
    private String blacklistReason;

    @JsonProperty("blacklistExpireAt")
    private LocalDateTime blacklistExpire;

    private String phone;

    private String email;

    private String college;

    private String major;

    private String grade;

    private LocalDateTime createdAt;

    private Boolean deleted;

    private LocalDateTime deletedAt;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .studentId(user.getStudentId())
                .name(user.getName())
                .role(user.getRole() != null ? user.getRole().toLowerCase() : null)
                .creditScore(user.getCreditScore())
                .violationCount(user.getViolationCount())
                .inBlacklist(user.isBlacklisted())
                .blacklistReason(user.getBlacklistReason())
                .blacklistExpire(user.getBlacklistExpire())
                .phone(user.getPhone())
                .email(user.getEmail())
                .college(user.getCollege())
                .major(user.getMajor())
                .grade(user.getGrade())
                .createdAt(user.getCreatedAt())
                .deleted(user.getDeleted())
                .deletedAt(user.getDeletedAt())
                .build();
    }

}