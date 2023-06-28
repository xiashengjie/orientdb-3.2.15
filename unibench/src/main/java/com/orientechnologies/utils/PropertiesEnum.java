package com.orientechnologies.utils;

/**
 * @author xsj
 */

public enum PropertiesEnum {
    SSH("ssh.properties","unibench/src/main/resources/ssh.properties"),
    ORIENTDB("orientdb.properties","unibench/src/main/resources/orientdb.properties"),
    COMMAND("command.properties","unibench/src/main/resources/command.properties");

    private String propertiesName;
    private String propertiesPath;

    private PropertiesEnum(String propertiesName,String propertiesPath) {
        this.propertiesName = propertiesName;
        this.propertiesPath = propertiesPath;
    }


    public String getPropertiesName() {
        return propertiesName;
    }
    public String getPropertiesPath() {
        return propertiesPath;
    }

    @Override
    public String toString() {
        return propertiesName+"/n"+propertiesPath;
    }
}
