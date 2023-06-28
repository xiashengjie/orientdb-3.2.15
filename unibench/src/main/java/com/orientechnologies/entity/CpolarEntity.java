package com.orientechnologies.entity;

import lombok.Data;

/**
 * @author xsj
 */
@Data
public class CpolarEntity {
    private String name;
    private String url;

    public CpolarEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
