package org.fpaitrault.viewmdl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.fpaitrault.interfaces.dao.SettingDAO;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.mailing.EMailFactory;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Welcome {
    @WireVariable
    private SettingDAO settingDAO;

    public Welcome() {
    }
    
    
    public String getMessage() throws IOException, TemplateException {
        Map<String,Object> model = new HashMap<String, Object>();
        //Create data model
        //Calculate countdown
        Date today = Calendar.getInstance().getTime();
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(today.getYear()+1900, Calendar.DECEMBER, 25);
        Date christmas = cal.getTime();
        long diff = christmas.getTime() - today.getTime();
        long numDays = diff/(1000*60*60*24);
        model.put("countdown_d",  Long.toString(numDays/10) ); //$NON-NLS-1$
        model.put("countdown_u",  Long.toString(numDays%10) ); //$NON-NLS-1$
        
        //configure page processor
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(EMailFactory.class,"/"); //$NON-NLS-1$
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8"); //$NON-NLS-1$
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        
        Template tmpl = new Template("tpl", settingDAO.get("Welcome.TPL"), cfg); //$NON-NLS-1$ //$NON-NLS-2$
        
        StringWriter writer = new StringWriter();
        
        tmpl.process(model, writer);
        
        return writer.toString();
    }
    
}
