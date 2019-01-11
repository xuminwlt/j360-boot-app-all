package me.j360.disboot.miniprogram.domain.response;

import lombok.Data;
import me.j360.framework.base.domain.BaseDO;

/**
 * @author: min_xu
 * @date: 2018/1/17 下午6:58
 * 说明：
 */

@Data
public class UserSessionResponse extends BaseDO {

    private String jwt;
    private boolean register;

}
