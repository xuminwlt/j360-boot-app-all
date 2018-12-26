package me.j360.disboot.service;

import me.j360.disboot.biz.bootstrap.BootstrapApplication;
import me.j360.disboot.biz.manager.UserManager;
import me.j360.disboot.model.domain.User;
import me.j360.disboot.model.domain.UserAccountLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: min_xu
 * @date: 2018/12/21 2:58 PM
 * 说明：
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootstrapApplication.class)
@ActiveProfiles("sharding")
public class UserServiceTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void getUserById() {
        User user = userManager.getUserById(1L);
        Assert.assertNotNull(user);
    }


    @Test
    public void insert() {
        User user = new User();
        //ser.setUid(1L);
        user.setName("1");
        userManager.insert(user);

        user = new User();
        //user.setUid(2L);
        user.setName("2");
        userManager.insert(user);
    }

    @Test
    public void insertAccount() {
        UserAccountLog accountLog = new UserAccountLog();
        accountLog.setUid(1L);
        userManager.insertAccount(accountLog);

        accountLog = new UserAccountLog();
        accountLog.setUid(2L);
        userManager.insertAccount(accountLog);
    }
}
