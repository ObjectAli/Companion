package com.companion.web.model;

import com.companion.common.error.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final int statusCode;
    private final List<ErrorDetail> errors;
    private final LocalDateTime timestamp;
    private final String path;

    private ApiResponse(Builder<T> builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.data = builder.data;
        this.statusCode = builder.statusCode;
        this.errors = builder.errors;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
        this.path = builder.path;
    }

    // Статический метод для старта билдера
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    // Статические методы для быстрого создания успешных/ошибочных ответов
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .statusCode(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .statusCode(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(int statusCode, String message, List<ErrorDetail> errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .errors(errors)
                .build();
    }

    // Внутренний класс Builder
    public static class Builder<T> {
        private boolean success;
        private String message;
        private T data;
        private int statusCode;
        private List<ErrorDetail> errors;
        private LocalDateTime timestamp;
        private String path;

        public Builder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> errors(List<ErrorDetail> errors) {
            this.errors = errors;
            return this;
        }

        public Builder<T> timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(this);
        }
    }
}
