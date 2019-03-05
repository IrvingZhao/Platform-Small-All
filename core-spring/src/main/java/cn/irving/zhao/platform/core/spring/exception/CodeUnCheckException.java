package cn.irving.zhao.platform.core.spring.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 带有错误编码的非检查异常
 */
@Getter
@Setter
public class CodeUnCheckException extends RuntimeException implements CodeException {

    private String code;

    public CodeUnCheckException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public CodeUnCheckException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getCode(), cause);
        this.code = errorCode.getCode();
    }

    public CodeUnCheckException(ErrorCode errorCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode.getMsg(), cause, enableSuppression, writableStackTrace);
        this.code = errorCode.getCode();
    }

    public CodeUnCheckException(String code) {
        this.code = code;
    }

    public CodeUnCheckException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CodeUnCheckException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CodeUnCheckException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public CodeUnCheckException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
