package org.example.hightrafficproject.api.common;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("BAD_REQUEST", e.getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class, OptimisticLockingFailureException.class})
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException e) {
        String code = (e instanceof OptimisticLockingFailureException) ? "CONFLICT_RETRY" : "OUT_OF_STOCK";
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(code, e.getMessage()));
    }

    // Fallback: 기타 예외는 기존처럼 500, 하지만 상세 스택은 로깅으로만 남기고 바디는 심플하게
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOthers(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("INTERNAL_ERROR", "서버 내부 오류가 발생했습니다."));
    }

    public static class ErrorResponse {
        public String code;
        public String message;
        public OffsetDateTime timestamp;
        public Map<String, Object> detail;

        public static ErrorResponse of(String code, String message) {
            ErrorResponse r = new ErrorResponse();
            r.code = code;
            r.message = message;
            r.timestamp = OffsetDateTime.now();
            r.detail = null;
            return r;
        }
    }
}
