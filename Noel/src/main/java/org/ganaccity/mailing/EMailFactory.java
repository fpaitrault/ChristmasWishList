package org.ganaccity.mailing;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ganaccity.mdl.User;
import org.ganaccity.mdl.Wish;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class EMailFactory {
    public static String createEmail(User user, List<Wish> wishes) throws IOException, TemplateException {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(EMailFactory.class,"/");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        
        Template tmpl = cfg.getTemplate("email.tpl");
        
        StringWriter writer = new StringWriter();
        tmpl.process(createModel(user, wishes), writer);
        
        return writer.toString();
    }

    private static Map<String,List<Wish>> createModel(User user, List<Wish> wishes) {
        Map<String,List<Wish>> model = new HashMap<String,List<Wish>>();
        List<Wish> resWishes = new LinkedList<Wish>();
        for(Wish wish : wishes) {
            //Remove wishes that are for current user
            if(!wish.getDest().equals(user)) {
                resWishes.add(wish);
            }
        }
        model.put("wishes", resWishes);
        return model;
    }
}
