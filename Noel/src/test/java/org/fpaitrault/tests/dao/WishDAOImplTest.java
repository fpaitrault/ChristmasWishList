package org.fpaitrault.tests.dao;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.fpaitrault.dao.WishDAOImpl;
import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.WishDAO;
import org.fpaitrault.mdl.User;
import org.fpaitrault.mdl.Wish;
import org.fpaitrault.tests.MdlFactory;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.springframework.test.util.ReflectionTestUtils;

public class WishDAOImplTest {

    private WishDAO wishDao = null;
    private Session currentSession = null;
    private AuthenticationService authService = null;

    @Before
    public void setUp() throws Exception {
        wishDao = new WishDAOImpl();
        currentSession = PowerMock.createMock(Session.class);
        authService = PowerMock.createMock(AuthenticationService.class);
        ReflectionTestUtils.setField(wishDao, "currentSession", currentSession);
        ReflectionTestUtils.setField(wishDao, "authService", authService);
    }


    @Test
    public void testReadAll() {
        List<User> users = MdlFactory.createUsers();
        List<Wish> wishes = MdlFactory.createWishes(users);
        Query query = PowerMock.createMock(Query.class);
        
        for(User user : users) {
            EasyMock.expect(currentSession.createQuery("from org.fpaitrault.mdl.Wish")).andReturn(query);
            EasyMock.expect(query.list()).andReturn(new LinkedList<Wish>(wishes));
            EasyMock.expect(authService.getUserCredential()).andReturn(user);
            
            PowerMock.replayAll();
            
            List<Wish> readWishes = wishDao.readAll();
            
            for(Wish readWish : readWishes) {
                //Don't show wish for current user not written by current user
                if(readWish.getAuthor() != user) {
                    assertNotSame("User: "+user.getUsername()+" Dest: "+readWish.getDest(),user, readWish.getDest());
                    if(user != readWish.getDest()) {
                        System.out.println("");
                    }
                }
            }
            
                
            PowerMock.verifyAll();
            PowerMock.resetAll();
        }
    }

}
