package com.jiage.battle.cocos2d;

import com.jiage.battle.cocos2d.aircraft3.SickTo;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：
 */
public class Cocos2dUtil {
    /**
     * 设置范围内黄色
     * @param sickTo
     * @param pnt     坐标 CGPoint.ccp（x,y）
     * @param x       锚点x
     * @param y       锚点y
     * @param width   宽
     * @param height  高
     * @return
     */
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, float x, float y, int width, int height){
        return setColor(sickTo, pnt,255,255,0,150, x, y, width, height);
    }
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, int width, int height){
        return setColor(sickTo, pnt,255,255,0,150, 0, 0, width, height);
    }
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, int r, int g, int b, float x, float y, int width, int height){
        return setColor(sickTo, pnt,r,g,b,150, x, y, width, height);
    }
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, int r, int g, int b, int a, float x, float y, int width, int height){
        CCColorLayer layer = CCColorLayer.node(ccColor4B.ccc4(r, g, b, a),width,height);
        layer.setAnchorPoint(x, y);
        layer.setPosition(pnt);
        sickTo.addChild(layer);
        return layer;
    }
}
