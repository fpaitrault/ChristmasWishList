package org.ganaccity.tests.viewmdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.easymock.EasyMock;
import org.ganaccity.AuthenticationServiceImpl;
import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.interfaces.SessionService;
import org.ganaccity.interfaces.dao.SessionDAO;
import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.mdl.User;
import org.ganaccity.viewmdl.Login;
import org.ganaccity.viewmdl.MainWindow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.zkoss.zk.ui.Executions;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { MainWindow.class , Executions.class})
public class MainWindowTest {

    private static final String INCORRECT_PASSWORD = "INCORRECT";
    private static final String [] TEST_PASSWORD = {"testPassword", "testPassword 2"};
    private static final String [] TEST_USER_NAME = {"testUser", "testUser 2"};

    private MainWindow window = null;
    private AuthenticationService authService = null;
    private UserDAO users = null;
    private List<User> testUsers = null;

    @Test
    @Before
    public void TestSetup() {
        window = new MainWindow();
        PowerMock.mockStatic(Executions.class);
        authService = PowerMock.createMock(AuthenticationService.class);
        users  = PowerMock.createMock(UserDAO.class);
        ReflectionTestUtils.setField(window, "userDAO", users);
        ReflectionTestUtils.setField(window, "authService", authService);
        
        //Load reference list
        testUsers = new ArrayList<User>();
        for(int i=0;i<TEST_PASSWORD.length;i++) {
            testUsers.add(new User(TEST_USER_NAME[i], DigestUtils.md5Hex(TEST_PASSWORD[i]),i));
        }
    }

    @Test
    public void testGetUsers() {
        EasyMock.expect(users.readAll()).andReturn(testUsers);
        
        PowerMock.replayAll();

        assertEquals(window.getUsers(), testUsers);

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testLogout() {
        authService.logout();
        EasyMock.expectLastCall().once();
        Executions.sendRedirect("/login.zul");
        PowerMock.expectLastCall().once();
        
        PowerMock.replayAll();

        window.logout();

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testGetUser() {
        EasyMock.expect(authService.getUserCredential()).andReturn(testUsers.get(0));
        
        PowerMock.replayAll();

        assertEquals(window.getUser(), testUsers.get(0));

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
}
