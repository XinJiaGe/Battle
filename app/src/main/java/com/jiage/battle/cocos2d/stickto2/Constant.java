package com.jiage.battle.cocos2d.stickto2;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：
 */
public class Constant {
    /**
     * 进程
     */
    public enum Process{
        Start,//开始
        Level,//选关卡
        Barracks,//兵营
        Game//游戏中
    }
    /**
     * 开始TAG
     */
    public static final class StartTag{
        public static final int Start = 1;//开始游戏
        public static final int Barracks = 2;//兵营
        public static final int Out = 3;//退出游戏
    }

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
    public enum AttackType{
        GongJian,//弓箭
        DianQiu,//电球
    }

    /**
     * 类型
     */
    public enum ModelType{
        JianTa,//箭塔
        DianTa,//电塔
    }

    /**
     * 怪物类型
     */
    public enum EnemyType{
        ZhiZhu,//蜘蛛
        XiaoChong,//小虫
    }
}
