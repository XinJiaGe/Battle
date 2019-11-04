package com.jiage.battle.cocos2d.aircraft3.model;

import org.cocos2d.types.CGPoint;

/**
 * 作者：忻佳
 * 日期：2019-11-04
 * 描述：
 */
public class CGPointModel {
    private CGPoint cgPoint;
    private int width;
    private int height;

    public CGPointModel(int x, int y, int width, int height){
        this.cgPoint = CGPoint.ccp(x, y);
        this.width = width;
        this.height = height;
    }

    public CGPoint getCgPoint() {
        return cgPoint;
    }

    public void setCgPoint(CGPoint cgPoint) {
        this.cgPoint = cgPoint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
