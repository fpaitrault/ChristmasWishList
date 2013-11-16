package org.ganaccity.dao;

import java.util.List;

import org.ganaccity.mdl.User;

public class UserDAO extends GenericDAO<User> {

    private List<User> userCache = null;

    public UserDAO() {
        super(User.class);
        userCache = super.readAll();
    }

    public final User readByUserName(final String username) {
        for (User user : userCache) {
            if (user.getName().equals(username)) {
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
