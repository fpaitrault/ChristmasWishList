package org.fpaitrault;

import java.util.UUID;

import org.fpaitrault.interfaces.SessionService;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Sessions;

@Service("sessionSVC")
public class SessionServiceImpl implements SessionService {

    private static final String USER_DATABASE_INDEX = "USER_INDEX";
    private static final String USER_DATABASE_COOKIE = "USER_COOKIE";

    @Override
    public void register(int userId, UUID uuid) {
        Sessions.getCurrent().setAttribute(USER_DATABASE_INDEX, userId);
        Sessions.getCurrent().setAttribute(USER_DATABASE_COOKIE, uuid);
    }

    @Override
    public void unregister() {
        Sessions.getCurrent().removeAttribute(USER_DATABASE_INDEX);
        Sessions.getCurrent().removeAttribute(USER_DATABASE_COOKIE);
    }

    @Override
    public UUID getUUID() {
        return  (UUID)Sessions.getCurrent().getAttribute(USER_DATABASE_COOKIE);
    }

    @Override
    public Integer getUserId() {
        return (Integer) Sessions.getCurrent().getAttribute(USER_DATABASE_INDEX);
    }

}
