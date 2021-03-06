package org.fpaitrault.viewmdl;

import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.SettingDAO;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Login {
    private boolean firstLogin = false;
    private String name = ""; //$NON-NLS-1$
    private String password = ""; //$NON-NLS-1$
    private String passwdConfirm = ""; //$NON-NLS-1$
    private String passwdErrorMsg = ""; //$NON-NLS-1$
    
    @WireVariable
    private AuthenticationService authService;
    @WireVariable
    private SettingDAO settingDAO;
    private String email;

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    @NotifyChange("firstLogin")
    public void login() {
        if(authService.isFirstLogin(getName()))
            setFirstLogin(true);
        else
        {
            if(authService.login(getName(), getPassword()))
                Executions.sendRedirect("/"); //$NON-NLS-1$
        }
    }
    
    @NotifyChange("passwdErrorMsg")
    @Command
    public void firstLogin() {
        if(!getPassword().equals(getPasswdConfirm()))
        {
            setPasswdErrorMsg(Labels.getLabel("login.password.mismatch")); //$NON-NLS-1$
            return;
        }
        //Update password on database
        authService.updatePassword(getName(), getPassword());
        //Update email on database
        if(getEmail() != null)
            authService.updateEmail(getName(), getEmail());
        //Login application
        if(authService.login(getName(), getPassword()))
            Executions.sendRedirect("/"); //$NON-NLS-1$
    }
    
    public String getBackground() {
        return settingDAO.get("Login.BACKGROUND"); //$NON-NLS-1$
    }
}
