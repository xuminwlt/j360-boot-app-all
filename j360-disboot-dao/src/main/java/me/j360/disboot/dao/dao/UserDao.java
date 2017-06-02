package me.j360.disboot.dao.dao;

import me.j360.disboot.model.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Package: me.j360.disboot.dao.dao
 * User: min_xu
 * Date: 2017/6/2 下午2:03
 * 说明：使用SqlSession接口使用
 */

@Component
public class UserDao {

    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User selectUserById(long id) {
        return this.sqlSession.selectOne("selectUserById", id);
    }
}
