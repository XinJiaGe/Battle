package com.jiage.battle.cocos2d.aircraft3;

import android.view.MotionEvent;

import com.jiage.battle.cocos2d.aircraft3.model.CGPointModel;
import com.jiage.battle.cocos2d.aircraft3.model.PathsModel;

import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：忻佳
 * 日期：2019-10-31
 * 描述：地图
 */
public class MapLayer {
    private List<CGPointModel> placeList = new ArrayList<>();
    private List<CGPointModel> obstacleRectList = new ArrayList<>();
    private List<PathsModel[]> pathList = new ArrayList<>();
    public MapLayer(SickTo sickTo) {
        //获取tiled地图数据
        CCTMXTiledMap cctmxTiledMap = CCTMXTiledMap.tiledMap("map/map.tmx");
        cctmxTiledMap.setAnchorPoint(0.5f,0.5f);
        cctmxTiledMap.setPosition(cctmxTiledMap.getContentSize().width/2,cctmxTiledMap.getContentSize().height/2);
        sickTo.addChild(cctmxTiledMap,Config.map.z,Config.map.tag);
        init(cctmxTiledMap);
    }

    /**
     * 点击时
     * @param event
     */
    public void ccTouchesBegan(MotionEvent event){

    }

    /**
     * 抬起时
     * @param event
     */
    public void ccTouchesEnded(MotionEvent event){

    }

    /**
     * 初始化解析地图数据
     * @param cctmxTiledMap
     */
    private void init(CCTMXTiledMap cctmxTiledMap){
        for (CCTMXObjectGroup objectGroup : cctmxTiledMap.objectGroups) {
            String groupName = objectGroup.groupName;
            ArrayList<HashMap<String, String>> objects = objectGroup.objects;
            for (int i = 0; i < 10; i++) {
                if(groupName.equals("path"+(i+1))){//获取敌人行走路径资源1
                    PathsModel[] path = new PathsModel[objects.size()];
                    for (int j = 0; j < objects.size(); j++) {
                        PathsModel pathsModel = new PathsModel();
                        CGPoint point = new CGPoint();
                        point.set(Integer.parseInt(objects.get(j).get("x")),Integer.parseInt(objects.get(j).get("y")));
                        pathsModel.setPoint(point);
                        pathsModel.setOrientation(Integer.parseInt(objects.get(j).get("orientation")));
                        path[j] = pathsModel;
                    }
                    pathList.add(path);
                }
            }
            if(groupName.equals("place")){//获取可放置位置的rect
                for (HashMap<String, String> object : objects) {
                    int x = Integer.parseInt(object.get("x"));
                    int y = Integer.parseInt(object.get("y"));
                    int width = Integer.parseInt(object.get("width"));
                    int height = Integer.parseInt(object.get("height"));
                    placeList.add(new CGPointModel(x,y,width,height));
                }
            }
            if(groupName.equals("obstacleRect")){//获取障碍物rect
                for (HashMap<String, String> object : objects) {
                    int x = Integer.parseInt(object.get("x"));
                    int y = Integer.parseInt(object.get("y"));
                    int width = Integer.parseInt(object.get("width"));
                    int height = Integer.parseInt(object.get("height"));
                    obstacleRectList.add(new CGPointModel(x,y,width,height));
                }
            }
        }
    }

    public List<PathsModel[]> getPathList() {
        return pathList;
    }

    public List<CGPointModel> getPlaceList() {
        return placeList;
    }

    public List<CGPointModel> getObstacleRectList() {
        return obstacleRectList;
    }
}
