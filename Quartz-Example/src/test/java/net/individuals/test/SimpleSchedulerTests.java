package net.individuals.test;


import net.individuals.quartz.ScanDirectoryJob;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Barudisshu
 */
public class SimpleSchedulerTests {

    private static Logger logger = Logger.getLogger(SimpleSchedulerTests.class);

    private Scheduler createScheduler() throws SchedulerException {//创建调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    private void scheduleJob(Scheduler scheduler) throws SchedulerException {
        // Create a job detail for the job
        JobDetail jobDetail = newJob(ScanDirectoryJob.class)
                .withIdentity("ScanDirectory", "jobDetail-group")
                .withDescription("ScanDirectory from tomcat conf")
                .build();

        // Configure the directory to scan
        jobDetail.getJobDataMap()
                .put("SCAN_DIR", "D:\\Program Files\\IntelliJ IDEA 12.1.5\\runtime\\apache-tomcat-7.0.39\\conf");   // Set the jobDataMap that is associated with the job

        // Create a trigger that fires every 10 seconds,forever
        Trigger trigger = newTrigger()
                .withIdentity("scanTrigger", "trigger-group")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)) // 每十秒触发一次
                .build();


        //Associate the trigger with the job in the schedule
        scheduler.scheduleJob(jobDetail, trigger);

    }

    @Test
    public void ScanDirectoryTests() {
        SimpleSchedulerTests simpleSchedulerTests = new SimpleSchedulerTests();
        try {
            Scheduler scheduler = simpleSchedulerTests.createScheduler();

            // Start the scheduler running
            scheduler.start();

            logger.info("Scheduler started at " + new Date());
            simpleSchedulerTests.scheduleJob(scheduler);

            // Stop the scheduler after 10 second
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
