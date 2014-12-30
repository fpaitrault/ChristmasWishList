package org.fpaitrault.viewmdl.mgmt;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Mailing {

    @WireVariable
    private SettingDAO settingDAO;

    private String mailing = ""; //$NON-NLS-1$
    private String srcEmail = ""; //$NON-NLS-1$
    private String subject = ""; //$NON-NLS-1$
    private String host = ""; //$NON-NLS-1$
    private String port = ""; //$NON-NLS-1$
    private String password = "";    //$NON-NLS-1$
    
    @Init
    public void init() {
        this.srcEmail = settingDAO.get("MailingTask.FROM"); //$NON-NLS-1$
        this.subject = settingDAO.get("MailingTask.SUBJECT"); //$NON-NLS-1$
        this.host = settingDAO.get("MailingTask.HOST"); //$NON-NLS-1$
        this.port = settingDAO.get("MailingTask.PORT"); //$NON-NLS-1$
        this.password = settingDAO.get("MailingTask.PASSWORD"); //$NON-NLS-1$
        this.mailing = settingDAO.get("MailingTask.EMAILTPL"); //$NON-NLS-1$
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
        mailing.replaceAll("&", "&amp;"); //$NON-NLS-1$ //$NON-NLS-2$
        mailing.replaceAll("\n", "<br/>"); //$NON-NLS-1$ //$NON-NLS-2$
        return mailing;
    }
    public void setMailing(String mailing) {
        this.mailing = mailing.replaceAll("&amp;", "&"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Command
    @NotifyChange({"mailing"})
    public void resetMailing() {
        this.mailing = getMailing();
    }

    @Command
    public void saveMailing() {
        settingDAO.set("MailingTask.EMAILTPL", this.mailing); //$NON-NLS-1$
        settingDAO.set("MailingTask.FROM", this.srcEmail); //$NON-NLS-1$
        settingDAO.set("MailingTask.SUBJECT", this.subject); //$NON-NLS-1$
        settingDAO.set("MailingTask.PASSWORD", this.password); //$NON-NLS-1$
        settingDAO.set("MailingTask.HOST", this.host); //$NON-NLS-1$
        settingDAO.set("MailingTask.PORT", this.port); //$NON-NLS-1$
        Messagebox.show(Labels.getLabel("admin.mailing.update"),  //$NON-NLS-1$
                Labels.getLabel("admin.title"), new Messagebox.Button[]{ //$NON-NLS-1$
                Messagebox.Button.OK}, Messagebox.INFORMATION,null);
    }

}

