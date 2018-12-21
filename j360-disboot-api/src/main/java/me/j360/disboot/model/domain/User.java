package me.j360.disboot.model.domain;

import lombok.Data;
import me.j360.disboot.base.domain.model.BaseDO;

/**
 * Package: me.j360.disboot.model.domain
 * User: min_xu
 * Date: 2017/6/2 下午2:01
 * 说明：
 */
@Data
public class User extends BaseDO {

    private Long id;
    private String name;
}
