package com.jiage.battle.cocos2d.stickto3;

import com.jiage.battle.cocos2d.stickto2.model.CGPointModel;
import com.jiage.battle.cocos2d.stickto2.model.PathsModel;

import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：忻佳
 * 日期：2019-11-12
 * 描述：
 */
public class MapLayer {
    private CCTMXTiledMap tiledMap;
    private List<PathsModel[]> pathList = new ArrayList<>();
    private List<CGPointModel> placeList = new ArrayList<>();

    public MapLayer(StickTo stickTo){
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        //获取tiled地图数据
        tiledMap = CCTMXTiledMap.tiledMap("map/stick.tmx");
        tiledMap.setAnchorPoint(0,00);
        tiledMap.setPosition(0,0);
        stickTo.addChild(tiledMap, 1,1);
        for (CCTMXObjectGroup objectGroup : tiledMap.objectGroups) {
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
        }
    }

    public List<PathsModel[]> getPathList() {
        return pathList;
    }

    public List<CGPointModel> getPlaceList() {
        return placeList;
    }
}
