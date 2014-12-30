package org.fpaitrault.viewmdl.mgmt;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Welcome {

    @WireVariable
    private SettingDAO settingDAO;

    private String welcome = null;
    
    public String getWelcome() {
        String welcome = settingDAO.get("Welcome.TPL"); //$NON-NLS-1$
        welcome.replaceAll("&", "&amp;"); //$NON-NLS-1$ //$NON-NLS-2$
        welcome.replaceAll("\n", "<br/>"); //$NON-NLS-1$ //$NON-NLS-2$
        return welcome;
    }
    public void setWelcome(String welcome) {
        this.welcome = welcome.replaceAll("&amp;", "&"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Command
    public void saveWelcome() {
        settingDAO.set("Welcome.TPL", this.welcome); //$NON-NLS-1$
        Messagebox.show(Labels.getLabel("admin.welcome.update"),  //$NON-NLS-1$
                Labels.getLabel("admin.title"), new Messagebox.Button[]{ //$NON-NLS-1$
                Messagebox.Button.OK}, Messagebox.INFORMATION,null);
    }
    
}

