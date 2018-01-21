package cn.irving.zhao.util.remote.mina.core.paired;

import java.util.UUID;

public class PairedMessageLock<T> {

    public PairedMessageLock(Class<T> resultType) {
        this.resultType = resultType;
    }

    private T result;

    private RuntimeException exception;

    private Class<T> resultType;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Class<T> getResultType() {
        return resultType;
    }

    public void setResultType(Class<T> resultType) {
        this.resultType = resultType;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }
}
