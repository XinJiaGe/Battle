package com.jiage.battle.entity;

/**
 * 作者：李忻佳
 * 日期：2018/9/14/014.
 * 说明：
 */

public class BlueDeviceEntity {
    private String name;
    private String address;
    private int state;

    public BlueDeviceEntity(String name,String address,int state){
        this.name = name;
        this.address = address;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
