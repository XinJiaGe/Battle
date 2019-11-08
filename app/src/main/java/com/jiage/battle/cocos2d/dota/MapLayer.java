package com.jiage.battle.cocos2d.dota;

import org.cocos2d.layers.CCTMXTiledMap;

/**
 * 作者：忻佳
 * 日期：2019-11-08
 * 描述：
 */
public class MapLayer {
    private Dota mDota;

    public MapLayer(Dota dota){
        this.mDota = dota;
        init();
    }

    private void init() {
        //获取tiled地图数据
        CCTMXTiledMap tiledMap = CCTMXTiledMap.tiledMap("map/dota.tmx");
        tiledMap.setAnchorPoint(0,0);
        tiledMap.setPosition(0,0);
        mDota.addChild(tiledMap);
    }
}
