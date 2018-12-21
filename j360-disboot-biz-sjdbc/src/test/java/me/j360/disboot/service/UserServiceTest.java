package me.j360.disboot.service;

import me.j360.disboot.biz.bootstrap.BootstrapApplication;
import me.j360.disboot.biz.manager.UserManager;
import me.j360.disboot.model.domain.User;
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
@ActiveProfiles("local")
public class UserServiceTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void getUserById() {
        User user = userManager.getUserById(1L);
        Assert.assertNotNull(user);
    }
}
