package com.sparta_logistics.auth.application.global.exception;


import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthException(ErrorCode message) {
        super(message.getMessage());
        this.errorCode = message;
    }

}
