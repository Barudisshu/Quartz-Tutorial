package net.individuals.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author Barudisshu
 */
public class HelloWorld implements Job {

    private static Logger logger = Logger.getLogger(HelloWorld.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("\n---------------------------------" +
                "\n\nHello World! \n\n" +
                "---------------------------------\n" +
                new Date());
    }
}
