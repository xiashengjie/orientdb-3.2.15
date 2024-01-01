package com.orientechnologies.utils;

import com.orientechnologies.benchMark.QueryUtils;
import com.orientechnologies.entity.CpolarEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author xsj
 */
public class CpolarUtils {
    private static int steps = 4;
    private static ArrayList<CpolarEntity> getCpolarEntityList(String mail,String password){
        // 构造登录表单数据
        Map<String, String> formData = new HashMap<>(2);
        formData.put("login", mail);
        formData.put("password", password);

        Connection.Response loginResponse;
        Document targetPageDoc = null;
        try {
            // 发送POST请求并登录
            loginResponse = Jsoup.connect("https://dashboard.cpolar.com/login")
                    .method(Connection.Method.POST)
                    .data(formData)
                    .execute();

            targetPageDoc = Jsoup.connect("https://dashboard.cpolar.com/status")
                    // 使用登录后的cookies
                    .cookies(loginResponse.cookies())
                    .get();
        }catch (Exception e){
            System.out.println("登录失败");
        }

        Elements elements = targetPageDoc.select("tbody");
        Element element = elements.get(0);

        Elements urls = element.select("a");
        Elements info = element.select("td");
        int size = urls.size();

        ArrayList<CpolarEntity> cpolarEntities = new ArrayList<>();
        String[] urlArr = new String[size];
        String[] nameArr = new String[size];

        for (int i = 0; i <= size-1; i ++) {
            urlArr[i] = urls.get(i).text();
        }
        for (int i = 0; i <= info.size()-1; i += steps) {
            nameArr[i/4] = info.get(i).text();
        }
        for (int i = 0; i <= size-1; i ++) {
            CpolarEntity cpolarEntity = new CpolarEntity(nameArr[i], urlArr[i]);
            cpolarEntities.add(cpolarEntity);
        }

        return cpolarEntities;
    }

    @Test
    public static ArrayList<String> getUrlListByName(String name){
        ArrayList<String> urls = new ArrayList<>();
        String[] mails = {"372192060@qq.com", "1404067426@qq.com"};
        String password = "XiaShengJie123";
        for (String mail : mails) {
            List<CpolarEntity> CpolarEntityList = getCpolarEntityList(mail,password).stream()
                    .filter(cpolarEntity -> cpolarEntity.getName().equals(name))
                    .collect(Collectors.toList());
            for (CpolarEntity cpolarEntity : CpolarEntityList) {
                urls.add(cpolarEntity.getUrl().replace("tcp://",""));
            }
        }
        return urls;
    }

    public static HashMap<String, String> getSSHUrl(){
        HashMap<String, String> map = new HashMap<>(8);
        //Properties properties = new Properties();

        ArrayList<String> ssh = getUrlListByName("ssh");
        Properties commandConfig = ConfigUtils.getConfig(PropertiesEnum.COMMAND);
        String cmd = commandConfig.getProperty("hostname");
//        int threads = 4;
//        ExecutorService executor = new ThreadPoolExecutor(
//                threads,
//                threads+5,
//                3,
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(3),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (String s : ssh) {
           // executor.submit(() -> {
                String[] split = s.split(":");
                String url = split[0];
                String port = split[1];
                ch.ethz.ssh2.Connection connection = QueryUtils.login(url, Integer.parseInt(port));
                String result = QueryUtils.execute(connection, cmd).replace("\n","");
                String urlP = result+".url";
                String portP = result+".port";
                System.out.println("------------"+result+"-----------");
                System.out.println("------------"+url+"-----------");
                System.out.println("------------"+port+"-----------");
                map.put(urlP,url);
                map.put(portP,port);
//                ConfigUtils.setConfig(properties,PropertiesEnum.SSH,urlP,url);
//                ConfigUtils.setConfig(properties,PropertiesEnum.SSH,portP,port);

                connection.close();
           // });
        }
       // executor.shutdown();
        return map;
    }

    public static String getOrientdbUrl(){
        ArrayList<String> ssh = getUrlListByName("orientdbUrl");
//        ArrayList<String> ssh1 = getUrlListByName("orientdbcluster");
        String url = ssh.get(0);
//        String url1 = ssh1.get(0);
//        String orientdbUrl = "remote:"+url+"/"+";"+url1+"/";
        String orientdbUrl = "remote:"+url+"/";
//        System.out.println(ssh.get(0));
        return orientdbUrl;
    }


    public static void main(String[] args) {
        getSSHUrl();


    }
}
