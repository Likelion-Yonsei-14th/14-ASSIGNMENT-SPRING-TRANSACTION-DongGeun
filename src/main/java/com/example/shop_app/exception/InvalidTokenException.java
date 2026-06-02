package com.example.shop_app.exception;

public class InvalidTokenException extends CustomException {
    
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
