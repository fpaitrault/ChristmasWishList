package org.fpaitrault.viewmdl.mgmt;

import org.fpaitrault.mdl.User;

public class UserForm {

    private User user = new User();
    private String retypedPassword;
    private String retypedEmail;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRetypedPassword() {
        return retypedPassword;
    }

    public void setRetypedPassword(String retypedPassword) {
        this.retypedPassword = retypedPassword;
    }

    public String getRetypedEmail() {
        return retypedEmail;
    }

    public void setRetypedEmail(String retypedEmail) {
        this.retypedEmail = retypedEmail;
    }
}
