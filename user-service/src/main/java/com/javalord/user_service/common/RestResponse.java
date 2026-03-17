package com.javalord.user_service.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestResponse<T> {

    private Status status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public RestResponse(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
