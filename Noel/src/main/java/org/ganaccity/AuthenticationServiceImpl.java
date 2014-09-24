package org.ganaccity;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.interfaces.SessionService;
import org.ganaccity.interfaces.dao.SessionDAO;
import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.mdl.Session;
import org.ganaccity.mdl.User;

@Service("authService")
@Scope("singleton")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired @Qualifier("sessionDAO")
	private SessionDAO session;
    @Autowired @Qualifier("userDAO")
	private UserDAO users;
    @Autowired @Qualifier("sessionSVC")
    private SessionService sessionService;
    
	
	public boolean login(String name, String password) {
	    List<User> userList = users.readAll();
		for(User user : userList) {
			if(user.getUsername().equalsIgnoreCase(name) &&
					user.getHash().equals(DigestUtils.md5Hex(password))) {
			       //Store identifiers into session database
		        UUID uuid = UUID.randomUUID();
		        session.create(new Session(uuid.toString(), user));
                //Store informations into HTML Session Manager
		        sessionService.register(Integer.valueOf(user.getIndex()), uuid);
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
        session.deleteSessionByUUID(sessionService.getUUID().toString());
        sessionService.unregister();
	}

	public User getUserCredential() {
        User user = null;
        Integer userId = sessionService.getUserId();
        if (userId != null && userId >= 0) {
            user = users.readById(((Integer) sessionService.getUserId()));
        }
        return user;
	}
	
	public void updatePassword(String name, String password) {
	    User user = users.readByUserName(name);
	    user.setPassword(password);
	    users.update(user);
	}

}
