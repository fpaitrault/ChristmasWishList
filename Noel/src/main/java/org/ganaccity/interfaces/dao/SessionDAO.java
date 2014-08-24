package org.ganaccity.interfaces.dao;

import org.ganaccity.mdl.Session;
import org.ganaccity.mdl.User;

public interface SessionDAO extends GenericDAO<Session> {
    void deleteSessionByUUID(String uuid);
    User readUserByUUID(String uuid);
}
