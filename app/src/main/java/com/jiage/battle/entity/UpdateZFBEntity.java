package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 日期：2018/1/3/003.
 * 说明：
 */

public class UpdateZFBEntity extends BaseEntity {

    /**
     * Data : {"success":true,"remaker":"支付宝账号修改成功"}
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
         * success : true
         * remaker : 支付宝账号修改成功
         */

        private boolean success;
        private String remaker;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getRemaker() {
            return remaker;
        }

        public void setRemaker(String remaker) {
            this.remaker = remaker;
        }
    }
}
