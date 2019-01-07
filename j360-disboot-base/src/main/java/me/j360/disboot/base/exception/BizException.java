package me.j360.disboot.base.exception;

import lombok.ToString;
import me.j360.disboot.base.constant.BaseErrorCode;
import me.j360.disboot.base.util.ExceptionUtil;

/**
 * Package: me.j360.dubbo.base.exception
 * User: min_xu
 * Date: 16/8/23 下午2:01
 * 说明：业务异常,去除无关的trace信息增强性能,使用clone进行使用
 *  throw BizException.bizException.clone(BaseErrorCode baseErrorCode)
 */

@ToString
public class BizException extends RuntimeException implements Cloneable {

    //
    public static BizException bizException = ExceptionUtil.clearStackTrace(ExceptionUtil.setStackTrace(new BizException(0, ""), BizException.class, ""));

    private static final long serialVersionUID = -6438755184394143413L;

    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

    protected int exceptionCode = -1;
    protected String message;

    public int getExceptionCode() {
        return this.exceptionCode;
    }

    public BizException(int exceptionCode, String message) {
        super(message);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public BizException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getErrorMsg());
        this.message = baseErrorCode.getErrorMsg();
        this.exceptionCode = baseErrorCode.getErrorCode();
    }

    public BizException(int exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public BizException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode.getErrorMsg(), cause);
        this.message = baseErrorCode.getErrorMsg();
        this.exceptionCode = baseErrorCode.getErrorCode();
    }

    @Override
    public BizException clone() {
        try {
            return (BizException) super.clone();
        } catch (CloneNotSupportedException e) { // NOSONAR
            return null;
        }
    }

    public BizException clone(String message) {
        BizException newException = this.clone();
        newException.setMessage(message);
        return newException;
    }

    public BizException clone(int exceptionCode, String message) {
        BizException newException = this.clone();
        newException.setExceptionCode(exceptionCode);
        newException.setMessage(message);
        return newException;
    }

    public BizException clone(BaseErrorCode baseErrorCode) {
        BizException newException = this.clone();
        newException.setExceptionCode(baseErrorCode.getErrorCode());
        newException.setMessage(baseErrorCode.getErrorMsg());
        return newException;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }


}
