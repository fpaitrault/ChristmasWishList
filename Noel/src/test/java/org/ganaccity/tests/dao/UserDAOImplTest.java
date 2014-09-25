package org.ganaccity.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.ganaccity.dao.UserDAOImpl;
import org.ganaccity.mdl.User;
import org.ganaccity.tests.MdlFactory;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

@PrepareForTest( { UserDAOImpl.class} )
public class UserDAOImplTest {
    
    UserDAOImpl userDao = null;
    Session currentSession = null;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDAOImpl();
        currentSession = PowerMock.createMock(Session.class);
        ReflectionTestUtils.setField(userDao, "currentSession", currentSession);
    }

    @Test
    public void testReadByUserName() {
        List<User> users = MdlFactory.createUsers();
        Query query = PowerMock.createMock(Query.class);
        EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.User")).andReturn(query);
        EasyMock.expect(query.list()).andReturn(users);
        
        PowerMock.replayAll();
        
        User ok = userDao.readByUserName("Florent");
        assertNotNull(ok);
        assertEquals("Florent", ok.getUsername());
        assertEquals("toto", ok.getHash());
        
        User notOk = userDao.readByUserName("Unknown");
        assertNull(notOk);
            
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testReadAll() {
        List<User> users = MdlFactory.createUsers();
        Query query = PowerMock.createMock(Query.class);
        EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.User")).andReturn(query);
        EasyMock.expect(query.list()).andReturn(users);
        
        PowerMock.replayAll();
        
        List<User> readUsers = userDao.readAll();
        
        assertEquals(users.size(), readUsers.size());
        for(User user : users) {
            boolean found = false;
            for(User readUser : readUsers) {
                if(user.getUsername().equals(readUser.getUsername())) {
                    assertEquals(user.getUsername(), readUser.getUsername());
                    assertEquals(user.getHash(), readUser.getHash());
                    found = true;
                }
            }
            assertTrue(found);
        }
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
}
