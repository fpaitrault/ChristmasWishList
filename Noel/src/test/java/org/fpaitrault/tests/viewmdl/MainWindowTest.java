package org.fpaitrault.tests.viewmdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.easymock.EasyMock;
import org.fpaitrault.AuthenticationServiceImpl;
import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.SessionService;
import org.fpaitrault.interfaces.dao.SessionDAO;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mdl.User;
import org.fpaitrault.viewmdl.Login;
import org.fpaitrault.viewmdl.MainWindow;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.test.util.ReflectionTestUtils;
import org.zkoss.zk.ui.Executions;

@PrepareForTest( { MainWindow.class , Executions.class})
public class MainWindowTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

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
