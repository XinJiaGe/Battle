package com.jiage.battle.cocos2d.aircraft3;

import com.jiage.battle.cocos2d.Constant;

/**
 * 作者：忻佳
 * 日期：2019-11-07
 * 描述：
 */
public class Config {
    public static class map{
        public final static int tag = 1;
        public final static int z = 1;
    }
    public static class bullet{
        public final static int tag = 10;
        public final static int z = 40;
        public final static float bulletLogicInterval = 0.1f;
        //获取移动速度
        public static int getSpeed(Constant.ATTACKTYPE attacktype){
            switch (attacktype) {
                case GONGJIAN:
                    return 1200;
            }
            return 0;
        }
        //获取攻击力
        public static int getAggressivity(Constant.ATTACKTYPE attacktype){
            switch (attacktype) {
                case GONGJIAN:
                    return 1;
            }
            return 0;
        }
    }
    public static class enemy{
        public final static int tag = 9;
        public final static float addEnemyInterval = 0.4f;
        public static int getZ(Constant.ENEMYTYPE enemytype){
            switch (enemytype) {
                case ZHIZHU:
                    return 10;
                case ZOMBI:
                    return 11;
            }
            return 0;
        }
    }
    public static class model{
        public final static int tag = 1;
        public final static int z = 31;
        //获取攻击距离
        public static int geTattackDistance(Constant.MODELTYPE type){
            switch (type) {
                case JIANTA:
                    return 300;
            }
            return 0;
        }
    }
    public static class player{
        public final static int tag = 2;
        public final static int z = 30;
        public final static float playerAttackInterval = 0.2f;
    }
    public static class bool{
        public final static int tag = 1;
        public final static int z = 20;
    }
}
