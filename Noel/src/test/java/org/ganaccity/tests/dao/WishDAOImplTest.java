package org.ganaccity.tests.dao;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.ganaccity.dao.WishDAOImpl;
import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.interfaces.dao.WishDAO;
import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;
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
        List<User> users = createUsers();
        List<Wish> wishes = createWishes(users);
        Query query = PowerMock.createMock(Query.class);
        
        for(User user : users) {
            EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.Wish")).andReturn(query);
            EasyMock.expect(query.list()).andReturn(new LinkedList<Wish>(wishes));
            EasyMock.expect(authService.getUserCredential()).andReturn(user);
            
            PowerMock.replayAll();
            
            List<Wish> readWishes = wishDao.readAll();
            
            for(Wish readWish : readWishes) {
                //Don't show wish for current user not written by current user
                if(readWish.getAuthor() != user) {
                    assertNotSame("User: "+user.getName()+" Dest: "+readWish.getDest(),user, readWish.getDest());
                    if(user != readWish.getDest()) {
                        System.out.println("");
                    }
                }
            }
            
                
            PowerMock.verifyAll();
            PowerMock.resetAll();
        }
    }

    private List<User> createUsers() {
        List<User> users = new LinkedList<User>();
        users.add(new User("Florent", "toto",0));
        users.add(new User("Maeva", "test",1));
        users.add(new User("Howard", "titi",2));
        return users;
    }

    private List<Wish> createWishes(List<User> users) {
        List<Wish> wishes = new LinkedList<Wish>();
        for(User author : users) {
            for(User dest : users) {
                for(User resv : users) {
                    Wish wish = createWish(author, dest, resv);
                    wishes.add(wish);
                }
                Wish wish = createWish(author, dest, null);
                wishes.add(wish);
            }
        }
        return wishes;
    }


    private Wish createWish(User author, User dest, User resv) {
        Wish wish = new Wish(
                author, 
                "Present for "+dest.toString(), 
                "Comment from "+author.toString(),dest,resv);
        return wish;
    }

}
