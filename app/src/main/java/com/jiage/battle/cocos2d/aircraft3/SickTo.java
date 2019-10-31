package com.jiage.battle.cocos2d.aircraft3;

import android.view.MotionEvent;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-10-29
 * 描述：
 */
public class SickTo extends CCColorLayer {
    private String TAG = "SickTo";
    private ModelLayer modelLayer;
    private PlayerLayer playerLayer;
    private MapLayer mapLayer;

    public SickTo(ccColor4B color) {
        super(color);
        init();
    }

    private void init(){
        setIsTouchEnabled(true);
        mapLayer = new MapLayer(this);
        modelLayer = new ModelLayer(this);
        playerLayer = new PlayerLayer(this);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        modelLayer.ccTouchesBegan(event);
        return super.ccTouchesBegan(event);
    }

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        modelLayer.ccTouchesMoved(event, mapLayer);
        return super.ccTouchesMoved(event);
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        modelLayer.ccTouchesEnded(event,playerLayer);
        return super.ccTouchesEnded(event);
    }
}
