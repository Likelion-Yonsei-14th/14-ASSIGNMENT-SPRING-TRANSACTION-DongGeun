package com.example.shop_app.exception;

public class LoginFailedException extends CustomException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }
    
}
