package com.orientechnologies.benchMark;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author xsj
 */
public class QuartzJob {

    public static void main(String[] args) throws SchedulerException, InterruptedException {

        // 1、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(Threads.class)
                .withIdentity("triggerOne", "triggerGroupOne").build();
        // 2、构建Trigger实例
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerOne", "triggerGroupOne")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        //每隔600s执行一次
                        .withIntervalInSeconds(3600)
                        //一直执行
                        .repeatForever()).build();

        //3、创建调度器Scheduler并执行
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

//        //睡眠
//        TimeUnit.HOURS.sleep(6);
//        scheduler.shutdown();
//        System.out.println("--------scheduler shutdown ! ------------");

    }
}


