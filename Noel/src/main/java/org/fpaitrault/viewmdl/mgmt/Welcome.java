package org.fpaitrault.viewmdl.mgmt;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Welcome {

    @WireVariable
    private SettingDAO settingDAO;

    private String welcome = null;
    
    public String getWelcome() {
        String welcome = settingDAO.get("Welcome.TPL");
        welcome.replaceAll("&", "&amp;");
        welcome.replaceAll("\n", "<br/>");
        return welcome;
    }
    public void setWelcome(String welcome) {
        this.welcome = welcome.replaceAll("&amp;", "&");
    }

    @Command
    public void saveWelcome() {
        settingDAO.set("Welcome.TPL", this.welcome);
        Messagebox.show("Page d'accueil mise à jour", "Administration", new Messagebox.Button[]{
                Messagebox.Button.OK}, Messagebox.INFORMATION,null);
    }
    
}

