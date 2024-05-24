package com.elfyntan.uni_app.exceptions;

public enum ErrorCode {
    INVALID_ID("ERR001", "Invalid ID"),
    EMAIL_CONFLICT("ERR002", "Email address is already in use"),
    BAD_REQUEST("ERR003", "Bad request"),
    INTERNAL_SERVER_ERROR("ERR004", "An unexpected error occurred");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
