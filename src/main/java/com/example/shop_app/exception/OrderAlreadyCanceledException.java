package com.example.shop_app.exception;

public class OrderAlreadyCanceledException extends CustomException {
    public OrderAlreadyCanceledException() {
        super(ErrorCode.ORDER_ALREADY_CANCELED);
    }
}
