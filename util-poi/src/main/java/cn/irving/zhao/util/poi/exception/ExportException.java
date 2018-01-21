package cn.irving.zhao.util.poi.exception;

/**
 * excel导出异常
 */
public class ExportException extends Exception {
    public ExportException() {
    }

    public ExportException(String message) {
        super(message);
    }

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
