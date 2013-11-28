package org.fpaitrault.viewmdl;

import java.util.List;

import org.fpaitrault.AuthenticationService;
import org.fpaitrault.DeviceMode;
import org.fpaitrault.dao.UserDAO;
import org.fpaitrault.mdl.User;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;

public class MainWindow {
    
    private UserDAO users = new UserDAO();
    private User user = AuthenticationService.instance().getUserCredential();
    private DeviceMode deviceMode = new DeviceMode();

    public List<User> getUsers() {
        return users.readAll();
    }

    @Command
    public void logout() {
        AuthenticationService.instance().logout();
        Executions.sendRedirect("/login.zul");
    }

    public User getUser() {
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
