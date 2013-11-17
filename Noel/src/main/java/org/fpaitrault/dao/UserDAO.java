package org.fpaitrault.dao;

import java.util.List;

import org.fpaitrault.mdl.User;

public class UserDAO extends GenericDAO<User> {

    private List<User> userCache = null;

    public UserDAO() {
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
