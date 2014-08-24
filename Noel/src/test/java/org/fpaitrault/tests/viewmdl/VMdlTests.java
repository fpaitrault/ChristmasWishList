package org.fpaitrault.tests.viewmdl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    AuthenticationServiceImplTest.class, 
    LoginTest.class, 
    MainWindowTest.class,
    UserGridTest.class
    })
public class VMdlTests {

}
