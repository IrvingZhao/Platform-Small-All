package cn.irving.zhao.util.poi.exception;

/**
 * excel 读取异常
 */
public class ImportException extends RuntimeException {
    public ImportException() {
    }

    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
