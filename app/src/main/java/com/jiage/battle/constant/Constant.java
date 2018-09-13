package com.jiage.battle.constant;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2017/12/25/025.
 * 说明：
 */

public class Constant {

    /**
     * 功能
     */
    public enum FunctionType{
        doubleTimes, //两倍
        threeTimes, //三倍
        fourTimes, //四倍
        nulls//没有
    }

    /**
     * 获取功能数据
     */
    public static class FunctionTypeGet{
        /**
         * 获取颜色
         * @param functionType
         * @return
         */
        public static int getColor(FunctionType functionType){
            switch (functionType) {
                case doubleTimes:
                    return Color.GRAY;
                case threeTimes:
                    return Color.GRAY;
                case fourTimes:
                    return Color.GRAY;
                case nulls:
                    return Color.GRAY;
                default:
                    return Color.GRAY;
            }
        }

        /**
         * 获取高度
         * @param functionType
         * @return
         */
        public static int getHeight(FunctionType functionType){
            switch (functionType) {
                case doubleTimes:
                    return 50;
                case threeTimes:
                    return 50;
                case fourTimes:
                    return 50;
                case nulls:
                    return 50;
                default:
                    return 50;
            }
        }

        /**
         * 获取宽度
         * @param functionType
         * @return
         */
        public static int getWidht(FunctionType functionType){
            switch (functionType) {
                case doubleTimes:
                    return 50;
                case threeTimes:
                    return 50;
                case fourTimes:
                    return 50;
                case nulls:
                    return 50;
                default:
                    return 50;
            }
        }
    }
    /**
     * 主角
     */
    public static class Player{
        public static final int width = 250;//宽度
        public static final int height = 25;//高度
        public static final int color = Color.BLACK;//颜色
    }
    /**
     * 球
     */
    public static class Bullet{
        public static final int radius = 10;//半径
        public static final int color = Color.RED;//颜色
    }
    /**
     * 方块
     */
    public static class Block{

        /**
         * 获取方块宽度
         * @param screenW
         * @param size
         * @return
         */
        public static int getWidth(int screenW ,int size){
            return screenW/size;
        }

        /**
         * 获取方块颜色
         * @param i
         */
        public static int getColor(int i){
            switch (i) {
                case 0:
                    return Color.WHITE;
                case 1:
                    return Color.BLUE;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.YELLOW;
                case 4:
                    return Color.RED;
                case 5:
                    return Color.CYAN;
                case 6:
                    return Color.DKGRAY;
                case 7:
                    return Color.GRAY;
                case 8:
                    return Color.TRANSPARENT;
            }
            return Color.BLACK;
        }
    }
}
