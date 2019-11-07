package com.jiage.battle.cocos2d;

import com.jiage.battle.cocos2d.aircraft3.Config;
import com.jiage.battle.cocos2d.aircraft3.SickTo;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：
 */
public class Cocos2dUtil {
    /**
     * 设置范围内颜色
     * @param sickTo
     * @param pnt     坐标 CGPoint.ccp（x,y）
     * @param r
     * @param g
     * @param b
     * @param a
     * @param x       锚点x
     * @param y       锚点y
     * @param width   宽
     * @param height  高
     * @return
     */
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, int r, int g, int b, int a, float x, float y, float width, float height,int z,int tag){
        CCColorLayer layer = CCColorLayer.node(ccColor4B.ccc4(r, g, b, a),width,height);
        layer.setAnchorPoint(x, y);
        layer.setPosition(pnt);
        sickTo.addChild(layer,z,tag);
        return layer;
    }
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, float x, float y, float width, float height,int z,int tag){
        return setColor(sickTo, pnt,255,255,0,150, x, y, width, height,z,tag);
    }
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, int width, int height,int z,int tag){
        return setColor(sickTo, pnt,255,255,0,150, 0, 0, width, height,z,tag);
    }
    public static CCColorLayer setColor(SickTo sickTo, CGPoint pnt, int r, int g, int b, float x, float y, float width, float height,int z,int tag){
        return setColor(sickTo, pnt,r,g,b,150, x, y, width, height,z,tag);
    }

    /**
     * 设置血量
     * @param sickTo
     * @param ccSprite
     * @param blood
     * @param maxblood
     * @return
     */
    public static CCColorLayer setLayerBool(SickTo sickTo, CCSprite ccSprite, int blood, int maxblood){
        CGPoint position = ccSprite.getPosition();
        float width = ccSprite.getContentSize().width;
        position.set(position.x,position.y+20);
        return setColor(sickTo,position,255,0,0,0,0, blood*width/maxblood,5, ccSprite.getZOrder(),Config.blood.tag);
    }
}
