package com.wpq.sample.custom_recyclerview.bean;

/**
 * @author wpq
 * @version 1.0
 */
public class MultiTypeBean {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;

    private int type;
    private String content;

    public MultiTypeBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
