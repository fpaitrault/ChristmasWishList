package org.fpaitrault.viewmdl.mgmt;

import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NewUser extends UserForm {

    @WireVariable
    private AuthenticationService authService = null;
    @WireVariable
    private UserDAO userDAO;
    @Wire
    private Window modal = null;
    
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
    }
    
    @Command @NotifyChange({"popupVisible"})
    public void submit() {
        userDAO.create(getUser());
        modal.detach();
    }
}

