package cn.irving.zhao.platform.core.spring.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeCheckException extends Exception implements CodeException {

    private String code;

    public CodeCheckException(String code) {
        this.code = code;
    }

    public CodeCheckException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CodeCheckException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CodeCheckException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public CodeCheckException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
