package com.example.shop_app.exception;

public class ForbiddenException extends CustomException {
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}
