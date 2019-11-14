package com.jiage.battle.cocos2d.stickto3;

import com.jiage.battle.cocos2d.stickto2.model.PathsModel;
import com.jiage.battle.util.OtherUtil;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-13
 * 描述：
 */
public class EnemyLayer {
    private StickTo stickTo;
    private Vector<Enemy> vcEnemys = new Vector<>();
    private List<PathsModel[]> pathList;

    public EnemyLayer(StickTo stickTo, List<PathsModel[]> pathList){
        this.stickTo = stickTo;
        this.pathList = pathList;
    }

    public void add(){
        if(pathList!=null){
            PathsModel[] pathsModels = pathList.get(OtherUtil.getRandom(0, 3));
            vcEnemys.add(new Enemy(stickTo,Constant.EnemyType.LaoShu,pathsModels));

        }
    }

    public class Enemy{
        private Constant.EnemyType enemyType;
        private PathsModel[] pathsModels;
        private CCSprite ccSprite;

        public Enemy(StickTo stickTo, Constant.EnemyType enemyType, PathsModel[] pathsModels) {
            this.enemyType = enemyType;
            this.pathsModels = pathsModels;
            PathsModel pathsModel = pathsModels[2];

            CCSprite ccSprite = CCSprite.sprite("enemy/mouse_walk_01.png");
            ccSprite.setAnchorPoint(0.5f,0.5f);
            ccSprite.setPosition(pathsModel.getPoint());
            stickTo.addChild(ccSprite, 5,5);
            // 序列帧的播放
            ArrayList<CCSpriteFrame> frames = new ArrayList<>();
            String format = "enemy/mouse_walk_%02d.png";// 02d 占位符 可以表示两位的整数 如果不足两位前面用0补足
            for (int i = 1; i <= 8; i++) {
                String name = String.format(format, i);
                CCSpriteFrame displayedFrame = CCSprite.sprite(name).displayedFrame();
                frames.add(displayedFrame);
            }
            // 配置序列帧的信息 参数1 动作的名字(给程序员看的) 参数2 每一帧播放的时间 单位秒 参数3 所有用到的帧
            CCAnimation anim = CCAnimation.animation("老鼠走路", 0.1f, frames);
            CCAnimate animate = CCAnimate.action(anim);
            // 序列帧动作默认是永不停止的循环
            CCRepeatForever forever = CCRepeatForever.action(animate);
            ccSprite.runAction(forever);
        }
    }
}
