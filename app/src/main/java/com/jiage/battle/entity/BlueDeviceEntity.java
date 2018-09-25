package com.jiage.battle.entity;

import android.graphics.Rect;

import com.jiage.battle.surface.snake.RectangleKeyboard;

/**
 * 作者：李忻佳
 * 日期：2018/9/14/014.
 * 说明：
 */

public class BlueDeviceEntity {
    private String name;
    private String address;
    private int state;
    private int type;//1： 加入 2: 退出 3:消息 4:位置 5:方向 6:移动位置我 7:移动位置他
    private int x;
    private int y;
    private Rect rect;
    private RectangleKeyboard.Direction DIRECTIONMY;

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public RectangleKeyboard.Direction getDIRECTIONMY() {
        return DIRECTIONMY;
    }

    public void setDIRECTIONMY(RectangleKeyboard.Direction DIRECTIONMY) {
        this.DIRECTIONMY = DIRECTIONMY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
