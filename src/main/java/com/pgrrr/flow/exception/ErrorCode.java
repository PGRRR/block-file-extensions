package com.pgrrr.flow.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    DUPLICATE_EXTENSION(HttpStatus.BAD_REQUEST, "E001", "이미 추가된 확장자입니다."),
    EXCEED_EXTENSION_LIMIT(HttpStatus.BAD_REQUEST, "E002", "최대 추가 가능 개수는 200개입니다."),
    BLOCKED_MIME_TYPE(HttpStatus.BAD_REQUEST, "F001", "허용되지 않는 파일 종류입니다."),
    BLOCKED_EXTENSION(HttpStatus.BAD_REQUEST, "F002", "허용되지 않는 파일 확장자입니다."),
    UNAUTHORIZED_NAME(HttpStatus.BAD_REQUEST, "F003", "파일 이름에 제한된 문자가 포함되어 있습니다.");

    private final HttpStatus statusCode;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus statusCode, String errorCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return errorMessage;
    }
}
