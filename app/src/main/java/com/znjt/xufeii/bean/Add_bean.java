package com.znjt.xufeii.bean;

/**
 * Created by Administrator on 2019/4/28 0028.
 */

public class Add_bean {
    private  String id;
    private String message;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public Add_bean(String id, String message, String select) {

        this.id = id;
        this.message = message;
        this.select = select;
    }

    private String select;

    public Add_bean(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
