package org.ganaccity.tests;

import org.ganaccity.tests.dao.DAOTests;
import org.ganaccity.tests.viewmdl.VMdlTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ VMdlTests.class,
                DAOTests.class})
public class AllTests {

}
