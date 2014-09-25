package org.fpaitrault.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.fpaitrault.dao.SessionDAOImpl;
import org.fpaitrault.dao.SettingDAOImpl;
import org.fpaitrault.mdl.Session;
import org.fpaitrault.mdl.Setting;
import org.fpaitrault.mdl.User;
import org.fpaitrault.tests.MdlFactory;
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
        EasyMock.expect(currentSession.createQuery("from org.fpaitrault.mdl.Setting")).andReturn(query);
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
        EasyMock.expect(currentSession.createQuery("from org.fpaitrault.mdl.Setting")).andReturn(query);
        EasyMock.expect(query.list()).andReturn(settings);
        
        PowerMock.replayAll();
        
        assertNull( settingDao.get("DEADBEEF"));
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
}
