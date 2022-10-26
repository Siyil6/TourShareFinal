package com.example.tourshare.bean;

import org.litepal.crud.LitePalSupport;


public class User extends LitePalSupport {
    private long id;
    private String name;
    private String email;
    private String pwd;
    private String icon;
    private String des;

    public User(String name, String email, String pwd ) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;

    }
    public User(String name, String email, String pwd,String icon) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.icon = icon;

    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
