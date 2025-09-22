package org.example.hightrafficproject.common.error;

import java.util.Map;

public class BusinessException extends RuntimeException {
    private final ErrorCode code;
    private final Map<String, Object> detail;

    public BusinessException(ErrorCode code) {
        super(code.getDefaultMessage());
        this.code = code;
        this.detail = null;
    }

    public BusinessException(ErrorCode code, String message) {
        super(message);
        this.code = code;
        this.detail = null;
    }

    public BusinessException(ErrorCode code, String message, Map<String, Object> detail) {
        super(message);
        this.code = code;
        this.detail = detail;
    }

    public ErrorCode getCode() {
        return code;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }
}
