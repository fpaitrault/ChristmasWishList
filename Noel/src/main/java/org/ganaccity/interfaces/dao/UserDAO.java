package org.ganaccity.interfaces.dao;

import java.util.List;

import org.ganaccity.mdl.User;


public interface UserDAO extends GenericDAO<User> {
    User readByUserName(String username);
}
