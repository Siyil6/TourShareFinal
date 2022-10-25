package com.example.tourshare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;


public class Command extends LitePalSupport implements Serializable {
    private long id;
    private String user_id;
    private String user_name;
    private String user_icon;
    private String title;
    private String add_time;
    private String path;
    private int dz;
    private int pl;
    private int zf;
    private String address;

    public Command(String user_id, String user_name, String user_icon, String title, String add_time, String path, String address) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_icon = user_icon;
        this.title = title;
        this.add_time = add_time;
        this.path = path;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDz() {
        return dz;
    }

    public void setDz(int dz) {
        this.dz = dz;
    }

    public int getPl() {
        return pl;
    }

    public void setPl(int pl) {
        this.pl = pl;
    }

    public int getZf() {
        return zf;
    }

    public void setZf(int zf) {
        this.zf = zf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
