package org.fpaitrault;

import java.util.Map;

import org.fpaitrault.interfaces.AuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.spring.SpringUtil;

public class AuthenticationInit implements Initiator {

    private AuthenticationService authService;
    
    public AuthenticationInit() {
        authService = (AuthenticationService) SpringUtil.getBean("authService");
    }
    
	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		
		if(authService.getUserCredential()==null) {
			Executions.sendRedirect("/login.zul");
			return;
		}
	}

}
