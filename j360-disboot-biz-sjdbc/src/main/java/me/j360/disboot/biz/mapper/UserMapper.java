package me.j360.disboot.biz.mapper;

import me.j360.disboot.model.domain.User;
import me.j360.disboot.model.domain.UserAccountLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author: min_xu
 * @date: 2018/12/20 3:13 PM
 * 说明：
 */
public interface UserMapper {

    @Select("select * from t_user where uid = #{uid}")
    User getUserById(Long uid);

    @Insert("INSERT INTO `t_user` (`name`) VALUES " +
            "( #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int insert(User user);

    @Insert("INSERT INTO `t_user_account_log` (`uid`) VALUES " +
            "( #{uid})")
    @Options(useGeneratedKeys = true, keyProperty = "log_id")
    int insertAccount(UserAccountLog log);

}
