package com.pgrrr.flow.exception;

public class FileTypeCheckException extends RuntimeException{
    private final ErrorCode errorCode;

    public FileTypeCheckException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
