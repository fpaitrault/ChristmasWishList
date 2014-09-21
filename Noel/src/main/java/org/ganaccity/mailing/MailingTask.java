package org.ganaccity.mailing;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.ganaccity.interfaces.dao.SettingDAO;
import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.interfaces.dao.WishDAO;
import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class MailingTask extends QuartzJobBean {

    private WishDAO wishDao;
    private UserDAO userDao;
    private SettingDAO settingDao;

    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        String message = null;
        //Retrieve data access objects
        resolveDAOs(context);

        //Read database
        List<Wish> wishes = wishDao.getHotWishes();
        List<User> users = userDao.readAll();

        //Configure and send email for each user if necessary
        Session session = createEMailSession();
        for(User user : users) {
            //Check if user has a configured email
            if(user.getEmail() != null) {
                try {
                    message = EMailFactory.createEmail(user, wishes);
                    sendEMail(session, user.getEmail(), message);
                } catch (Exception e) {
                    e.printStackTrace();

                }   
            }
        }
    }
    private void sendEMail(Session session, String to, String msgText) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(settingDao.get("MailingTask.FROM"))); //$NON-NLS-1$
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(settingDao.get("MailingTask.SUBJECT")); //$NON-NLS-1$
            message.setContent(msgText, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private Session createEMailSession() {
        Session session = null;
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.auth", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("mail.smtp.starttls.enable", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("mail.smtp.port", "587"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("mail.smtp.host", "mail.gmx.com"); //$NON-NLS-1$ //$NON-NLS-2$
        // Get the default Session object.
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(settingDao.get("MailingTask.FROM"), //$NON-NLS-1$ 
                        settingDao.get("MailingTask.PASSWORD")); //$NON-NLS-1$
            }
        });
        Session.getDefaultInstance(properties);
        return session;
    }

    private void resolveDAOs(JobExecutionContext context) {
        if(wishDao == null || userDao == null || settingDao == null) {
            ApplicationContext appctx;
            try {
                appctx = (ApplicationContext)context.getScheduler().getContext().get("applicationContext");
                wishDao = (WishDAO) appctx.getBean("wishDAO");
                userDao = (UserDAO) appctx.getBean("userDAO");
                settingDao = (SettingDAO) appctx.getBean("settingDAO");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

}

