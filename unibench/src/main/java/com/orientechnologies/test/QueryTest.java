package com.orientechnologies.test;


import com.orientechnologies.benchMark.QueryUtils;
import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.OrientdbEnum;
import com.orientechnologies.utils.PropertiesEnum;
import org.junit.Test;

import java.util.Properties;

/**
 * @author xsj
 */
public class QueryTest {

    private final static String [] QUERY_LIST = new String[]{"Q1","Q4","Q5","Q6","Q8","Q9","Q10"};

    @Test
    public void testSingleQuery(){
        QueryUtils.printInfo();
        // Create a thread pool
        String query = QueryUtils.randomChoice(QUERY_LIST);
        QueryUtils.resultToFile(OrientdbEnum.SF1,query,0,true);
    }

    @Test
    public void testConfig(){
        Properties properties = ConfigUtils.getConfig(PropertiesEnum.SSH);
        String userName = properties.getProperty("ssh.username");
        String password = properties.getProperty("ssh.password");
        System.out.println(userName);
        System.out.println(password);
    }

}
