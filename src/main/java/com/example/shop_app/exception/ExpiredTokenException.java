package com.example.shop_app.exception;

public class ExpiredTokenException extends CustomException {
    
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
