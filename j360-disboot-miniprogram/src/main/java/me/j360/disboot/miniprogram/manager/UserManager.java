package me.j360.disboot.miniprogram.manager;

import me.j360.framework.boot.shiro.JwtSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: min_xu
 * @date: 2019/1/11 1:39 PM
 * 说明：
 */

@Service
public class UserManager {

    @Autowired
    private JwtSignature jwtSignature;

    public String authGuest(String cid) {
        return jwtSignature.createGuest(cid);
    }

    public String authUser(String cid, String uuid) {
        return jwtSignature.createUser(cid, uuid);
    }
}
