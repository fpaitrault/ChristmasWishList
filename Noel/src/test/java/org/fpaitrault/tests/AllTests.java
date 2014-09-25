package org.fpaitrault.tests;

import org.fpaitrault.tests.dao.DAOTests;
import org.fpaitrault.tests.mailing.EMailFactoryTest;
import org.fpaitrault.tests.viewmdl.VMdlTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ VMdlTests.class,
                DAOTests.class,
                EMailFactoryTest.class})
public class AllTests {

}
