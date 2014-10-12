package org.ganaccity.mailing;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ganaccity.interfaces.dao.SettingDAO;
import org.ganaccity.interfaces.dao.UserDAO;
import org.ganaccity.interfaces.dao.WishDAO;
import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class EMailFactory {
    protected static final SimpleDateFormat dateFormat = 
            new SimpleDateFormat("dd/MM/yyyy");
    protected Calendar calendar;
    private SettingDAO settingDao;

    public EMailFactory(JobExecutionContext context) {
        calendar = Calendar.getInstance();
        resolveDAOs(context);
    }

    private void resolveDAOs(JobExecutionContext context) {
        if(settingDao == null) {
            ApplicationContext appctx;
            try {
                appctx = (ApplicationContext)context.getScheduler().getContext().get("applicationContext");
                settingDao = (SettingDAO) appctx.getBean("settingDAO");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String createEmail(User user, List<Wish> wishes) throws IOException, TemplateException {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(EMailFactory.class,"/");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        Template tmpl = new Template("tpl", settingDao.get("MailingTask.EMAILTPL"), cfg);
        
        StringWriter writer = new StringWriter();
        tmpl.process(createModel(user, wishes), writer);
        
        return writer.toString();
    }

    public Map<String,Object> createModel(User user, List<Wish> wishes) {
        Map<String,Object> model = new HashMap<String,Object>();
        List<Wish> resWishes = new LinkedList<Wish>();
        for(Wish wish : wishes) {
            //Remove wishes that are for current user
            if(!wish.getDest().equals(user) && user.getFriends().contains(wish.getDest())) {
                resWishes.add(wish);
            }
        }
        model.put("wishes", resWishes);
        
        //Calculate countdown
        Date today = calendar.getTime();
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(today.getYear()+1900, Calendar.DECEMBER, 25);
        Date christmas = cal.getTime();
        long diff = christmas.getTime() - today.getTime();
        model.put("countdown",  Long.toString(diff/(1000*60*60*24)));
        model.put("user",  user.getName());
        return model;
    }
}
