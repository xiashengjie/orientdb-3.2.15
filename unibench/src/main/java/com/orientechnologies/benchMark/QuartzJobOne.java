package com.orientechnologies.benchMark;//package com.orientechnologies.benchMark;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QuartzJobOne {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // 设置一个Runnable任务
        Runnable task = () -> {
            // 这里写你要执行的任务代码
            Threads.executeQuery();
            System.out.println("任务执行了，当前时间：" + System.currentTimeMillis());
        };

        // 设置初始延迟为半小时（1800000毫秒），之后每小时（3600000毫秒）执行一次
        executor.scheduleAtFixedRate(task, 7200000, 5400000, TimeUnit.MILLISECONDS);
    }
}