package org.fpaitrault;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class PeriodicTask extends QuartzJobBean {
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

    }
}

