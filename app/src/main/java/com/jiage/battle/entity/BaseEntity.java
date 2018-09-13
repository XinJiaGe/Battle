package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 日期：2017/11/28/028.
 * 说明：BaseEntity
 */

public class BaseEntity {
    private String code;
    private String sub_code;
    private String msg;
    private String sub_msg;
    private int Status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSub_msg() {
        return sub_msg;
    }

    public void setSub_msg(String sub_msg) {
        this.sub_msg = sub_msg;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
