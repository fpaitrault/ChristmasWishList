package org.ganaccity.viewmdl;

import java.util.List;

import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.mdl.DeviceMode;
import org.ganaccity.mdl.User;
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
    
    private User user = null;
    private DeviceMode deviceMode = null;

    public MainWindow() {
        deviceMode = new DeviceMode();
    }
    
    public List<User> getUsers() {
        return userDAO.readAll();
    }
    
    public List<User> getFriends() {
        return user.getFriends();
    }

    @Command
    public void logout() {
        authService.logout();
        Executions.sendRedirect("/login.zul");
    }

    public User getUser() {
        if(user == null) {
            user = authService.getUserCredential(); 
        }
        return user;
    }
    
    @Command
    @NotifyChange("*")
    public void clientInfoChanged(@BindingParam("evt") ClientInfoEvent event){
            deviceMode.setClientInfo((ClientInfoEvent)event);
    }
    
    public DeviceMode getDeviceMode() {
        return deviceMode;
    }

}
