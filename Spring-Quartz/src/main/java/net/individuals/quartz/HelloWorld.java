package net.individuals.quartz;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * @author Barudisshu
 */
public class HelloWorld{

    private static Logger logger = Logger.getLogger(HelloWorld.class);

    public HelloWorld() {
    }

    /**
     * spring 检测要求不带参数
     */
    public void execute() {
        logger.info("-----------------------------------------" +
                "\n\nKick your ass and fuck your mother! \n\n" +
                "-----------------------------------------" +
                new Date());
    }
}
