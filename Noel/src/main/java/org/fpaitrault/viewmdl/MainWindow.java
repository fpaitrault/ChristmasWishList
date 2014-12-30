package org.fpaitrault.viewmdl;

import java.util.LinkedList;
import java.util.List;

import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.SettingDAO;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mdl.User;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainWindow {
    
    @WireVariable
    private AuthenticationService authService = null;
    @WireVariable
    private UserDAO userDAO;
    @WireVariable
    private SettingDAO settingDAO;
    
    private User user = null;

    public List<User> getUsers() {
        return userDAO.readAll();
    }
    
    public List<User> getFriends() {
        List<User> res = new LinkedList<User>(user.getFriends());
        res.add(user);
        return res;
    }

    @Command
    public void logout() {
        authService.logout();
        Executions.sendRedirect("/login.zul"); //$NON-NLS-1$
    }
    
    @Command
    public void showAdmin() {
        Executions.createComponents("admin.zul", null, null); //$NON-NLS-1$
    }

    public User getUser() {
        if(user == null) {
            user = authService.getUserCredential(); 
        }
        return user;
    }

    public String getBanner() {
        return settingDAO.get("Index.BANNER"); //$NON-NLS-1$
    }
    
    public String getBackground() {
        return settingDAO.get("Index.BACKGROUND"); //$NON-NLS-1$
    }
}
