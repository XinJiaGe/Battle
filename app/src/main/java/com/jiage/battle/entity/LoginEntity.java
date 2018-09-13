package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 日期：2017/11/30/030.
 * 说明：账号密码登录
 */

public class LoginEntity extends BaseEntity {
    private UserEntity Data;

    public UserEntity getData() {
        return Data;
    }

    public void setData(UserEntity data) {
        Data = data;
    }
}
