package com.andr;

public enum ResultType {
    SUCCESS(0),
    UNKNOWN_LOGIN(1),
    INVALID_PASSWORD(2),
    UNKNOWN_ROLE(3),
    ACCESS_DENIED(4),
    INVALID_ACTIVITY(5);

    private final int code;
    ResultType(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
