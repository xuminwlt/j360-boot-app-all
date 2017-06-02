package me.j360.disboot.manager;

import me.j360.disboot.model.domain.User;
import me.j360.disboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Package: me.j360.disboot.manager
 * User: min_xu
 * Date: 2017/6/1 下午6:33
 * 说明：
 */

@Component
public class UserManager {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public User getUserById(Long uid) {
        return userRepository.getUserById(uid);
    }
}
