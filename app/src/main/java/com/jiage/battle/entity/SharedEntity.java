package com.jiage.battle.entity;

/**
 * Created by Administrator on 2017/12/3 0003.
 * 说明：分享实体
 */

public class SharedEntity extends BaseEntity{

    /**
     * "code":"0000","sub_code":"0000","msg":"","sub_msg":"","Status":200,
     * "Data":{"href":"http://192.188.88.177:8086/pages/index.html?code=1000028","title":"小白兔注册","description":"注册描述",
     *        "imgUrl":"http://www.love-edu.cn/UploadFiles/201610/2016102336182409.png"}}
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
         Data":{"href":"http://192.188.88.177:8086/pages/index.html?code=1000028","title":"小白兔注册","description":"注册描述",
         *        "imgUrl":"http://www.love-edu.cn/UploadFiles/201610/2016102336182409.png"}
         */

        private String href;
        private String title;
        private String description;
        private String imgUrl;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
