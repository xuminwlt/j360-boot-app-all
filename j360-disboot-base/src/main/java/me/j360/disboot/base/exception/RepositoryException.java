package me.j360.disboot.base.exception;

import lombok.ToString;
import me.j360.disboot.base.constant.BaseErrorCode;


@ToString
public class RepositoryException extends RuntimeException {


    private static final long serialVersionUID = -6438755184394143413L;

    protected int exceptionCode = -1;

    public int getExceptionCode() {
        return this.exceptionCode;
    }

    public RepositoryException(int exceptionCode,String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public RepositoryException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getErrorMsg());
        this.exceptionCode = baseErrorCode.getErrorCode();
    }

    public RepositoryException(int exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public RepositoryException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode.getErrorMsg(), cause);
        this.exceptionCode = baseErrorCode.getErrorCode();
    }
}
