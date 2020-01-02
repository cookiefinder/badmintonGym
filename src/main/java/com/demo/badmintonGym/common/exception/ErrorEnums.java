package com.demo.badmintonGym.common.exception;

public enum  ErrorEnums {

    INVALID_INPUT("the booking is invalid!"),
    TIME_CONFLICT("the booking conflicts with existing bookings!"),
    NOT_EXIST("the booking being cancelled does not exist"),
    FILE_NOT_FOUND("file is not found"),
    ;

    private String msg;

    ErrorEnums(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
