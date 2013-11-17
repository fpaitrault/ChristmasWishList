package org.ganaccity;

import java.util.UUID;

import org.zkoss.zk.ui.Sessions;
import org.apache.commons.codec.digest.DigestUtils;
import org.ganaccity.ctrl.CookieController;
import org.ganaccity.dao.SessionDAO;
import org.ganaccity.dao.UserDAO;
import org.ganaccity.mdl.Session;
import org.ganaccity.mdl.User;

public class AuthenticationService {

    private static final int YEAR_SEC = 3600 * 24 * 365;
    private static final String USER_DATABASE_INDEX = "USER_INDEX";
    private static final String USER_DATABASE_COOKIE = "USER_COOKIE";
    private static final String USER_COOKIE_STRING = "IdentData";
	static private AuthenticationService instance = new AuthenticationService();
	
	static public AuthenticationService instance() {
		return instance;
	}

	private SessionDAO session = new SessionDAO();
	private UserDAO users = new UserDAO();
	
	public boolean login(String name, String password) {
		for(User user : users.readAll()) {
			if(user.getName().equalsIgnoreCase(name) &&
					user.getHash().equals(DigestUtils.md5Hex(password))) {
			       //Store identifiers into session database
		        UUID uuid = UUID.randomUUID();
		        session.create(new Session(uuid.toString(), user));
//		        CookieController.instance().setCookie(
//		                USER_COOKIE_STRING, uuid.toString(), YEAR_SEC); // 1 year stored info.
                //Store informations into HTML Session Manager
                Sessions.getCurrent().setAttribute(USER_DATABASE_INDEX, Integer.valueOf(user.getIndex()));
                Sessions.getCurrent().setAttribute(USER_DATABASE_COOKIE, uuid);
				return true;
			}
		}
		return false;
	}
	public boolean isFirstLogin(String name) {
	    User user = users.readByUserName(name);
	    if(user != null && (user.getHash() == null || user.getHash().isEmpty()))
	        return true;
	    else
	        return false;
	}

	public void logout() {
        session.deleteSessionByUUID(Sessions.getCurrent().getAttribute(USER_DATABASE_COOKIE).toString());
        Sessions.getCurrent().removeAttribute(USER_DATABASE_INDEX);
        Sessions.getCurrent().removeAttribute(USER_DATABASE_COOKIE);
	}

	public User getUserCredential() {
        User user = null;
        if (Sessions.getCurrent().getAttribute(USER_DATABASE_INDEX) != null) {
            user = users.readById(((Integer) Sessions.getCurrent().getAttribute(USER_DATABASE_INDEX)));
        } /*else {
            //Retrieve stored informations into browser cookies
            String uuid = CookieController.instance().getCookie(USER_COOKIE_STRING);
            user = session.readUserByUUID(uuid);
            if (user != null) {
                //Store informations into HTML Session Manager
                Sessions.getCurrent().setAttribute(USER_DATABASE_INDEX, Integer.valueOf(user.getIndex()));
                Sessions.getCurrent().setAttribute(USER_DATABASE_COOKIE, uuid);
            }
        }*/
        return user;
	}
	
	public void updatePassword(String name, String password) {
	    User user = users.readByUserName(name);
	    user.setPassword(password);
	    users.update(user);
	}

}
