package me.j360.disboot.dao.mapper;

import me.j360.disboot.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Package: me.j360.disboot.dao.mapper
 * User: min_xu
 * Date: 2017/6/2 下午2:00
 * 说明：使用Mapper接口调用
 */

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM User WHERE uid = #{uid}")
    User getUserById(@Param("state") Long uid);


}
