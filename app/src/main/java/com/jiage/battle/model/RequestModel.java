package com.jiage.battle.model;

import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.entity.UserEntity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：李忻佳
 * 日期：2017/11/28/028.
 * 说明：
 */

public class RequestModel<E> {

    private Map<String, Object> mData = new HashMap<String, Object>();
    private Map<String, File> mUploadFile = new HashMap<String, File>();

    private String api = "";
    private String downloadUrl = "";
    private String uploadUrl = "";
    private String Act = "";
    private String Cat = "";

    public RequestModel(Map<String, Object> data) {
        super();
        this.mData = data;
        init();
    }

    public RequestModel() {
        super();
        init();
    }

    // 初始
    private void init() {
        putToken();
        putTime();
    }

    /**
     * 加入User
     */
    private void putToken() {
        UserEntity user = JsonDbModelDao.getInstance().query(UserEntity.class, true);
        if (user != null) {
            put("userid", user.getUserid());
        }
    }

    /**
     * 加入时间戳
     */
    private void putTime() {
//        put("time", String.valueOf(System.currentTimeMillis()));
    }

    public void remove(String rem) {
        mData.remove(rem);
    }

    public void removeId() {
        mData.remove("userid");
    }

    public void putPage(PageModel page) {
        put("pageIndex", page.getPi());
    }

    public void put(String key, Object value) {
        mData.put(key, value);
    }

    public void putDownload(String url) {
        this.downloadUrl = url;
    }
    public void putUpload(String url) {
        this.uploadUrl = url;
    }
    public void putUploadFile(String key,File file){
        mUploadFile.put(key,file);
    }

    public Map<String, Object> getData() {
        return mData;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public Map<String, File> getmUploadFile() {
        return mUploadFile;
    }
}
