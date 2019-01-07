package me.j360.disboot;

import me.j360.disboot.base.constant.DefaultErrorCode;
import me.j360.disboot.base.exception.BizException;
import org.junit.Test;

/**
 * @author: min_xu
 * @date: 2019/1/7 5:49 PM
 * 说明：
 */
public class BizExceptionTest {

    @Test
    public void throwTimeTest() {
        long start = (System.currentTimeMillis());
        BizException bizException = BizException.bizException;
        System.out.println(System.currentTimeMillis()  - start);
        for (int i= 0;i < 1000; i++) {
            try {
                throw bizException.clone(DefaultErrorCode.SYS_ERROR);
            } catch (Exception e) {
                //System.out.println(e);
            }
        }
        System.out.println(System.currentTimeMillis()  - start);
        for (int i= 0;i < 1000; i++) {
            try {
                throw new BizException(DefaultErrorCode.SYS_ERROR);
            } catch (Exception e) {
                //System.out.println(e);
            }
        }
        System.out.println(System.currentTimeMillis()  - start);
    }
}
