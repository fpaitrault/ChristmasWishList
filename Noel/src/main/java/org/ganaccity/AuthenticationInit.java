package org.ganaccity;

import java.util.Map;


import org.ganaccity.AuthenticationService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class AuthenticationInit implements Initiator {

	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		
		if(AuthenticationService.instance().getUserCredential()==null) {
			Executions.sendRedirect("/login.zul");
			return;
		}
	}

}
