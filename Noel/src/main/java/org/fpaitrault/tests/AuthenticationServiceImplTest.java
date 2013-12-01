package org.fpaitrault.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.easymock.EasyMock;
import org.fpaitrault.AuthenticationServiceImpl;
import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.SessionService;
import org.fpaitrault.interfaces.dao.SessionDAO;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mdl.Session;
import org.fpaitrault.mdl.User;
import org.fpaitrault.viewmdl.Login;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.zkoss.zk.ui.Executions;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { AuthenticationServiceImpl.class})
public class AuthenticationServiceImplTest {
    private static final String INCORRECT_PASSWORD = "INCORRECT";
    private static final String [] TEST_PASSWORD = {"testPassword", "testPassword 2"};
    private static final String [] TEST_USER_NAME = {"testUser", "testUser 2"};

    private AuthenticationServiceImpl authService = null;
    private UserDAO users = null;
    private SessionDAO sessions = null;
    private List<User> testUsers = null;
    private UUID uuid = UUID.randomUUID();
    private SessionService sessionService;

    @Before
    public void TestSetup() {
        authService = new AuthenticationServiceImpl();
        PowerMock.mockStatic(UUID.class);
        PowerMock.mockStatic(Session.class);
        users  = PowerMock.createMock(UserDAO.class);
        sessions   = PowerMock.createMock(SessionDAO.class);
        sessionService   = PowerMock.createMock(SessionService.class);
        ReflectionTestUtils.setField(authService, "users", users);
        ReflectionTestUtils.setField(authService, "session", sessions);
        ReflectionTestUtils.setField(authService, "sessionService", sessionService);
        
        //Load reference list
        testUsers = new ArrayList<User>();
        for(int i=0;i<TEST_PASSWORD.length;i++) {
            testUsers.add(new User(TEST_USER_NAME[i], DigestUtils.md5Hex(TEST_PASSWORD[i])));
        }
    }

    @Test
    public void testLogin_Success() throws Exception {
        testLogin(true); //Success
        testLogin(false); //Error
    }

    private void testLogin(boolean success) throws Exception {
        EasyMock.expect(users.readAll()).andReturn(testUsers);
        if(success) {
            EasyMock.expect(UUID.randomUUID()).andReturn(uuid);
            Session session = new Session(uuid.toString(),testUsers.get(0));
            PowerMock.expectNew(Session.class, uuid.toString(), testUsers.get(0)).andReturn(session);
            EasyMock.expect(sessions.create(session)).andReturn(0);
            sessionService.register(testUsers.get(0).getIndex(), uuid);
            EasyMock.expectLastCall().once();
        }
        PowerMock.replayAll();
        
        if(success) {
            assertTrue(authService.login(TEST_USER_NAME[0], TEST_PASSWORD[0]));
        } else {
            assertFalse(authService.login(TEST_USER_NAME[0],INCORRECT_PASSWORD));
        }

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testIsFirstLogin() {
        testIsFirstLogin(true);
        testIsFirstLogin(false);
    }

    private void testIsFirstLogin(boolean isfirst) {
        User user = null;
        if(isfirst) {
            user = new User(TEST_USER_NAME[0], "");
        } else {
            user = new User(TEST_USER_NAME[0], DigestUtils.md5Hex(TEST_PASSWORD[0]));
        }
        EasyMock.expect(users.readByUserName(TEST_USER_NAME[0])).andReturn(user);
        
        PowerMock.replayAll();
        
        if(isfirst) {
            assertTrue(authService.isFirstLogin(TEST_USER_NAME[0]));
        } else {
            assertFalse(authService.isFirstLogin(TEST_USER_NAME[0]));
        }

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testLogout() {
        EasyMock.expect(sessionService.getUUID()).andReturn(uuid);
        sessions.deleteSessionByUUID(uuid.toString());
        EasyMock.expectLastCall().once();
        sessionService.unregister();
        EasyMock.expectLastCall().once();

        PowerMock.replayAll();
        authService.logout();
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testGetUserCredential() {
        int userId = 456;
        EasyMock.expect(sessionService.getUserId()).andReturn(userId);
        EasyMock.expect(sessionService.getUserId()).andReturn(userId);
        EasyMock.expect(users.readById(userId)).andReturn(testUsers.get(0));

        PowerMock.replayAll();
        assertEquals(authService.getUserCredential(), testUsers.get(0));
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testUpdatePassword() {
        EasyMock.expect(users.readByUserName(TEST_USER_NAME[0])).andReturn(testUsers.get(0));
        users.update(testUsers.get(0));
        EasyMock.expectLastCall().once();

        PowerMock.replayAll();
        authService.updatePassword(TEST_USER_NAME[0], TEST_PASSWORD[0]);
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

}
