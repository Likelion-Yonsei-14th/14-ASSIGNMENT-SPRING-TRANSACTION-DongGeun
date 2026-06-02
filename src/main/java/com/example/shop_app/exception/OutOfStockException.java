package com.example.shop_app.exception;

public class OutOfStockException extends CustomException {
    public OutOfStockException() {
        super(ErrorCode.OUT_OF_STOCK);
    }
}
