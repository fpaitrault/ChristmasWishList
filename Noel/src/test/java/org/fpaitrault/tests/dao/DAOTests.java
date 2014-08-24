package org.fpaitrault.tests.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ WishDAOImplTest.class,
                UserDAOImplTest.class,
                SessionDAOImplTest.class})
public class DAOTests {

}
