package com.jiage.battle.entity;

/**
 * Created by Administrator on 2018/1/22/022.
 *
 */

public class GetalipayaccountEntity extends BaseEntity {

    /**
     * Data : {"phone":"15071560829"}
     */

    private DataBean Data;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * phone : 15071560829
         */

        private String alipayaccount;

        public String getAlipayaccount() {
            return alipayaccount;
        }

        public void setAlipayaccount(String alipayaccount) {
            this.alipayaccount = alipayaccount;
        }
    }
}
