package net.individuals.quartz;

import net.individuals.filter.FileExtensionFileFilter;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

/**
 * 定义一个Job扫描一个目录中的文件，并显示文件的详细信息
 *
 * @author Barudisshu
 */
public class ScanDirectoryJob implements Job {

    private static Logger logger = Logger.getLogger(ScanDirectoryJob.class);

    /**
     * Job接口中的execute方法体内为一个逻辑事务，所有工作事务在这里完成
     *
     * @param context 执行上下文环境，包含jobDetail、trigger、jobDataMap、calendar等一系列组合子项
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // Every job has its own job detail
        JobDetail jobDetail = context.getJobDetail();

        // The name is defined in the job definition
        String jobName = ((JobDetailImpl)jobDetail).getName();   //任务名称

        // Log the time the job started
        logger.info(jobName + " fired at " + new Date());   //记录任务开始的时间

        // The directory to scan is stored in the job map
        JobDataMap dataMap = jobDetail.getJobDataMap();     //任务所配置的数据映射表

        String dirName = dataMap.getString("SCAN_DIR");     //获取要扫描的目录

        // Validate the required input
        if (dirName == null) {//所需要的扫描目录没有提供
            throw new JobExecutionException("Directory not configured");
        }

        // Make sure the directory exists
        File dir = new File(dirName);
        if (!dir.exists()) {//提供的是错误目录
            throw new JobExecutionException("Invalid Dir " + dirName);
        }

        // Use FileFilter to get only XML files
        FileFilter filter = new FileExtensionFileFilter(".xml");
        // 只统计XML文件
        File[] files = dir.listFiles(filter);

        if (files == null || files.length <= 0) {//目录下没有XML文件
            logger.info("No XML files found in " + dir);

            // Return since there were no files
            return;
        }

        // The number of XML files
        int size = files.length;
        logger.info("The number of XML files: " + size);

        // Iterate through the files found
        for (File file : files) {
            // Log something interesting about each file
            File aFile = file.getAbsoluteFile();
            long fileSize = file.length();
            String msg = aFile + " - Size: " + fileSize;

            logger.info(msg);   //记录下文件的路径和大小
        }
    }
}


























