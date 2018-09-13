package com.jiage.battle.entity;


/**
 * Created by Administrator on 2017/12/1 0001.
 * agentid:41
 * amt:100.00
 * jsamt:0.00
 * deposit:0.00
 * isLock":0,
 * frozenamt:0.00
 * minsettamt":0.00
 * lastSettDate":"2017-12-01T00:00:00
 * id:5
 */

public class BalanceEntity extends BaseEntity {

    /**
     * Data : {"userid":41,"amt":100,"jsamt":0,"deposit":0,"isLock":0,"frozenamt":0,"minsettamt":0,"lastSettDate":"2017-12-01T00:00:00","id":5}
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
         * userid : 21
         * amt : 100
         * */


        private int userid;
        private Double amt;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public Double getAmt() {
            return amt;
        }

        public void setAmt(Double amt) {
            this.amt = amt;
        }
    }
}
