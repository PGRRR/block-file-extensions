package com.pgrrr.flow.exception;

public class ExceedException extends RuntimeException {
    private final ErrorCode errorCode;

    public ExceedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
