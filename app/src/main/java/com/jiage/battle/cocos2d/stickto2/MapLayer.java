package com.jiage.battle.cocos2d.stickto2;

import android.view.MotionEvent;

import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.stickto2.model.CGPointModel;
import com.jiage.battle.cocos2d.stickto2.model.PathsModel;

import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：忻佳
 * 日期：2019-10-31
 * 描述：地图
 */
public class MapLayer {
    private CGSize winSize;//视口大小
    private SickTo mSickTo;
    private List<CGPointModel> placeList = new ArrayList<>();
    private List<CGPointModel> obstacleRectList = new ArrayList<>();
    private List<PathsModel[]> pathList = new ArrayList<>();
    private List<CCLabel> ccLabels = new ArrayList<>();
    private Constant.Process mProcess = Constant.Process.Start;

    public MapLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
        winSize = CCDirector.sharedDirector().displaySize();
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
        CGPoint point = new CGPoint();
        point.set(event.getX(), event.getY());
        //坐标转换
        CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
        switch (mProcess) {
            case Start:
                for (CCLabel ccLabel : ccLabels) {
                    CGSize contentSize = ccLabel.getContentSize();
                    CGPoint position = ccLabel.getPosition();
                    if(CollisionUtil.isClickRectangle(cgLocation.x,cgLocation.y,position.x-contentSize.width/2,position.y-contentSize.height/2,contentSize.width,contentSize.height)){
                        mSickTo.initialization(ccLabel.getTag());
                    }
                }
                break;
        }
    }

    /**
     * 初始化解析地图数据
     * @param process
     */
    public void init(Constant.Process process){
        this.mProcess = process;
        switch (process) {
            case Start:
                ccLabels.clear();
                //参数为：文字内容，字体名称，文字大小
                CCLabel startLabel = CCLabel.makeLabel("开始游戏", "DroidSans", 60);
                startLabel.setColor(ccColor3B.ccBLACK);
                startLabel.setTag(Constant.StartTag.Start);
                CCLabel barracksLabel = CCLabel.makeLabel("兵营", "DroidSans", 60);
                barracksLabel.setColor(ccColor3B.ccBLACK);
                barracksLabel.setTag(Constant.StartTag.Barracks);
                CGSize barracksSize = barracksLabel.getContentSize();
                CCLabel outLabel = CCLabel.makeLabel("退出游戏", "DroidSans", 60);
                outLabel.setColor(ccColor3B.ccBLACK);
                outLabel.setTag(Constant.StartTag.Out);
                barracksLabel.setPosition(winSize.width / 2, winSize.height / 2);
                mSickTo.addChild(barracksLabel);
                ccLabels.add(barracksLabel);
                startLabel.setPosition(winSize.width / 2, winSize.height / 2+barracksSize.height*2);
                mSickTo.addChild(startLabel);
                ccLabels.add(startLabel);
                outLabel.setPosition(winSize.width / 2, winSize.height / 2-barracksSize.height*2);
                mSickTo.addChild(outLabel);
                ccLabels.add(outLabel);
                break;
            case Level:

                break;
            case Barracks:

                break;
            case Game:
                //获取tiled地图数据
                CCTMXTiledMap tiledMap = CCTMXTiledMap.tiledMap("map/map.tmx");
                tiledMap.setAnchorPoint(0.5f,0.5f);
                tiledMap.setPosition(tiledMap.getContentSize().width/2,tiledMap.getContentSize().height/2);
                mSickTo.addChild(tiledMap,Config.map.z,Config.map.tag);
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
                break;
        }
    }

    /**
     * 清除所有
     */
    public void initialization(){
        pathList.clear();
        placeList.clear();
        ccLabels.clear();
        obstacleRectList.clear();
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
