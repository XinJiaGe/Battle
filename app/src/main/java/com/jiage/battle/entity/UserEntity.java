package com.jiage.battle.entity;

import com.jiage.battle.constant.ApkConstant;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 作者：李忻佳
 * 日期：2017/11/30/030.
 * 说明：用户entity
 */
@Table(name = "user")
public class UserEntity implements Serializable{
    @Column(name = "_id", isId = true)
    private int _id;
    @Column(name = "region")
    private String region;//地区
    @Column(name = "city")
    private String city;//地区
    @Column(name = "sex")
    private boolean sex;
    @Column(name = "headPortraitimg")
    private String headPortraitimg;//头像
    @Column(name = "agent_no")
    private String agent_no;//小白兔号
    @Column(name = "phone")
    private String phone;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "userid")
    private int userid;
    @Column(name = "token")
    private String token;
    @Column(name = "alipayName")//真实姓名
    private String alipayName;
    @Column(name = "countries")
    private String countries;
    @Column(name = "alipayaccount")//
    private String alipayaccount;
    @Column(name = "status")//
    private int status;

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getAlipayaccount() {
        return alipayaccount;
    }

    public void setAlipayaccount(String alipayaccount) {
        this.alipayaccount = alipayaccount;
    }
    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getHeadPortraitimg() {
        return headPortraitimg;
    }

    public void setHeadPortraitimg(String headPortraitimg) {
        this.headPortraitimg = headPortraitimg;
    }

    public String getAgent_no() {
        return agent_no;
    }

    public void setAgent_no(String agent_no) {
        this.agent_no = agent_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
