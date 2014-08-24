package org.ganaccity.viewmdl;

import org.ganaccity.interfaces.AuthenticationService;
import org.ganaccity.mdl.DeviceMode;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Login {
    private boolean firstLogin = false;
    private String name = "";
    private String password = "";
    private String passwdConfirm = "";
    private String passwdErrorMsg = "";
    private DeviceMode deviceMode = new DeviceMode();
    
    @WireVariable
    private AuthenticationService authService;

    public boolean getFirstLogin() {
        return firstLogin;
    }
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
    public String getPasswdConfirm() {
        return passwdConfirm;
    }
    public void setPasswdConfirm(String passwdConfirm) {
        this.passwdConfirm = passwdConfirm;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswdErrorMsg() {
        return passwdErrorMsg;
    }
    public void setPasswdErrorMsg(String passwdErrorMsg) {
        this.passwdErrorMsg = passwdErrorMsg;
    }

    @Command
    @NotifyChange("*")
    public void clientInfoChanged(@BindingParam("evt") ClientInfoEvent event){
            deviceMode.setClientInfo((ClientInfoEvent)event);
    }
    
    public DeviceMode getDeviceMode() {
        return deviceMode;
    }

    @Command
    @NotifyChange("firstLogin")
    public void login() {
        if(authService.isFirstLogin(getName()))
            setFirstLogin(true);
        else
        {
            if(authService.login(getName(), getPassword()))
                Executions.sendRedirect("/");
        }
    }
    
    @NotifyChange("passwdErrorMsg")
    @Command
    public void firstLogin() {
        if(!getPassword().equals(getPasswdConfirm()))
        {
            setPasswdErrorMsg("Les mots de passe ne correspondent pas");
            return;
        }
        //Update password on database
        authService.updatePassword(getName(), getPassword());
        //Login application
        if(authService.login(getName(), getPassword()))
            Executions.sendRedirect("/");
    }
}
