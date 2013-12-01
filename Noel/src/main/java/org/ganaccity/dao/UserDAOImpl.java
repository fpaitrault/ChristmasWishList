package org.ganaccity.dao;

import java.util.List;

import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.mdl.User;
import org.springframework.stereotype.Service;

@Service("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    private List<User> userCache = null;

    public UserDAOImpl() {
        super(User.class);
        userCache = super.readAll();
    }

    public final User readByUserName(final String username) {
        for (User user : userCache) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public final List<User> readAll() {
        return userCache;
    }

}
