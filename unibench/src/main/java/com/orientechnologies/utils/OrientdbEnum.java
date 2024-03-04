package com.orientechnologies.utils;

/**
 * @author xsj
 */

public enum OrientdbEnum {
    //OSYSTEM("OSystem","cp -rf /data/minio/orientdb/copy/OSystem/ /data/minio/orientdb/databases/"),
    SF1("test","cp -rf /data/minio/orientdb/copy/test/ /data/minio/orientdb/databases/"),
    SF10("testSF10","cp -rf /data/minio/orientdb/copy/testSF10/ /data/minio/orientdb/databases/"),
    SF30("testSF30","cp -rf /data/minio/orientdb/copy/testSF30/ /data/minio/orientdb/databases/");
//    SF100("testSF100","cp -rf /data/minio/orientdb/copy/testSF100/ /data/minio/orientdb/databases/");

    private String name;
    private String refreshCmd;

    private OrientdbEnum(String name,String refreshCmd) {
        this.name = name;
        this.refreshCmd = refreshCmd;
    }
    public String getName() {
        return name;
    }
    public String getRefreshCmd() {
        return refreshCmd;
    }


    @Override
    public String toString() {
        return name+refreshCmd;
    }
}
