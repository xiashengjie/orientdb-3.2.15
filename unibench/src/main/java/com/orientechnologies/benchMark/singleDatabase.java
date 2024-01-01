//package com.orientechnologies.benchMark;
//
//import com.orientechnologies.utils.ConfigUtils;
//import com.orientechnologies.utils.CpolarUtils;
//import com.orientechnologies.utils.OrientdbEnum;
//import com.orientechnologies.utils.PropertiesEnum;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//
//import java.util.Properties;
//import java.util.Random;
//import java.util.concurrent.*;
//
///**
// * @author xsj
// */
//public class singleDatabase implements Job {
//
//    private final static String [] QUERY_LIST = new String[]{"Q1","Q2","Q3","Q4","Q5","Q6","Q8","Q9","Q10"};
//
//    public static void main(String[] args) {
//        executeQuery();
//    }
//
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) {
//        executeQuery();
//    }
//
//
//    /* 开启随机线程数执行查询*/
//
//    public static void executeQuery(){
//        QueryUtils.printInfo();
//        // Create a thread pool
//        Properties properties = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);
//
//        Random random = new Random();
//        int threads = random.nextInt(Integer.parseInt(properties.getProperty("database.threadsArea")));
//
//        System.out.println("开启的线程数为："+threads);
//
///*      corePoolSize:核心线程池大小
//        maximumPoolSize:最大核心线程池大小
//        keepAliveTime:空闲线程存活时间
//        unit:时间单位
//        workQueue:阻塞队列
//        threadFactory:线程工厂：创建线程的，一般不用动
//        handler:拒绝策略
//        new ThreadPoolExecutor.AbortPolicy() // 不执行新任务，直接抛出异常，提示线程池已满
//        new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！由调用线程处理该任务
//        new ThreadPoolExecutor.DiscardPolicy() //不执行新任务，也不抛出异常
//        new ThreadPoolExecutor.DiscardOldestPolicy() //丢弃队列最前面的任务，然后重新提交被拒绝的任务。*/
//
////        QueryUtils.cleanCache();
////        QueryUtils.startOrientdb();
//        ExecutorService executor = new ThreadPoolExecutor(
//                threads,
//                threads+5,
//                3,
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(3),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.DiscardOldestPolicy());
//        long currentTimeMillis = System.currentTimeMillis();
//        String taskName = "task"+currentTimeMillis;
//        OrientdbEnum orientdbEnum = OrientdbEnum.values()[new Random().nextInt(OrientdbEnum.values().length)];
//        // Submit multiple tasks
//        String dbUrl = CpolarUtils.getOrientdbUrl();
//        for (int i = 0; i < threads; i++) {
//            String query = QueryUtils.randomChoice(QUERY_LIST);
//                executor.submit(() -> {
//                    QueryUtils.resultToFile(taskName,orientdbEnum,query,threads,false,false,dbUrl);
//                });
//        }
//        // Shutdown the thread pool
//        executor.shutdown();
//    }
//}
