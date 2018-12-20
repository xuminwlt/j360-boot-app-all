package me.j360.disboot.mapper;

import me.j360.disboot.model.domain.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author: min_xu
 * @date: 2018/12/20 3:13 PM
 * 说明：
 */
public interface UserMapper {

    @Select("select * from user where id = #{uid}")
    User getUserById(Long uid);
}
