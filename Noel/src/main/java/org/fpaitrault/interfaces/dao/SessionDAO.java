package org.fpaitrault.interfaces.dao;

import org.fpaitrault.mdl.Session;
import org.fpaitrault.mdl.User;

public interface SessionDAO extends GenericDAO<Session> {
    void deleteSessionByUUID(String uuid);
    User readUserByUUID(String uuid);
}
