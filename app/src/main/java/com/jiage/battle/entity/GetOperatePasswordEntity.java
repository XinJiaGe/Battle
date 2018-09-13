package com.jiage.battle.entity;

/**
 * Created by Administrator on 2018/1/20/020.
 *
 */

public class GetOperatePasswordEntity extends BaseEntity {

    private GetOperatePasswordEntity.DataBean Data;

    public GetOperatePasswordEntity.DataBean getData() {
        return Data;
    }

    public void setData(GetOperatePasswordEntity.DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
