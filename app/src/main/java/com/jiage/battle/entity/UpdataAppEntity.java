package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 日期：2017/12/8/008.
 * 说明：版本更新
 */

public class UpdataAppEntity extends BaseEntity {

    /**
     * Data : {"title":"测试更新版本","edition_type":1,"edition_content":"1.0.0","edition_No":"测试更新版本","createTime":1512713934,"url":"http://1shouyin.oss-cn-qingdao.aliyuncs.com/app-zs-release.apk","ForcedToupdate":0,"id":1}
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
         * title : 测试更新版本
         * edition_type : 1
         * edition_content : 1.0.0
         * edition_No : 测试更新版本
         * createTime : 1512713934
         * url : http://1shouyin.oss-cn-qingdao.aliyuncs.com/app-zs-release.apk
         * ForcedToupdate : 0
         * id : 1
         */

        private String title;
        private int edition_type;
        private String edition_content;
        private String edition_No;
        private int createTime;
        private String url;
        private int ForcedToupdate;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEdition_type() {
            return edition_type;
        }

        public void setEdition_type(int edition_type) {
            this.edition_type = edition_type;
        }

        public String getEdition_content() {
            return edition_content;
        }

        public void setEdition_content(String edition_content) {
            this.edition_content = edition_content;
        }

        public String getEdition_No() {
            return edition_No;
        }

        public void setEdition_No(String edition_No) {
            this.edition_No = edition_No;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getForcedToupdate() {
            return ForcedToupdate;
        }

        public void setForcedToupdate(int ForcedToupdate) {
            this.ForcedToupdate = ForcedToupdate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
