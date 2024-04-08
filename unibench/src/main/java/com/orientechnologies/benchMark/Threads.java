package com.orientechnologies.benchMark;

import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.CpolarUtils;
import com.orientechnologies.utils.OrientdbEnum;
import com.orientechnologies.utils.PropertiesEnum;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author xsj
 */
public class Threads implements Job {

//    "remote:122.51.75.59:9024",,"remote:122.51.75.59:9028"
    private final static String [] QUERY_LIST = new String[]{"Q1","Q2","Q3","Q4","Q5","Q7","Q6","Q8","Q9","Q10"};
//    private final static String [] QUERY_LIST = new String[]{"Q10"};
//    private final static String [] SERVER_LIST = new String[]{"remote:192.168.103.95:2424","remote:192.168.103.135:2425","remote:192.168.103.80:2424"};
//    private final static String [] SERVER_LIST = new String[]{"remote:122.51.75.59:9025","remote:122.51.75.59:9026","remote:122.51.75.59:9027"};
    private final static String [] SERVER_LIST = new String[]{"remote:122.51.75.59:9025","remote:122.51.75.59:9027","remote:122.51.75.59:9028","remote:122.51.75.59:9028","remote:122.51.75.59:9027","remote:122.51.75.59:9025"};
//    private final static String [] SERVER_LIST = new String[]{"122.51.75.59:8530","122.51.75.59:8531","122.51.75.59:8532"};
//    private final static String [] SERVER_LIST = new String[]{"remote:2.tcp.vip.cpolar.cn:12880","remote:2.tcp.vip.cpolar.cn:11321","remote:3.tcp.vip.cpolar.cn:12735"};
//    private final static String [] QUERY_LIST = new String[]{"Q7"};
    public static void main(String[] args) {

//        try {
//            // 休眠半小时（30分钟），即30 * 60 * 1000 毫秒
//            Thread.sleep(30 * 60 * 1000);
//        } catch (InterruptedException e) {
//            // 处理中断异常
//            e.printStackTrace();
//        }
        executeQuery();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        executeQuery();
    }


    /* 开启随机线程数执行查询*/

    public static void executeQuery(){
        QueryUtils.printInfo();
        // Create a thread pool
        Properties properties = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);

        java.util.Random random = new java.util.Random();
//        int threads = random.nextInt(Integer.parseInt(properties.getProperty("database.threadsArea")))+1;
        int threads = 200;
        System.out.println("开启的线程数："+threads);

/*      corePoolSize:核心线程池大小
        maximumPoolSize:最大核心线程池大小
        keepAliveTime:空闲线程存活时间
        unit:时间单位
        workQueue:阻塞队列
        threadFactory:线程工厂：创建线程的，一般不用动
        handler:拒绝策略
        new ThreadPoolExecutor.AbortPolicy() // 不执行新任务，直接抛出异常，提示线程池已满
        new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！由调用线程处理该任务
        new ThreadPoolExecutor.DiscardPolicy() //不执行新任务，也不抛出异常
        new ThreadPoolExecutor.DiscardOldestPolicy() //丢弃队列最前面的任务，然后重新提交被拒绝的任务。*/

//        QueryUtils.cleanCache();
//        QueryUtils.startOrientdb();
        ExecutorService executor = new ThreadPoolExecutor(
                threads,
                threads+5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        long currentTimeMillis = System.currentTimeMillis();
        String taskName = "task"+currentTimeMillis;
//        String dbUrl = CpolarUtils.getOrientdbUrl();
//        System.out.println(dbUrl);
//        String dbUrl = "remote:localhost:2424;localhost:2425";
//        String dbUrl = "remote:192.168.10.127:2424;192.168.10.127:2425/";
//        String dbUrl = "remote:2.tcp.vip.cpolar.cn:12880;2.tcp.vip.cpolar.cn:11321;3.tcp.vip.cpolar.cn:12735/";
//        String dbUrl = "remote:2.tcp.vip.cpolar.cn:11321/";
        // Submit multiple tasks
//        MMDB db = new OrientQueryNew(dbUrl);
//        MMDB db = new OrientQueryNew();
        int j = 0;
        int q = 1;
        int d = 1;
        for (int i = 0; i < threads; i++) {
//            String query = QueryUtils.randomChoice(QUERY_LIST);
            String query = "Q"+q;
            q = q+1;
            if(q>10)q=1;
//            String server = QueryUtils.randomChoice(SERVER_LIST);
//            String server = "remote:122.51.75.59:9025";
//            String server = "122.51.75.59";
            String server = SERVER_LIST[j];
//            System.out.println(server);
//            System.out.println(j);
            j = j+1;
            if (j==SERVER_LIST.length){
                j = 0;
            }
            OrientdbEnum orientdbEnum;
//            String server = "remote:192.168.103.95:2424;192.168.103.135:2424";
//            OrientdbEnum orientdbEnum = OrientdbEnum.values()[new Random().nextInt(OrientdbEnum.values().length)];
            if (d==1){
                orientdbEnum = OrientdbEnum.SF1;
            } else if (d==2) {
                orientdbEnum = OrientdbEnum.SF10;
            }else {
                orientdbEnum = OrientdbEnum.SF30;
            }
            d = d+1;
            if (d>3)d=1;
//            OrientdbEnum orientdbEnum = OrientdbEnum.SF10;
            System.out.println(query + orientdbEnum.getName());
                executor.submit(() -> {
                    try {
                        QueryUtils.resultToFile(taskName,orientdbEnum,query,threads,false,true,server);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
//        db.close();
        // Shutdown the thread pool
        executor.shutdown();
    }
}
