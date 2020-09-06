package com.znjt.xufeii.bean;

/**
 * Created by Administrator on 2019/4/26.
 */

public class DataSimple {
    private String name;
    private int image;

    public DataSimple(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public DataSimple() {
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
