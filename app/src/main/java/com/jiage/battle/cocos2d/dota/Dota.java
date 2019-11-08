package com.jiage.battle.cocos2d.dota;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-11-08
 * 描述：
 */
public class Dota extends CCColorLayer {
    private MapLayer mapLayer;

    public Dota(ccColor4B color) {
        super(color);
        setIsTouchEnabled(true);
        init();
    }

    private void init() {
        mapLayer = new MapLayer(this);
    }
}
