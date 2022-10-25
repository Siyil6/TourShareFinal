package com.example.tourshare.base;

import java.io.Serializable;


public class BaseResponse implements Serializable {


    public final static String DEFAULT_ERROR_CODE = "-1";
    private String Code = DEFAULT_ERROR_CODE;
    private String Message;
    private Serializable Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public Serializable getData() {
        return Data;
    }

    public void setData(Serializable data) {
        this.Data = data;
    }
}
