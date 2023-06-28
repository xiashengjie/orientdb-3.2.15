package com.orientechnologies.utils;

import java.io.*;
import java.util.Properties;

/**
 * @author xsj
 */
public class ConfigUtils {

    public static Properties getConfig(PropertiesEnum propertiesEnum){
        Properties properties = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream(propertiesEnum.getPropertiesName());
        try {
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void setConfig(Properties properties,PropertiesEnum propertiesEnum,String key,String value){
        InputStream input = ClassLoader.getSystemResourceAsStream(propertiesEnum.getPropertiesName());
        try {
            properties.load(input);
            input.close();
            properties.setProperty(key,value);
            FileOutputStream fileOutputStream = new FileOutputStream(propertiesEnum.getPropertiesPath());
            properties.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
