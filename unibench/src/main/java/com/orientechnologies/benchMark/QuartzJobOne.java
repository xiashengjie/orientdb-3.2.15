//package com.orientechnologies.benchMark;
//
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author xsj
// */
//public class QuartzJobOne {
//
//    public static void main(String[] args) throws SchedulerException, InterruptedException {
//        // 1、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
//        JobDetail jobDetail = JobBuilder.newJob(singleDatabase.class)
//                .withIdentity("triggerOne", "triggerGroupOne").build();
//
////        Date startDate = new Date();
////        startDate.setTime(startDate.getTime() + 23400000);
////
////        Date endDate = new Date();
////        endDate.setTime(startDate.getTime() + 5000);
//
//        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerOne", "triggerGroupOne")
//                .usingJobData("triggerOne", "这是jobDetail1的trigger")
//                .startNow()//立即生效
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(4400)//每隔1s执行一次
//                        .repeatForever()).build();//一直执行
//
//
//
//        //3、创建调度器Scheduler并执行
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.scheduleJob(jobDetail, trigger);
//        System.out.println("--------scheduler start ! ------------");
//        scheduler.start();
//
////        //睡眠
////        TimeUnit.HOURS.sleep(10);
////        scheduler.shutdown();
////        System.out.println("--------scheduler shutdown ! ------------");
//
//    }
//}
//
//
