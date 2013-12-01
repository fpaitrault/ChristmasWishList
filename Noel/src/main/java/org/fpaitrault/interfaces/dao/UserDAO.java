package org.fpaitrault.interfaces.dao;

import java.util.List;

import org.fpaitrault.mdl.User;


public interface UserDAO extends GenericDAO<User> {
    User readByUserName(String username);
}
