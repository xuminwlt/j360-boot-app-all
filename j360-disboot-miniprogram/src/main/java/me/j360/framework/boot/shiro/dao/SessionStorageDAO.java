package me.j360.framework.boot.shiro.dao;

import me.j360.framework.common.web.context.SessionUser;

/**
 * @author: min_xu
 * @date: 2019/1/11 4:30 PM
 * 说明：
 */
public interface SessionStorageDAO {

    void save(SessionUser sessionUser);

    SessionUser get(String sessionId);
}
