package org.fpaitrault.tests.viewmdl;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.fpaitrault.interfaces.AuthenticationService;
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
@PrepareForTest( { Login.class , Executions.class})
public class LoginTest {

    private static final String INCORRECT_PASSWORD = "INCORRECT";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String TEST_USER_NAME = "testUser";
    Login login = null;
    AuthenticationService authService = null;
    
    @Before
    public void TestSetup() {
        login = new Login();
        PowerMock.mockStatic(Executions.class);
        authService = PowerMock.createMock(AuthenticationService.class);
        ReflectionTestUtils.setField(login, "authService", authService);
    }

    @Test
    public void testGetFirstLogin() {
        login.setFirstLogin(true);
        assertTrue(login.getFirstLogin());
        
        login.setFirstLogin(false);
        assertFalse(login.getFirstLogin());
    }

    @Test
    public void testGetPasswdConfirm() {
        login.setPasswdConfirm(TEST_PASSWORD);
        assertTrue(login.getPasswdConfirm().equals(TEST_PASSWORD));
    }

    @Test
    public void testLogin_FirstLogin() {
        EasyMock.expect(authService.isFirstLogin(TEST_USER_NAME)).andReturn(true);
        PowerMock.replayAll();

        login.setName(TEST_USER_NAME);
        login.login();
        assertTrue(login.getFirstLogin());

        PowerMock.verifyAll();
    }
    
    enum PasswordSate { OK, INCORRECT, EMPTY };
    @Test
    public void testLogin_UserPwdVerif() {
        testLogin_UserPwdVerif(PasswordSate.OK);
        testLogin_UserPwdVerif(PasswordSate.INCORRECT);
        testLogin_UserPwdVerif(PasswordSate.EMPTY);        
    }
    private void testLogin_UserPwdVerif(PasswordSate passwdState) {
        String password;
        switch(passwdState) {
        case EMPTY:
            password = "";
            break;
        case INCORRECT:
            password = INCORRECT_PASSWORD;
            break;
        default:
            password = TEST_PASSWORD;
        }
        EasyMock.expect(authService.isFirstLogin(TEST_USER_NAME)).andReturn(false);
        EasyMock.expect(authService.login(TEST_USER_NAME,password)).andReturn(
                passwdState==PasswordSate.OK?true:false);
        if(passwdState == PasswordSate.OK) {
            Executions.sendRedirect("/");
            PowerMock.expectLastCall().once();
        }
        PowerMock.replayAll();

        login.setName(TEST_USER_NAME);
        login.setPassword(password);
        login.login();
        assertFalse(login.getFirstLogin());

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
    
    @Test
    public void testFirstLogin_BadPwdRepeat() {
        PowerMock.replayAll();

        login.setPassword(TEST_PASSWORD);
        login.setPasswdConfirm(INCORRECT_PASSWORD);
        login.firstLogin();
        assertTrue(login.getPasswdErrorMsg().length()!=0);
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testFirstLogin_CorrectPwd() {
        testFirstLogin_CorrectPwd(true);
        testFirstLogin_CorrectPwd(false);
    }

    private void testFirstLogin_CorrectPwd(boolean loginSuccess) {
        authService.updatePassword(TEST_USER_NAME, TEST_PASSWORD);
        EasyMock.expectLastCall().once();
        EasyMock.expect(authService.login(TEST_USER_NAME,TEST_PASSWORD)).andReturn(loginSuccess);
        if(loginSuccess) {
            Executions.sendRedirect("/");
            PowerMock.expectLastCall().once();
        }
        PowerMock.replayAll();

        login.setName(TEST_USER_NAME);
        login.setPassword(TEST_PASSWORD);
        login.setPasswdConfirm(TEST_PASSWORD);
        login.firstLogin();
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
}
