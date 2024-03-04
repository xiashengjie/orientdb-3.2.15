package com.orientechnologies.benchMark;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.CpolarUtils;
import com.orientechnologies.utils.OrientdbEnum;
import com.orientechnologies.utils.PropertiesEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xsj
 */
public class QueryUtils {
    private static final Logger log = LoggerFactory.getLogger(QueryUtils.class);
    private static final String DEFAULT_CHART="UTF-8";




    public static String randomChoice(String[] array) {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(array.length);
        return array[index];
    }


    /**
     * 执行对应查询
     */
    public static long runQuery(String query,OrientdbEnum orientdbEnum,String  dbUrl) throws Exception{
//           MMDB db = new OrientQueryNew();
           MMDB db = new Arango();
//           MMDB db = new OrientQuery();
        System.out.println(query+" is running");
//        String dbUrl = "";
        Path currentDir = Paths.get(".");
        switch(query) {
            case "Q1":
                List<String> P1;
                    P1 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/PersonIds"));
                    String personIdQ1 = randomChoice(P1.toArray(new String[0]));
                    return db.Q1(personIdQ1,orientdbEnum,dbUrl);
            case "Q2":
                List<String> P2;
                    P2 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/ProductIds"));
                    String productIdQ2 = randomChoice(P2.toArray(new String[0]));
                    return db.Q2(productIdQ2,orientdbEnum,dbUrl);
            case "Q3":
                List<String> P3;
                    P3 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/ProductIds"));
                    String productIdQ3 = randomChoice(P3.toArray(new String[0]));
                    return db.Q3(productIdQ3,orientdbEnum,dbUrl);
            case "Q4":
                List<String> P4;
                    return db.Q4(orientdbEnum,dbUrl);
            case "Q5":
                List<String> P5_1;
                List<String> P5_2;
                    P5_1 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/PersonIds"));
                    P5_2 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/Brands"));
                    String personIdQ5 = randomChoice(P5_1.toArray(new String[0]));
                    String brandQ5 = randomChoice(P5_2.toArray(new String[0]));
                    return db.Q5(personIdQ5,brandQ5,orientdbEnum,dbUrl);
            case "Q6":
                List<String> P6;
                    P6 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/PersonIds"));
                    String srcQ6 = randomChoice(P6.toArray(new String[0]));
                    String dstQ6 = randomChoice(P6.toArray(new String[0]));
                    return db.Q6(srcQ6, dstQ6,orientdbEnum,dbUrl);
            case "Q7":
                List<String> P7;
                    P7 = Files.readAllLines(Paths.get(currentDir.toAbsolutePath()+"/Brands"));
                    String brandQ7 = randomChoice(P7.toArray(new String[0]));
                    return db.Q7(brandQ7,orientdbEnum,dbUrl);
            case "Q8":
                    return db.Q8(orientdbEnum,dbUrl);
            case "Q9":
                    return db.Q9(orientdbEnum,dbUrl);
            case "Q10":
                    return db.Q10(orientdbEnum,dbUrl);
            default:break;
        }
        return 0;
    }


    /**
     * 将结果写入文件
     */
    public static void writeToFile(String taskName,String database,String query,long millisStart,long millisEnd,int n,String status,boolean cacheClean,boolean mixed){
        try{
            Properties properties = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);
            long time = millisEnd - millisStart;
            String content = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    taskName,database,query,millisStart,millisEnd,time,n,status,
                    cacheClean,mixed,properties.getProperty("orientdb.cpu"),
                    properties.getProperty("orientdb.pod"),
                    properties.getProperty("minio.pod"));
//            File file =new File("orientdb-juicefs-new-new.txt");
            File file =new File("arangodb-juicefs-new-new.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            //使用true，即进行append file
            FileWriter fileWriter = new FileWriter(file.getName(),true);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println(query+" finish");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 打印基本信息
     */
    public static void printInfo(){
        System.out.println("The benchmarking is starting...");
        System.out.println("----------------");
        System.out.println("----------------");
        System.out.println("----------------");
        // 创建一个DateTimeFormatter对象，用于定义时间输出的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 获取当前的LocalDateTime对象
        LocalDateTime now = LocalDateTime.now();

        // 使用DateTimeFormatter对象格式化当前时间
        String formattedDateTime = now.format(formatter);

        // 打印格式化后的当前时间
        System.out.println("当前的日期和时间是: " + formattedDateTime);
    }

    public static void refreshDatabase(OrientdbEnum orientdbEnum){
        HashMap<String, String> sshUrl = CpolarUtils.getSSHUrl();
        Connection sshConnection = login(sshUrl.get("node3.url"), Integer.parseInt(sshUrl.get("node3.port")));
        //Connection sshConnection2 = login(sshUrl.get("node3.url"), Integer.parseInt(sshUrl.get("node3.port")));
        String command = orientdbEnum.getRefreshCmd();
        //String[] split = command.split(";");

        System.out.println("---------------刷新数据库 "+orientdbEnum.getName()+" 中---------------");
        String result = execute(sshConnection, command);
        //String result2 = execute(sshConnection2, split[1]);
        System.out.println(result);
        //System.out.println(result2);
        System.out.println("刷新完毕");
        sshConnection.close();
        //sshConnection2.close();
    }

    public static void reboot(){
        HashMap<String, String> sshUrl = CpolarUtils.getSSHUrl();
        Connection sshConnection1 = login(sshUrl.get("master.url"), Integer.parseInt(sshUrl.get("master.port")));
        Connection sshConnection2 = login(sshUrl.get("node1.url"), Integer.parseInt(sshUrl.get("node1.port")));
        Connection sshConnection3 = login(sshUrl.get("node2.url"), Integer.parseInt(sshUrl.get("node2.port")));
        Connection sshConnection4 = login(sshUrl.get("node3.url"), Integer.parseInt(sshUrl.get("node3.port")));
        Properties config = ConfigUtils.getConfig(PropertiesEnum.COMMAND);
        String reboot = config.getProperty("reboot");
        System.out.println("重启所有机器");
        String result1 = execute(sshConnection1, reboot);
        String result2 = execute(sshConnection2, reboot);
        String result3 = execute(sshConnection3, reboot);
        String result4 = execute(sshConnection4, reboot);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);


    }

    /**
     * 执行查询并将结果写入文件
     */
    public static void resultToFile(String taskName,OrientdbEnum orientdbEnum,String query, int threads, boolean cacheClean,boolean mixed,String  dbUrl) throws IOException {
        long[] millisStart = new long[1];
        long[] millisEnd = new long[1];
        String[] status = new String[1];
        String database = orientdbEnum.getName();
        System.out.println("数据库是："+database);
        System.out.println("将要执行的查询是："+query);

        String content = String.format("%s,%s,%s,%s,%s\n",
                taskName,database,query,System.currentTimeMillis(),threads);
        File file =new File("arangodb-juicefs-new-new-all.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        //使用true，即进行append file
        FileWriter fileWriter = new FileWriter(file.getName(),true);
        fileWriter.write(content);
        fileWriter.close();

        status[0] = "success";
        try {
            System.out.println("----------------查询执行中---------------");
            millisStart[0] = System.currentTimeMillis();
            long time  = runQuery(query, orientdbEnum, dbUrl);
            if(time<0){
                time = -time;
            }
            millisEnd[0] = millisStart[0]+time;
            System.out.println("----------------查询执行完毕---------------");
        }
//        catch (ODatabaseException e){
//            status[0] = "failed";
//            System.out.println("----------------数据库 "+database+" 被更改，查询执行失败---------------");
//            e.printStackTrace();
//            refreshDatabase(orientdbEnum);
//            System.out.println("----------------重新执行查询---------------");
//            resultToFile(orientdbEnum,query,threads,cacheClean);
//        }
        catch (Exception e){
            status[0] = "failed";
            millisEnd[0] = System.currentTimeMillis();
            System.out.println("----------------查询执行失败---------------");
            System.out.println(e);
            e.printStackTrace();

//            QueryUtils.refreshDatabase(orientdbEnum);
//            resultToFile(taskName,orientdbEnum,query,threads,cacheClean,mixed);
        }finally {
            writeToFile(taskName,database,query, millisStart[0], millisEnd[0],threads,status[0],cacheClean,mixed);
        }
    }


    /**
     * 登录主机
     * @return
     *      登录成功返回true，否则返回false
     */
    public static Connection login(String ip,int port){

        boolean flg;
        Connection conn = null;
        try {
            conn = new Connection(ip, port);
            conn.connect();//连接
            Properties properties = ConfigUtils.getConfig(PropertiesEnum.SSH);
            String userName = properties.getProperty("ssh.username");
            String password = properties.getProperty("ssh.password");
            //认证
            flg=conn.authenticateWithPassword(userName, password);
            if(flg){
                log.info("=========登录成功========="+conn);
                return conn;
            }
        } catch (IOException e) {
            log.error("=========登录失败========="+e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 远程执行shell脚本或者命令
     * @param cmd
     *      即将执行的命令
     * @return
     *      命令执行完后返回的结果值
     */
    public static String execute(Connection conn,String cmd){
        String result="";
        try {
            if(conn !=null){
                //打开一个会话
                Session session= conn.openSession();
                //执行命令
                session.execCommand(cmd);
                result=processStdout(session.getStdout(),DEFAULT_CHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(StringUtils.isBlank(result)){
                    log.info("得到标准输出为空,链接conn:"+conn+",执行的命令："+cmd);
                    result=processStdout(session.getStderr(),DEFAULT_CHART);
                }else{
                    log.info("执行命令成功,链接conn:"+conn+",执行的命令："+cmd);
                }
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            log.info("执行命令失败,链接conn:"+conn+",执行的命令："+cmd+"  "+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 解析脚本执行返回的结果集
     * @param in 输入流对象
     * @param charset 编码
     * @return
     *       以纯文本的格式返回
     */
    private static String processStdout(InputStream in, String charset){
        InputStream  stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line;
            while((line=br.readLine()) != null){
                buffer.append(line+"\n");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("解析脚本出错："+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("解析脚本出错："+e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public static void cleanCache(){

        HashMap<String, String> sshUrl = CpolarUtils.getSSHUrl();

        Connection sshConnectionMaster = login(sshUrl.get("master.url"), Integer.parseInt(sshUrl.get("master.port")));
        Connection sshConnectionNode1 = login(sshUrl.get("node1.url"), Integer.parseInt(sshUrl.get("node1.port")));
        Connection sshConnectionNode2 = login(sshUrl.get("node2.url"), Integer.parseInt(sshUrl.get("node2.port")));
        Connection sshConnectionNode3 = login(sshUrl.get("node3.url"), Integer.parseInt(sshUrl.get("node3.port")));


        Properties commandConfig = ConfigUtils.getConfig(PropertiesEnum.COMMAND);
        /*master*/
        String commandMaster = commandConfig.getProperty("cleanAlluxio");

        /*node*/
        String commandNode = commandConfig.getProperty("deleteCache");

        String result = execute(sshConnectionMaster, commandMaster);
        String result1 = execute(sshConnectionNode1, commandNode);
        String result2 = execute(sshConnectionNode2, commandNode);
        String result3 = execute(sshConnectionNode3, commandNode);

        sshConnectionMaster.close();
        sshConnectionNode1.close();
        sshConnectionNode2.close();
        sshConnectionNode3.close();

        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        if(!result.contains("Error")){
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void cleanCacheJuiceFs(){

        HashMap<String, String> sshUrl = CpolarUtils.getSSHUrl();

        Connection sshConnectionMaster = login(sshUrl.get("master.url"), Integer.parseInt(sshUrl.get("master.port")));
        Connection sshConnectionNode1 = login(sshUrl.get("node1.url"), Integer.parseInt(sshUrl.get("node1.port")));
        Connection sshConnectionNode2 = login(sshUrl.get("node2.url"), Integer.parseInt(sshUrl.get("node2.port")));
        Connection sshConnectionNode3 = login(sshUrl.get("node3.url"), Integer.parseInt(sshUrl.get("node3.port")));


        Properties commandConfig = ConfigUtils.getConfig(PropertiesEnum.COMMAND);
        /*master*/
        String commandMaster = commandConfig.getProperty("cleanAlluxio");

        /*node*/
        String commandNode = commandConfig.getProperty("deleteCache");

        String result = execute(sshConnectionMaster, commandMaster);
        String result1 = execute(sshConnectionNode1, commandNode);
        String result2 = execute(sshConnectionNode2, commandNode);
        String result3 = execute(sshConnectionNode3, commandNode);

        sshConnectionMaster.close();
        sshConnectionNode1.close();
        sshConnectionNode2.close();
        sshConnectionNode3.close();

        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        if(!result.contains("Error")){
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void startOrientdb(){


        HashMap<String, String> sshUrl = CpolarUtils.getSSHUrl();
        Connection sshConnectionMaster = login(sshUrl.get("master.url"), Integer.parseInt(sshUrl.get("master.port")));
        Properties config = ConfigUtils.getConfig(PropertiesEnum.COMMAND);
        String startOrientDB = config.getProperty("startOrientDB");

        String result = execute(sshConnectionMaster, startOrientDB);
        System.out.println(result);
        if(result.contains("created")){
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void refreshEnv(){
        try {
            System.out.println("----------------刷新环境中---------------");
            cleanCache();
            startOrientdb();
            Thread.sleep(120000);
            System.out.println("----------------刷新环境成功---------------");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
