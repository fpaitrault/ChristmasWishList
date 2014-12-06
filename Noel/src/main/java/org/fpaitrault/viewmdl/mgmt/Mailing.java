package org.fpaitrault.viewmdl.mgmt;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Mailing {

    @WireVariable
    private SettingDAO settingDAO;

    private String mailing = "";
    private String srcEmail = "";
    private String subject = "";
    private String host = "";
    private String port = "";
    private String password = "";   
    
    @Init
    public void init() {
        this.srcEmail = settingDAO.get("MailingTask.FROM");
        this.subject = settingDAO.get("MailingTask.SUBJECT");
        this.host = settingDAO.get("MailingTask.HOST");
        this.port = settingDAO.get("MailingTask.PORT");
        this.password = settingDAO.get("MailingTask.PASSWORD");
        this.mailing = settingDAO.get("MailingTask.EMAILTPL");
    }
    
    public String getEmail() {
        return this.srcEmail;
    }
    public void setEmail(String srcEmail) {
        this.srcEmail = srcEmail;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSmtpHost() {
        return host;
    }
    public void setSmtpHost(String host) {
        this.host = host;
    }
    public String getSmtpPort() {
        return port;
    }
    public void setSmtpPort(String port) {
        this.port = port;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMailing() {
        String mailing = this.mailing;
        mailing.replaceAll("&", "&amp;");
        mailing.replaceAll("\n", "<br/>");
        return mailing;
    }
    public void setMailing(String mailing) {
        this.mailing = mailing.replaceAll("&amp;", "&");
    }

    @Command
    @NotifyChange({"mailing"})
    public void resetMailing() {
        this.mailing = getMailing();
    }

    @Command
    public void saveMailing() {
        settingDAO.set("MailingTask.EMAILTPL", this.mailing);
        settingDAO.set("MailingTask.FROM", this.srcEmail);
        settingDAO.set("MailingTask.SUBJECT", this.subject);
        settingDAO.set("MailingTask.PASSWORD", this.password);
        settingDAO.set("MailingTask.HOST", this.host);
        settingDAO.set("MailingTask.PORT", this.port);
        Messagebox.show("Modèle d'email mis à jour", "Administration", new Messagebox.Button[]{
                Messagebox.Button.OK}, Messagebox.INFORMATION,null);
    }

}

