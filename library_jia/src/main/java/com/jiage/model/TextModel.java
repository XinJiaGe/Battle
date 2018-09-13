package com.jiage.model;

import java.util.List;

/**
 * 创建人：李忻佳
 * 创建时间：2016/11/2
 * 类说明：按Atl + s   根据json字符串生成model
 */

public class TextModel {

    /**
     * about_info : 0
     * act : index
     * api_qq : 0
     * api_sina : 0
     * city_id : 15
     * city_name : 福州
     * citylist : [{"id":"18","name":"北京","py":"beijing"},{"id":"15","name":"福州","py":"fuzhou"},{"id":"19","name":"上海","py":"shanghai"},{"id":"20","name":"厦门","py":"xiamen"}]
     * ctl : init
     * group_id : 1
     * hot_city : {}
     * index_logo : http://192.168.0.81/fangwei/public/attachment/201202/04/16/4f2ce8336d784.png
     * info :
     * is_store_pay : 1
     * kf_email : qq@fanwe.com
     * kf_phone : 400-000-0000
     * max_bespoke_people_num : 14
     * menu_user_withdraw : 1
     * only_one_delivery : 0
     * page_size : 10
     * program_title : 方维O2O
     * qq_app_key :
     * qq_app_secret :
     * ref_uid : 1
     * region_version : 1
     * relation : [{"id":0,"name":"自己"},{"id":1,"name":"配偶"},{"id":2,"name":"父母"},{"id":3,"name":"儿女"},{"id":4,"name":"孙子/女"},{"id":5,"name":"其他"}]
     * return : 1
     * sess_id :
     * sina_app_key :
     * sina_app_secret :
     * sina_bind_url :
     * start_page : 1
     * start_page_new : 1
     * status : 1
     * user : 1
     * version : 3.05
     */

    private int about_info;
    private String act;
    private int api_qq;
    private int api_sina;
    private String city_id;
    private String city_name;
    private String ctl;
    private int group_id;
    private String index_logo;
    private String info;
    private int is_store_pay;
    private String kf_email;
    private String kf_phone;
    private String max_bespoke_people_num;
    private int menu_user_withdraw;
    private int only_one_delivery;
    private int page_size;
    private String program_title;
    private String qq_app_key;
    private String qq_app_secret;
    private int ref_uid;
    private int region_version;
    private int returnX;
    private String sess_id;
    private String sina_app_key;
    private String sina_app_secret;
    private String sina_bind_url;
    private int start_page;
    private int start_page_new;
    private int status;
    private int user;
    private double version;
    /**
     * id : 18
     * name : 北京
     * py : beijing
     */

    private List<CitylistBean> citylist;
    /**
     * id : 0
     * name : 自己
     */

    private List<RelationBean> relation;

    public int getAbout_info() {
        return about_info;
    }

    public void setAbout_info(int about_info) {
        this.about_info = about_info;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public int getApi_qq() {
        return api_qq;
    }

    public void setApi_qq(int api_qq) {
        this.api_qq = api_qq;
    }

    public int getApi_sina() {
        return api_sina;
    }

    public void setApi_sina(int api_sina) {
        this.api_sina = api_sina;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCtl() {
        return ctl;
    }

    public void setCtl(String ctl) {
        this.ctl = ctl;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getIndex_logo() {
        return index_logo;
    }

    public void setIndex_logo(String index_logo) {
        this.index_logo = index_logo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIs_store_pay() {
        return is_store_pay;
    }

    public void setIs_store_pay(int is_store_pay) {
        this.is_store_pay = is_store_pay;
    }

    public String getKf_email() {
        return kf_email;
    }

    public void setKf_email(String kf_email) {
        this.kf_email = kf_email;
    }

    public String getKf_phone() {
        return kf_phone;
    }

    public void setKf_phone(String kf_phone) {
        this.kf_phone = kf_phone;
    }

    public String getMax_bespoke_people_num() {
        return max_bespoke_people_num;
    }

    public void setMax_bespoke_people_num(String max_bespoke_people_num) {
        this.max_bespoke_people_num = max_bespoke_people_num;
    }

    public int getMenu_user_withdraw() {
        return menu_user_withdraw;
    }

    public void setMenu_user_withdraw(int menu_user_withdraw) {
        this.menu_user_withdraw = menu_user_withdraw;
    }

    public int getOnly_one_delivery() {
        return only_one_delivery;
    }

    public void setOnly_one_delivery(int only_one_delivery) {
        this.only_one_delivery = only_one_delivery;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getProgram_title() {
        return program_title;
    }

    public void setProgram_title(String program_title) {
        this.program_title = program_title;
    }

    public String getQq_app_key() {
        return qq_app_key;
    }

    public void setQq_app_key(String qq_app_key) {
        this.qq_app_key = qq_app_key;
    }

    public String getQq_app_secret() {
        return qq_app_secret;
    }

    public void setQq_app_secret(String qq_app_secret) {
        this.qq_app_secret = qq_app_secret;
    }

    public int getRef_uid() {
        return ref_uid;
    }

    public void setRef_uid(int ref_uid) {
        this.ref_uid = ref_uid;
    }

    public int getRegion_version() {
        return region_version;
    }

    public void setRegion_version(int region_version) {
        this.region_version = region_version;
    }

    public int getReturnX() {
        return returnX;
    }

    public void setReturnX(int returnX) {
        this.returnX = returnX;
    }

    public String getSess_id() {
        return sess_id;
    }

    public void setSess_id(String sess_id) {
        this.sess_id = sess_id;
    }

    public String getSina_app_key() {
        return sina_app_key;
    }

    public void setSina_app_key(String sina_app_key) {
        this.sina_app_key = sina_app_key;
    }

    public String getSina_app_secret() {
        return sina_app_secret;
    }

    public void setSina_app_secret(String sina_app_secret) {
        this.sina_app_secret = sina_app_secret;
    }

    public String getSina_bind_url() {
        return sina_bind_url;
    }

    public void setSina_bind_url(String sina_bind_url) {
        this.sina_bind_url = sina_bind_url;
    }

    public int getStart_page() {
        return start_page;
    }

    public void setStart_page(int start_page) {
        this.start_page = start_page;
    }

    public int getStart_page_new() {
        return start_page_new;
    }

    public void setStart_page_new(int start_page_new) {
        this.start_page_new = start_page_new;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<CitylistBean> getCitylist() {
        return citylist;
    }

    public void setCitylist(List<CitylistBean> citylist) {
        this.citylist = citylist;
    }

    public List<RelationBean> getRelation() {
        return relation;
    }

    public void setRelation(List<RelationBean> relation) {
        this.relation = relation;
    }

    public static class CitylistBean {
        private String id;
        private String name;
        private String py;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPy() {
            return py;
        }

        public void setPy(String py) {
            this.py = py;
        }
    }

    public static class RelationBean {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
