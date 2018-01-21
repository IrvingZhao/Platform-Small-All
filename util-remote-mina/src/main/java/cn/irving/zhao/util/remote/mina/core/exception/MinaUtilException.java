package cn.irving.zhao.util.remote.mina.core.exception;

/**
 * mina 异常
 */
public class MinaUtilException extends RuntimeException {

    public MinaUtilException() {
    }

    public MinaUtilException(String message) {
        super(message);
    }

    public MinaUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinaUtilException(Throwable cause) {
        super(cause);
    }

    public MinaUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
