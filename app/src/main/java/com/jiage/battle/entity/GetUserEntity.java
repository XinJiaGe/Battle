package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 时间：2018/4/16/016.
 * 说明：
 */

public class GetUserEntity extends BaseEntity {
    private UserEntity Data;

    public UserEntity getData() {
        return Data;
    }

    public void setData(UserEntity Data) {
        this.Data = Data;
    }
}
