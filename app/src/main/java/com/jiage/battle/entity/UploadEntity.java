package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 时间：2018/5/9/009.
 * 说明：
 */

public class UploadEntity extends BaseEntity{

    /**
     * Data : {"path":"http://xiaobaitu.nos-eastchina1.126.net/2018/201805/20180509/152584636255.jpg"}
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
         * path : http://xiaobaitu.nos-eastchina1.126.net/2018/201805/20180509/152584636255.jpg
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
