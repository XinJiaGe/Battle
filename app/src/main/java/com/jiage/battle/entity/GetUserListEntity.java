package com.jiage.battle.entity;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2018/4/16/016.
 * 说明：
 */

public class GetUserListEntity extends BaseEntity {
    private List<UserEntity> Data;

    public List<UserEntity> getData() {
        return Data;
    }

    public void setData(List<UserEntity> data) {
        Data = data;
    }
}
