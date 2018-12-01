package com.example.quanthinh2205.fragmentcommunicator;

import java.io.Serializable;

public class Info implements Serializable {
    private String info;
    private int image;
    private String name;
    private String lop;
    private int dtb;

    public Info(String info, int image, String name, String lop, int dtb) {
        this.info = info;
        this.image = image;
        this.name = name;
        this.lop = lop;
        this.dtb = dtb;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getDtb() {
        return dtb;
    }

    public void setDtb(int dtb) {
        this.dtb = dtb;
    }
}
