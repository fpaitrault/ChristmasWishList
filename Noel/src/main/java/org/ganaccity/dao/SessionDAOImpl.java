package org.ganaccity.dao;

import org.ganaccity.dao.GenericDAOImpl;
import org.ganaccity.interfaces.dao.SessionDAO;
import org.ganaccity.mdl.Session;
import org.ganaccity.mdl.User;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service("sessionDAO")
public class SessionDAOImpl extends GenericDAOImpl<Session> implements SessionDAO {
    public SessionDAOImpl() {
    	super(Session.class);
    }

    public final void deleteSessionByUUID(final String uuid) {
        synchronized (getSession()) {
            Session session = null;
            Transaction transact = getSession().beginTransaction();
            session = (Session) getSession().createQuery("from "+getType().getName()+" where uuid=:uuid")
                    .setString("uuid", uuid)
                    .uniqueResult();
            getSession().delete(session);
            transact.commit();
        }
    }

    public final User readUserByUUID(final String uuid) {
        synchronized (getSession()) {
            Session session = null;
            session = (Session) getSession().createQuery("from "+getType().getName()+" where uuid=:uuid")
                    .setString("uuid", uuid)
                    .uniqueResult();
            if (session != null) {
                return session.getUser();
            } else {
                return null;
            }
        }
    }
}
