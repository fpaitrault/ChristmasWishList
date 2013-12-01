package org.ganaccity.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    AuthenticationServiceImplTest.class, 
    LoginTest.class, 
    MainWindowTest.class
    })
public class AllTests {

}
