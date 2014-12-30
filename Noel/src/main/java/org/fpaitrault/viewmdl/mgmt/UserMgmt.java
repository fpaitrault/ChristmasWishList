package org.fpaitrault.viewmdl.mgmt;

import java.util.LinkedList;
import java.util.List;

import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.SettingDAO;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mdl.User;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserMgmt {

    @WireVariable
    private AuthenticationService authService = null;
    @WireVariable
    private UserDAO userDAO;
    @WireVariable
    private SettingDAO settingDAO;

    private User activeUser = null;
    private User selectedFriend = null;
    private User selectedNotFriend = null;
    private String mailing = null;
 
    public String getMailing() {
        String mailing = settingDAO.get("MailingTask.EMAILTPL"); //$NON-NLS-1$
        mailing.replaceAll("&", "&amp;"); //$NON-NLS-1$ //$NON-NLS-2$
        mailing.replaceAll("\n", "<br/>"); //$NON-NLS-1$ //$NON-NLS-2$
        return mailing;
    }
    public void setMailing(String mailing) {
        this.mailing = mailing.replaceAll("&amp;", "&"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public List<User> getUsers() {
        return userDAO.readAll();
    }
    public User getActiveUser() {
        return activeUser;
    }
    public User getSelectedFriend() {
        return selectedFriend;
    }
    public User getSelectedNotFriend() {
        return selectedNotFriend;
    }
    public List<User> getFriends() {
        if(activeUser != null) {
            return activeUser.getFriends();
        } else {
            return null;
        }
    }
    public List<User> getNotFriends() {
        if(activeUser != null) {
            List<User> res = userDAO.readAll();
            res.removeAll(activeUser.getFriends());
            res.remove(activeUser);
            return res;
        } else {
            return null;
        }
    }

    @NotifyChange({"friends","notFriends"})
    public void setActiveUser(User user) {
        activeUser = user;
    }

    @NotifyChange({"friends","notFriends"})
    public void setSelectedFriend(User user) {
        selectedFriend = user;
    }

    @NotifyChange({"friends","notFriends"})
    public void setSelectedNotFriend(User user) {
        selectedNotFriend = user;
    }
    
    @Command
    @NotifyChange({"friends","notFriends"})
    public void addFriend() {
        if(activeUser != null && selectedNotFriend != null) {
            activeUser.getFriends().add(selectedNotFriend);
            if(!activeUser.equals(selectedNotFriend)) {
                selectedNotFriend.getFriends().add(activeUser);
            }
            userDAO.update(activeUser);
        }
    }
    @Command
    @NotifyChange({"friends","notFriends"})
    public void removeFriend() {
        if(activeUser != null && selectedFriend != null) {
            activeUser.getFriends().remove(selectedFriend);
            selectedFriend.getFriends().remove(activeUser);
            userDAO.update(activeUser);
        }
    }
    @GlobalCommand
    @NotifyChange({"friends","notFriends","users", "activeUser"})
    public void refresh() { }
    
    @Command
    public void addUser() {
        Executions.createComponents("mgmt/newUser.zul", null, null); //$NON-NLS-1$
    }
}

