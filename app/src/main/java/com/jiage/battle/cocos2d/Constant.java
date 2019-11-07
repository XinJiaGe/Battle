package com.jiage.battle.cocos2d;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：
 */
public class Constant {

    /**
     * 方位
     * 8 1 2
     * 7   3
     * 6 5 4
     */
    public static final class  Orientation{
        public static final int TOP = 1;
        public static final int TOPLEFT = 8;
        public static final int TOPRIGHT = 2;
        public static final int LEFT = 7;
        public static final int RIGHT = 3;
        public static final int BOTTOM = 5;
        public static final int BOTTOMLEFT = 6;
        public static final int BOTTOMRIGHT = 4;
    }

    /**
     * 攻击方式
     */
    public enum ATTACKTYPE{
        GONGJIAN,//弓箭
    }

    /**
     * 类型
     */
    public enum MODELTYPE{
        JIANTA,//箭塔
    }

    /**
     * 怪物类型
     */
    public enum ENEMYTYPE{
        ZHIZHU,//蜘蛛
        ZOMBI,//小虫
    }
}
