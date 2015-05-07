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
public class MultiJobSchedulerTests {

    private static Logger logger = Logger.getLogger(MultiJobSchedulerTests.class);

    private Scheduler createScheduler() throws SchedulerException {//创建调度器
        return new StdSchedulerFactory().getScheduler();
    }

    private void scheduleJob(Scheduler scheduler, String jobName, Class<? extends Job> jobClass, String scanDir, String triggerName, int scanInterval) throws SchedulerException {
        // Create a job detail for the job
        JobDetail jobDetail = newJob(jobClass)
                .withIdentity(jobName, "jobDetail-group")
                .build();

        // Configure the directory to scan
        jobDetail.getJobDataMap()
                .put("SCAN_DIR", scanDir);   // Set the jobDataMap that is associated with the job

        // Create a trigger that fires every 10 seconds,forever
        Trigger trigger = newTrigger()
                .withIdentity(triggerName, "trigger-group")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(scanInterval)) // 每十秒触发一次
                .build();


        //Associate the trigger with the job in the schedule
        scheduler.scheduleJob(jobDetail, trigger);

    }

    @Test
    public void multiScanTests() {

        MultiJobSchedulerTests multiJobSchedulerTests = new MultiJobSchedulerTests();

        try {
            Scheduler scheduler = multiJobSchedulerTests.createScheduler();

            // Scheduler the first job
            multiJobSchedulerTests.scheduleJob(scheduler, "ScanTomcat", ScanDirectoryJob.class, "D:\\Program Files\\IntelliJ IDEA 12.1.5\\runtime\\apache-tomcat-7.0.39\\conf", "tomcatTrigger", 10);

            // Scheduler the second job
            multiJobSchedulerTests.scheduleJob(scheduler, "ScanGlassfish", ScanDirectoryJob.class, "D:\\Program Files\\IntelliJ IDEA 12.1.5\\runtime\\glassfish4\\glassfish\\domains\\domain1\\config", "glassfishTrigger", 15);

            // Start the scheduler running
            scheduler.start();

            logger.info("Scheduler started at " + new Date());

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
