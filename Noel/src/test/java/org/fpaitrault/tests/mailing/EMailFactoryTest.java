package org.fpaitrault.tests.mailing;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.fpaitrault.mailing.EMailFactory;
import org.fpaitrault.mdl.User;
import org.fpaitrault.mdl.Wish;
import org.fpaitrault.tests.MdlFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;

@PrepareForTest( {EMailFactory.class} )
public class EMailFactoryTest {
    SimpleDateFormat formatter;
    @Before
    public void setUp() throws Exception {
        formatter = new SimpleDateFormat("yyyy/MM/dd");
    }

    @Test
    public void testcreateModel() throws ParseException {
        List<User> users = MdlFactory.createUsers();
        EMailFactory factory = new EMailFactory();

        for(User user : users) {
            List<Wish> wishes = MdlFactory.createWishes(users);
            
            PowerMock.replayAll();
            
            Map<String,Object> mdl = factory.createModel(user, wishes);
            assertEquals(user.getName(), mdl.get("user"));
            List<Wish> resWishes = (List<Wish>)mdl.get("wishes");
            assertNotEquals(0, resWishes.size());
            for(Wish resWish : resWishes) {
                assertNotEquals(user, resWish.getDest());
            }
            
            PowerMock.verifyAll();
            PowerMock.resetAll();
            break;
        }
    }

}
