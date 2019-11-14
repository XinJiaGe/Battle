package com.jiage.battle.cocos2d.stickto2;

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
        public static int getSpeed(Constant.AttackType attacktype){
            switch (attacktype) {
                case GongJian:
                    return 1200;
                case DianQiu:
                    return 800;
            }
            return 0;
        }
        //获取攻击力
        public static int getAggressivity(Constant.AttackType attacktype){
            switch (attacktype) {
                case GongJian:
                    return 1;
                case DianQiu:
                    return 5;
            }
            return 0;
        }
    }
    public static class enemy{
        public final static int tag = 9;
        public final static float addEnemyInterval = 0.6f;
        public static int getZ(Constant.EnemyType enemytype){
            switch (enemytype) {
                case ZhiZhu:
                    return 10;
                case XiaoChong:
                    return 11;
            }
            return 0;
        }
        //获取金币
        public static int getGold(Constant.EnemyType enemytype){
            switch (enemytype) {
                case ZhiZhu:
                    return 1;
                case XiaoChong:
                    return 2;
            }
            return 1;
        }
        //获取移动速度
        public static int getSpeed(Constant.EnemyType enemytype){
            switch (enemytype) {
                case ZhiZhu:
                    return 200;
                case XiaoChong:
                    return 150;
            }
            return 200;
        }
        //获取血量
        public static int getBlood(Constant.EnemyType enemytype){
            switch (enemytype) {
                case ZhiZhu:
                    return 1;
                case XiaoChong:
                    return 2;
            }
            return 1;
        }
    }
    public static class model{
        public final static int tag = 1;
        public final static int z = 31;
        //获取攻击距离
        public static int geTattackDistance(Constant.ModelType type){
            switch (type) {
                case JianTa:
                    return 400;
                case DianTa:
                    return 300;
            }
            return 0;
        }
    }
    public static class player{
        public final static int tag = 2;
        public final static int z = 30;
        public final static float playerAttackInterval = 0.2f;
        //获取攻击速度
        public static int getSpeed(Constant.AttackType attackType){
            switch (attackType) {
                case GongJian:
                    return 20;
                case DianQiu:
                    return 80;
            }
            return 10;
        }
    }
    public static class blood{
        public final static int tag = 1;
        public final static int z = 20;
    }
}
