package com.orientechnologies.test;

import ch.ethz.ssh2.Connection;
import com.orientechnologies.benchMark.QueryUtils;
import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.PropertiesEnum;
import com.orientechnologies.utils.CpolarUtils;
import org.junit.Test;

import java.util.Properties;


/**
 * @author xsj
 */
public class SshTest {


    @Test
    public void test(){
        String masterHostName = "2.tcp.vip.cpolar.cn";
        int masterPort = 10727;
        String cdPath = "cd /opt/fluid_alluxio_orientdb/";
        String deleteOrientdb = "kubectl delete -f orientdb.yaml";
        String deleteAlluxioRuntime= "kubectl delete -f runtime.yaml";
        String commandMaster = String.format("%s ; %s ; %s", cdPath, deleteOrientdb, deleteAlluxioRuntime);
        Connection connection = QueryUtils.login(masterHostName, masterPort);
        String result = QueryUtils.execute(connection, commandMaster);
        System.out.println(result);
    }

    @Test
    public void testStartOrientdb(){
        QueryUtils.startOrientdb();
    }


    @Test
    public void testClean(){
        QueryUtils.cleanCache();
    }


    @Test
    public void testDel(){
        String commandMaster = "kubectl delete -n default pod orientdb-data-worker-0";

        String deleteMem = "rm -rf /dev/shm/*";
        String deleteSsd = "rm -rf /mnt/ssd/*";
        String deleteHdd = "rm -rf /mnt/hdd/*";
        String commandNode = String.format("%s ; %s ; %s", deleteMem, deleteSsd, deleteHdd);

        String masterHostName = "2.tcp.vip.cpolar.cn";
        int masterPort = 10727;

        String node1HostName = "2.tcp.vip.cpolar.cn";
        int node1Port = 13945;

        String node2HostName = "2.tcp.vip.cpolar.cn";
        int node2Port = 10835;

        String node3HostName = "2.tcp.vip.cpolar.cn";
        int node3Port = 14447;

        Connection sshConnectionMaster = QueryUtils.login(masterHostName, masterPort);
        Connection sshConnectionNode1 = QueryUtils.login(node1HostName, node1Port);
        Connection sshConnectionNode2 = QueryUtils.login(node2HostName, node2Port);
        Connection sshConnectionNode3 = QueryUtils.login(node3HostName, node3Port);


        String result1 = QueryUtils.execute(sshConnectionNode1, commandNode);
        String result2 = QueryUtils.execute(sshConnectionNode2, commandNode);
        String result3 = QueryUtils.execute(sshConnectionNode3, commandNode);
        String result = QueryUtils.execute(sshConnectionMaster, commandMaster);

        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    public void testGetUrl(){
        CpolarUtils.getSSHUrl();
    }


    @Test
    public void test1(){
        Properties config = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);
        System.out.println(123);
    }
}
