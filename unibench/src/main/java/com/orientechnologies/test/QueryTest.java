package com.orientechnologies.test;


import com.orientechnologies.benchMark.QueryUtils;
import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.CpolarUtils;
import com.orientechnologies.utils.OrientdbEnum;
import com.orientechnologies.utils.PropertiesEnum;
import org.junit.Test;

import java.util.Properties;
import java.util.Random;

/**
 * @author xsj
 */
public class QueryTest {

    private final static String [] QUERY_LIST = new String[]{"Q1","Q4","Q5","Q6","Q8","Q9","Q10"};

//    @Test
//    public void testSingleQuery(){
//        QueryUtils.printInfo();
//        String query = "Q7";
//        long currentTimeMillis = System.currentTimeMillis();
//        String taskName = "task"+currentTimeMillis;
//        String dbUrl = CpolarUtils.getOrientdbUrl();
//        QueryUtils.resultToFile(taskName,OrientdbEnum.SF1,query,0,true,false,dbUrl);
//    }

    /*不同大小数据库同时查询*/

    @Test
    public void testDiffDatabaseQuery(){
        QueryUtils.printInfo();
        String query = QueryUtils.randomChoice(QUERY_LIST);
        String query2 = QueryUtils.randomChoice(QUERY_LIST);
        OrientdbEnum randomDB1= OrientdbEnum.values()[new Random().nextInt(OrientdbEnum.values().length)];
        OrientdbEnum randomDB2= OrientdbEnum.values()[new Random().nextInt(OrientdbEnum.values().length)];

        long currentTimeMillis = System.currentTimeMillis();
        String taskName = "task"+currentTimeMillis;
        String dbUrl = CpolarUtils.getOrientdbUrl();
//        QueryUtils.resultToFile(taskName,randomDB1,query,0,true,true,dbUrl);
//        QueryUtils.resultToFile(taskName,randomDB2,query2,0,true,true,dbUrl);
    }


    @Test
    public void testConfig(){
        Properties properties = ConfigUtils.getConfig(PropertiesEnum.SSH);
        String userName = properties.getProperty("ssh.username");
        String password = properties.getProperty("ssh.password");
        System.out.println(userName);
        System.out.println(password);
    }


    @Test
    public void testRefresh(){
        //QueryUtils.refreshDatabase(OrientdbEnum.SF1);
        //QueryUtils.refreshDatabase(OrientdbEnum.SF10);
        QueryUtils.refreshDatabase(OrientdbEnum.SF30);
    }

    @Test
    public void testReboot(){
        QueryUtils.reboot();
    }
}
