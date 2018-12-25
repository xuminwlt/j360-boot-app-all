package me.j360.disboot.biz.manager;

import kamon.Kamon;
import kamon.metric.StartedTimer;
import me.j360.disboot.biz.repository.UserRepository;
import me.j360.disboot.model.domain.User;
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
        //Kamon sample
        StartedTimer timer = Kamon.timer("getUserById").start();
        User user = userRepository.getUserById(uid);
        timer.stop();
        return user;
    }
}
