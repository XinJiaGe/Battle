package com.jiage.battle.entity;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/8/23
 * 说明：
 */

public class CheckpointEntity {
    private List<CheckpointItemEntity> itemEntityList;

    public List<CheckpointItemEntity> getItemEntityList() {
        return itemEntityList;
    }

    public void setItemEntityList(List<CheckpointItemEntity> itemEntityList) {
        this.itemEntityList = itemEntityList;
    }
}
