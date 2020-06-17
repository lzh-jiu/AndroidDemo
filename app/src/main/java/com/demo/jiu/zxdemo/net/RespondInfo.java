package com.demo.jiu.zxdemo.net;

public class RespondInfo {
    private String message;
    private int cord;
    private String obj;

    @Override
    public String toString() {
        return "RespondInfo{" +
                "message='" + message + '\'' +
                ", cord=" + cord +
                ", obj='" + obj + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCord() {
        return cord;
    }

    public void setCord(int cord) {
        this.cord = cord;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
