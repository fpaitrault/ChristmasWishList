package org.ganaccity.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.ganaccity.dao.SessionDAOImpl;
import org.ganaccity.dao.SettingDAOImpl;
import org.ganaccity.mdl.Session;
import org.ganaccity.mdl.Setting;
import org.ganaccity.mdl.User;
import org.ganaccity.tests.MdlFactory;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;

@PrepareForTest( { SessionDAOImpl.class} )
public class SettingDAOImplTest {
    
    private SettingDAOImpl settingDao = null;
    private org.hibernate.classic.Session currentSession = null;

    @Before
    public void setUp() throws Exception {
        settingDao = new SettingDAOImpl();
        currentSession = PowerMock.createMock(org.hibernate.classic.Session.class);
        ReflectionTestUtils.setField(settingDao, "currentSession", currentSession);
    }
    
    @Test
    public void testget() {
        List<Setting> settings = MdlFactory.createSettings();
        Query query = PowerMock.createMock(Query.class);
        EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.Setting")).andReturn(query);
        EasyMock.expect(query.list()).andReturn(settings);
        
        PowerMock.replayAll();
        
        for(Setting setting : settings) {
            assertEquals(setting.getValue(), settingDao.get(setting.getKey()));
        }
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
    
    @Test
    public void testgetNull() {
        List<Setting> settings = MdlFactory.createSettings();
        Query query = PowerMock.createMock(Query.class);
        EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.Setting")).andReturn(query);
        EasyMock.expect(query.list()).andReturn(settings);
        
        PowerMock.replayAll();
        
        assertNull( settingDao.get("DEADBEEF"));
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
}
