package com.security_jwt.dto;

public class BaseResponseDTO<T> {

    private Integer success;
    private String message;
    private T data;

    public BaseResponseDTO() {
    }

    public BaseResponseDTO(Integer success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
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
