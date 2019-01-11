package me.j360.disboot.constant;

import lombok.ToString;
import me.j360.framework.base.constant.DefaultErrorCode;

/**
 * 说明：
 */
@ToString
public class ErrorCode extends DefaultErrorCode {

    public ErrorCode(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }
    public static final ErrorCode PRODUCT_OFFLINE = new ErrorCode(10001,"商品已经下线");
}
