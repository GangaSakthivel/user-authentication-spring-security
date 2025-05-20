package com.security_jwt.dto;


public class BaseResponseDTO<T> {

    private Integer success;
    private String message;
    private T data;

}
