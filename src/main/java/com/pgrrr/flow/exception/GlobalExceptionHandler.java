package com.pgrrr.flow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 모든 서버 예외 처리
     *
     * @param e 모든 예외
     * @return ResponseEntity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleInternalServerError(Throwable e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    /**
     * Valid 검증 예외는 ExceptionHandler로 처리
     *
     * @param e 유효하지 않는 데이터인 경우 발생
     * @return ResponseEntity 에러 메시지 나열
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage());
        }
        String message = builder.toString();
        return ResponseEntity.badRequest().body(message);
    }

    /**
     * 중복 예외 처리
     *
     * @param e 중복 되는 데이터인 경우 발생
     * @return ResponseEntity
     */
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<String> handleDuplicateException(DuplicateException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorCode.getMessage());
    }

    /**
     * 파일 관련 에러 처리
     *
     * @param e 파일 관련 에러인 경우 발생
     * @return ResponseEntity
     */
    @ExceptionHandler(FileTypeCheckException.class)
    public ResponseEntity<String> handleFileException(FileTypeCheckException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorCode.getMessage());
    }
}
