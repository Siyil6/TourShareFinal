package com.example.tourshare.bean;

import org.litepal.crud.LitePalSupport;


public class LikeBean extends LitePalSupport {
    private long id;
    private String u_id;
    private String c_id;

    public LikeBean(String u_id, String c_id) {
        this.u_id = u_id;
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

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
}
