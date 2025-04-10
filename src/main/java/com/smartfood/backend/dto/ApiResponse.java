package com.smartfood.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 通用的后端响应DTO（来自后端）
 */
@Schema(description = "通用后端响应DTO")
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
