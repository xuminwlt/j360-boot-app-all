package me.j360.disboot.biz.repository;


import me.j360.disboot.biz.mapper.UserMapper;
import me.j360.disboot.model.domain.User;
import me.j360.disboot.model.domain.UserAccountLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Package: me.j360.disboot.repository
 * User: min_xu
 * Date: 2017/6/2 下午2:19
 * 说明：
 */

@Repository
public class UserRepository {


    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long uid) {
        return userMapper.getUserById(uid);
    }


    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int insertAccount(UserAccountLog userAccountLog) {
        return userMapper.insertAccount(userAccountLog);
    }
}
