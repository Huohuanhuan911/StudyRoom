package com.studyroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 50)
    private String name;

    @Column(length = 20)
    @Builder.Default
    private String role = "STUDENT";

    @Column(name = "student_id", length = 50)
    private String studentId;

    @Column(name = "credit_score")
    @Builder.Default
    private Integer creditScore = 100;

    @Column(name = "violation_count")
    @Builder.Default
    private Integer violationCount = 0;

    @Column(name = "blacklist_expire")
    private LocalDateTime blacklistExpire;

    @Column(name = "blacklist_reason", length = 255)
    private String blacklistReason;

    private String college;

    private String major;

    private String grade;

    private String email;

    private String phone;

    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isBlacklisted() {
        return blacklistExpire != null && blacklistExpire.isAfter(LocalDateTime.now());
    }

}