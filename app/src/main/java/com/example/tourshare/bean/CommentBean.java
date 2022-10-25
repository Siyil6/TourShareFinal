package com.example.tourshare.bean;

import org.litepal.crud.LitePalSupport;

public class CommentBean extends LitePalSupport {
    private long id;
    private String icon;
    private String name;
    private String title;
    private String add_time;
    private String u_id;
    private String c_id;

    public CommentBean(String u_id,String icon, String name, String title, String add_time,String c_id) {
        this.u_id = u_id;
        this.icon = icon;
        this.name = name;
        this.title = title;
        this.add_time = add_time;
        this.c_id = c_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
