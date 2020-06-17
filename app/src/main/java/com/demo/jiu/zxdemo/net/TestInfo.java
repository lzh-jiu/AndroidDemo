package com.demo.jiu.zxdemo.net;

import java.util.List;

public class TestInfo {
    private List<String> Gisno;
    private int State;

    public List<String> getGisno() {
        return Gisno;
    }

    public void setGisno(List<String> gisno) {
        Gisno = gisno;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    @Override
    public String toString() {
        return "TestInfo{" +
                "Gisno=" + Gisno +
                ", State=" + State +
                '}';
    }
}
