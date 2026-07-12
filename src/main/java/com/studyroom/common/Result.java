package com.studyroom.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Boolean success;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return Result.<T>builder()
                .success(true)
                .message("操作成功")
                .data(null)
                .build();
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .success(true)
                .message("操作成功")
                .data(data)
                .build();
    }

    public static <T> Result<T> success(String message, T data) {
        return Result.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> Result<T> error(String message) {
        return Result.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> Result<T> error(String message, T data) {
        return Result.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }

}