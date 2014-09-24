package org.ganaccity.tests.viewmdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.easymock.EasyMock;
import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.interfaces.dao.WishDAO;
import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;
import org.ganaccity.viewmdl.UserGrid;
import org.ganaccity.viewmdl.WishStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.test.util.ReflectionTestUtils;
import org.zkoss.zk.ui.Executions;

@PrepareForTest( { UserGrid.class , Executions.class, Wish.class})
public class UserGridTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    private static final String [] TEST_PASSWORD = {"testPassword", "testPassword 2"};
    private static final String [] TEST_USER_NAME = {"testUser", "testUser 2"};

    UserGrid grid = null;
    private AuthenticationService authService = null;
    private UserDAO userDAO = null;
    private WishDAO wishDAO = null;
    private List<User> testUsers = null;
    private List<Wish> testWishes = null;

    @Before
    public void TestSetup() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        grid = new UserGrid();
        authService = PowerMock.createMock(AuthenticationService.class);
        userDAO = PowerMock.createMock(UserDAO.class);
        wishDAO = PowerMock.createMock(WishDAO.class);
        ReflectionTestUtils.setField(grid, "authService", authService);
        ReflectionTestUtils.setField(grid, "userDAO", userDAO);
        ReflectionTestUtils.setField(grid, "wishDAO", wishDAO);

        //Load reference list
        testUsers = new ArrayList<User>();
        testWishes = new ArrayList<Wish>();
        for(int i=0;i<TEST_USER_NAME.length;i++) {
            User user = new User(TEST_USER_NAME[i], DigestUtils.md5Hex(TEST_PASSWORD[i]),i);
            user.setIndex(i);
            testUsers.add(user);
        }
        //Create wishes for all users
        for(User user : testUsers) {
            for(User author : testUsers) {
                for(User reservedBy : testUsers) {
                    testWishes.add(new Wish(author, "description", "comment", user, reservedBy));
                }
                testWishes.add(new Wish(author, "description", "comment", user, null));
            }
        }
    }

    @Test
    public void testInit() {
        testInit(true);
        testInit(false);
    }

    private void testInit(boolean isCurrentUser) {
        User currUser = testUsers.get(0);
        User destUser = isCurrentUser?currUser:testUsers.get(1);
        List<Wish> selectedWishList = extractWishList(isCurrentUser, currUser,
                destUser);
        
        EasyMock.expect(authService.getUserCredential()).andReturn(currUser);
        EasyMock.expect(wishDAO.readAll()).andReturn(testWishes);
        
        PowerMock.replayAll();

        
        grid.init(destUser);
        List<WishStatus> wishStatus = grid.getWishList();
        assertEquals(wishStatus.size(), selectedWishList.size());
        for(WishStatus ws : wishStatus) {
            //Wish is contained in reference list
            assertTrue(selectedWishList.contains(ws.getWish()));
            //Wish is not editable
            assertFalse(ws.getEditStatus());
        }

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    private List<Wish> extractWishList(boolean isCurrentUser, User currUser,
            User destUser) {
        List<Wish> selectedWishList = new ArrayList<Wish>();
        for(Wish wish : testWishes) {
            if(wish.getDest() == destUser) {
                if(wish.getAuthor() == currUser) { //Wish added by current user
                    selectedWishList.add(wish);
                } else if(!isCurrentUser ) { //Wish not intended to current user
                    selectedWishList.add(wish);
                }
            }
        }
        return selectedWishList;
    }
    
    @Test
    public void testGetUsers() {
        EasyMock.expect(userDAO.readAll()).andReturn(testUsers);
        
        PowerMock.replayAll();

        assertEquals(grid.getUsers(), testUsers);

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }

    @Test
    public void testAddIdea() throws Exception {
        User currUser = testUsers.get(0);
        User destUser = testUsers.get(1);
        List<Wish> selectedWishList = extractWishList(false, currUser,
                destUser);

        EasyMock.expect(authService.getUserCredential()).andReturn(currUser);
        EasyMock.expect(wishDAO.readAll()).andReturn(testWishes);

        EasyMock.expect(authService.getUserCredential()).andReturn(currUser);
        Wish wish = new Wish(currUser,"","",destUser,null);
        PowerMock.expectNew(Wish.class, currUser,"","",destUser,null).andReturn(wish);
        EasyMock.expect(wishDAO.create(wish)).andReturn(0);

        PowerMock.replayAll();

        grid.init(destUser);
        grid.addIdea();
        
        List<WishStatus> wishStatus = grid.getWishList();
        assertEquals(wishStatus.size(), selectedWishList.size() + 1); //One new idea
        for(WishStatus ws : wishStatus) {
            if(!selectedWishList.contains(ws.getWish())) {
                assertTrue(ws.getEditStatus()); //Editable
                assertTrue(ws.getWish().getAuthor().equals(currUser)); // Author is current user
                assertTrue(ws.getWish().getDest().equals(destUser)); //Dest is current dest
            }
        }

        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
}
