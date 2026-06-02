package com.example.shop_app.exception;

public class InvalidOrderQuantityException extends CustomException {
    public InvalidOrderQuantityException() {
        super(ErrorCode.INVALID_ORDER_QUANTITY);
    }
}
