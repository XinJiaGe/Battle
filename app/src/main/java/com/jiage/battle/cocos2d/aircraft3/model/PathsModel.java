package com.jiage.battle.cocos2d.aircraft3.model;

import org.cocos2d.types.CGPoint;

/**
 * 作者：忻佳
 * 日期：2019-11-04
 * 描述：
 */
public class PathsModel {
    private CGPoint point;
    private int orientation;

    public CGPoint getPoint() {
        return point;
    }

    public void setPoint(CGPoint point) {
        this.point = point;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
