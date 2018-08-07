package com.example.zxg.myprogram.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 通用实体类，主要用于网络请求
 * Created by zxg on 2016/9/30.
 * QQ:1092885570
 */
public class CommonModel implements Serializable {
    /**
     * 状态码
     */
    protected int code = -1;
    /**
     * 状态文本描述
     */
    protected String message;

    /**
     * 消息体
     */
    protected JSONObject body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "code = " + code + " ,message = " + message + " ,body = " + body;
    }
}
