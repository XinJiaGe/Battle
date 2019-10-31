package com.jiage.battle.cocos2d.aircraft3;

import android.graphics.Rect;
import android.util.Log;

import org.cocos2d.layers.CCTMXLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：忻佳
 * 日期：2019-10-31
 * 描述：
 */
public class MapLayer {
    private List<Rect> placeList;
    private List<Rect> obstacleRectList;
    private int[][] pathList;
    public MapLayer(SickTo sickTo) {
        //获取tiled地图数据
        CCTMXTiledMap cctmxTiledMap = CCTMXTiledMap.tiledMap("map.tmx");
        cctmxTiledMap.setAnchorPoint(0.5f,0.5f);
        cctmxTiledMap.setPosition(cctmxTiledMap.getContentSize().width/2,cctmxTiledMap.getContentSize().height/2);
        sickTo.addChild(cctmxTiledMap);
        init(cctmxTiledMap);
        Log.e("fff","sss");
    }

    private void init(CCTMXTiledMap cctmxTiledMap){
        for (CCTMXObjectGroup objectGroup : cctmxTiledMap.objectGroups) {
            String groupName = objectGroup.groupName;
            ArrayList<HashMap<String, String>> objects = objectGroup.objects;
            if(groupName.equals("paht")){//获取敌人行走路径资源
                for (HashMap<String, String> object : objects) {
                    int x = Integer.parseInt(object.get("x"));
                    int y = Integer.parseInt(object.get("y"));
                }
            }
            if(groupName.equals("place")){//获取可放置位置的rect
                placeList = new ArrayList<>();
                for (HashMap<String, String> object : objects) {
                    int x = Integer.parseInt(object.get("x"));
                    int y = Integer.parseInt(object.get("y"));
                    int width = Integer.parseInt(object.get("width"));
                    int height = Integer.parseInt(object.get("height"));
                    placeList.add(new Rect(x,y,x+width,y+height));
                }
            }
            if(groupName.equals("obstacleRect")){//获取障碍物rect
                obstacleRectList = new ArrayList<>();
                for (HashMap<String, String> object : objects) {
                    int x = Integer.parseInt(object.get("x"));
                    int y = Integer.parseInt(object.get("y"));
                    int width = Integer.parseInt(object.get("width"));
                    int height = Integer.parseInt(object.get("height"));
                    obstacleRectList.add(new Rect(x,y,x+width,y+height));
                }
            }
        }
    }

    public List<Rect> getPlaceList() {
        return placeList;
    }

    public List<Rect> getObstacleRectList() {
        return obstacleRectList;
    }
}
