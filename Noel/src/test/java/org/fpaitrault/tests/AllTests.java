package org.fpaitrault.tests;

import org.fpaitrault.tests.dao.DAOTests;
import org.fpaitrault.tests.viewmdl.VMdlTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ VMdlTests.class,
                DAOTests.class})
public class AllTests {

}
