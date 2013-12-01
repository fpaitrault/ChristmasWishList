package org.fpaitrault.dao;

import java.util.List;

import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mdl.User;
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
