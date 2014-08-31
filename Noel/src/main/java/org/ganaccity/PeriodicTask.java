package org.ganaccity;

import org.ganaccity.interfaces.dao.WishDAO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class PeriodicTask extends QuartzJobBean {
    @Autowired @Qualifier("wishDAO")
    private WishDAO wishDAO;

    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

        System.out.println("Num. wishes: " + wishDAO.getHotWishes().size());

    }
}

