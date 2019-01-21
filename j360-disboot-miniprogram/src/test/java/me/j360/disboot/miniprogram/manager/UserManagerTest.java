package me.j360.disboot.miniprogram.manager;

import me.j360.disboot.miniprogram.BaseJunitTest;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * UserManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class UserManagerTest extends BaseJunitTest {

    @Autowired
    private UserManager userManager;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: authGuest(String cid)
     */
    @Test
    public void testAuthGuest() throws Exception {
        userManager.authGuest("111");
    }

    /**
     * Method: authUser(String cid, String uuid)
     */
    @Test
    public void testAuthUser() throws Exception {
//TODO: Test goes here... 
    }


} 
