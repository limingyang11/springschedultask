package com.my.springtask.utils;

public class ResultException extends RuntimeException {
    protected int code;

    public ResultException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ResultException(String messsage) {
        super(messsage);
    }

    public ResultException() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
