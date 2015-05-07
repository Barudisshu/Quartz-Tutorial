package net.individuals.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author Barudisshu
 */
public class LoadXmlTests {


    private static Logger logger = Logger.getLogger(LoadXmlTests.class);

    private Scheduler createScheduler() throws SchedulerException {//创建调度器
        return new StdSchedulerFactory("scan-quartz.properties").getScheduler();
    }

    @Test
    public void ScanDirectoryTests() {
        LoadXmlTests loadXmlTests = new LoadXmlTests();
        try {
            Scheduler scheduler = loadXmlTests.createScheduler();

            // Start the scheduler running
            scheduler.start();

            logger.info("Scheduler started at " + new Date());

            // Stop the scheduler after 10 second
            Thread.sleep(20000);
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
