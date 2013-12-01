package org.fpaitrault.interfaces;

import org.fpaitrault.mdl.User;

public interface AuthenticationService {
    boolean login(String name, String password);
    boolean isFirstLogin(String name);
    void logout();
    User getUserCredential();
    void updatePassword(String name, String password);
}
