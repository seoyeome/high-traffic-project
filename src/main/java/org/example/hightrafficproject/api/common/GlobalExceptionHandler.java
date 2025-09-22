package org.example.hightrafficproject.api.common;

import org.example.hightrafficproject.common.error.BusinessException;
import org.example.hightrafficproject.common.error.ErrorCode;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        ErrorCode code = e.getCode();
        return ResponseEntity.status(code.getHttpStatus())
                .body(ErrorResponse.of(code.getCode(), e.getMessage(), e.getDetail()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ErrorCode.BAD_REQUEST.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler({IllegalStateException.class, OptimisticLockingFailureException.class})
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException e) {
        String code = (e instanceof OptimisticLockingFailureException) ?
                ErrorCode.CONFLICT_RETRY.getCode() : ErrorCode.OUT_OF_STOCK.getCode();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(code, e.getMessage(), null));
    }

    // Fallback: 기타 예외는 기존처럼 500, 하지만 상세 스택은 로깅으로만 남기고 바디는 심플하게
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOthers(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(ErrorCode.INTERNAL_ERROR.getCode(), "서버 내부 오류가 발생했습니다.", null));
    }

    public static class ErrorResponse {
        public String code;
        public String message;
        public OffsetDateTime timestamp;
        public Map<String, Object> detail;

        public static ErrorResponse of(String code, String message, Map<String, Object> detail) {
            ErrorResponse r = new ErrorResponse();
            r.code = code;
            r.message = message;
            r.timestamp = OffsetDateTime.now();
            r.detail = detail;
            return r;
        }
    }
}
