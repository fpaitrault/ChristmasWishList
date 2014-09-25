package org.ganaccity.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.ganaccity.dao.SessionDAOImpl;
import org.ganaccity.mdl.Session;
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
public class SessionDAOImplTest {
    
    private SessionDAOImpl sessionDao = null;
    private org.hibernate.classic.Session currentSession = null;

    @Before
    public void setUp() throws Exception {
        sessionDao = new SessionDAOImpl();
        currentSession = PowerMock.createMock(org.hibernate.classic.Session.class);
        ReflectionTestUtils.setField(sessionDao, "currentSession", currentSession);
    }

    @Test
    public void testdeleteSessionByUUID() {
        List<Session> sessions = MdlFactory.createSessions();
        Query query = PowerMock.createMock(Query.class);
        Transaction transac = PowerMock.createMock(Transaction.class);
        for(Session session : sessions) {
            EasyMock.expect(currentSession.beginTransaction()).andReturn(transac);
            EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.Session where uuid=:uuid")).andReturn(query);
            EasyMock.expect(query.setString("uuid", session.getUUID())).andReturn(query);
            EasyMock.expect(query.uniqueResult()).andReturn(session);
            currentSession.delete(session);
            PowerMock.expectLastCall().once();
            transac.commit();
            PowerMock.expectLastCall().once();
            
            PowerMock.replayAll();
            
            sessionDao.deleteSessionByUUID(session.getUUID());
            
            PowerMock.verifyAll();
            PowerMock.resetAll();
        }
        
    }
    
    @Test
    public void testreadUserByUUID() {
        List<Session> sessions = MdlFactory.createSessions();
        Query query = PowerMock.createMock(Query.class);
        for(Session session : sessions) {
            EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.Session where uuid=:uuid")).andReturn(query);
            EasyMock.expect(query.setString("uuid", session.getUUID())).andReturn(query);
            EasyMock.expect(query.uniqueResult()).andReturn(session);
            
            PowerMock.replayAll();
            
            assertEquals(session.getUser(), sessionDao.readUserByUUID(session.getUUID()));
            
            PowerMock.verifyAll();
            PowerMock.resetAll();
        }
    }
    
    @Test
    public void testreadUserByUUIDNull() {
        Query query = PowerMock.createMock(Query.class);
        EasyMock.expect(currentSession.createQuery("from org.ganaccity.mdl.Session where uuid=:uuid")).andReturn(query);
        EasyMock.expect(query.setString("uuid", "DEADBEEF")).andReturn(query);
        EasyMock.expect(query.uniqueResult()).andReturn(null);
        
        PowerMock.replayAll();
        
        assertNull( sessionDao.readUserByUUID("DEADBEEF"));
        
        PowerMock.verifyAll();
        PowerMock.resetAll();
    }
    
}
