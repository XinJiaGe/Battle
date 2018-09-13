package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 日期：2017/12/6/006.
 * 说明：
 */

public class GetSecretKeyEntity extends BaseEntity {

    /**
     * Data : {"privateKeyServer":"","publicKeyClient":""}
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
         * privateKeyServer :
         * publicKeyClient :
         */

        private String privateKeyServer;
        private String publicKeyClient;

        public String getPrivateKeyServer() {
            return privateKeyServer;
        }

        public void setPrivateKeyServer(String privateKeyServer) {
            this.privateKeyServer = privateKeyServer;
        }

        public String getPublicKeyClient() {
            return publicKeyClient;
        }

        public void setPublicKeyClient(String publicKeyClient) {
            this.publicKeyClient = publicKeyClient;
        }
    }
}
