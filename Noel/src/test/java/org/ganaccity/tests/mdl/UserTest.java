package org.ganaccity.tests.mdl;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.jetty.http.security.Credential.MD5;
import org.ganaccity.mdl.User;
import org.ganaccity.tests.MdlFactory;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetFriends() {
        List<User> users = MdlFactory.createUsers();
        User user = new User();
        
        user.setFriends(users);
        assertEquals(users.toArray(), user.getFriends().toArray());
        
        user.setFriends(null);
        assertNull(user.getFriends());
    }

    @Test
    public void testGetEmail() {
        User user = new User();
        
        user.setEmail("toto@too.fr");
        assertEquals("toto@too.fr", user.getEmail());
    }

    @Test
    public void testGetUsername() {
        User user = new User();
        
        user.setUserName("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testGetName() {
        User user = new User();
        
        user.setName("name");
        assertEquals("name", user.getName());
    }

    @Test
    public void testGetHash() {
        User user = new User();
        
        user.setPassword("password");
        assertEquals( DigestUtils.md5Hex("password"), user.getHash());
    }

    @Test
    public void testGetIndex() {
        User user = new User();
        
        user.setIndex(4);
        assertEquals(4, user.getIndex());
    }

}
