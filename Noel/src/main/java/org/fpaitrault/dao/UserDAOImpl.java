package org.fpaitrault.dao;

import java.util.List;

import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mdl.User;
import org.springframework.stereotype.Service;

@Service("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    public final User readByUserName(final String username) {
        for (User user : readAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

}
