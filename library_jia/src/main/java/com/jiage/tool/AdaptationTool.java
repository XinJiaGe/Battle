package com.jiage.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.HashMap;

/**
 * 作者：李忻佳.
 * 时间：2016/11/18.
 * 说明：工具
 */

public class AdaptationTool {
    private final Context mContext;
    private AdaptationTool adaptationTool;
    public int screenWidth;
    public int screenHeight;
    private float RATIO;

    public AdaptationTool(Context context){
        this.mContext = context;
        init();
    }
    private void init(){
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        float ratioWidth = (float)screenWidth / 1080;
        float ratioHeight = (float)screenHeight / 1920;
        RATIO = Math.min(ratioWidth, ratioHeight);
    }
    /**
     * 用于适配不同分辨率的值
     * @param value   值
     * @return
     */
    public int setCanvasAdaptation(int value){
        int a = Math.round(value * RATIO);
        return a;
    }

    /**
     * 获取paint真实高度
     * @param paint
     * @return
     */
    public float getCanvasTextHeight(Paint paint){
        Paint.FontMetrics fontMetrics= paint.getFontMetrics();
        float textHeight = (-fontMetrics.ascent-fontMetrics.descent);
        return textHeight;
    }
    /**
     * 获取数组里面的最大值和最小值
     * @param value
     * @return
     */
    public int getValueMaxIndex(int[] value){
        int i,min,max;
        min=max=value[0];
        for(i=0;i<value.length;i++){
            if(value[i]>max)   // 判断最大值
                return i;
        }
        return -1;
    } /**
     * 获取数组里面的最大值和最小值
     * @param value
     * @return
     */
    public HashMap<String, Integer> getValueMaxMin(int[] value){
        int i,min,max;
        HashMap<String ,Integer> map = new HashMap<>();
        min=max=value[0];
        for(i=0;i<value.length;i++){
            if(value[i]>max)   // 判断最大值
                max=value[i];
            if(value[i]<min)   // 判断最小值
                min=value[i];
        }
        map.put("max",max);
        map.put("min",min);
        return map;
    }
    /**
     * 获取数组里面的最大值和最小值
     * @param value String
     * @return
     */
    public HashMap<String, Double> getValueMaxMin(String[] value){
        Double min,max;
        HashMap<String ,Double> map = new HashMap<>();
        min=max= Double.valueOf(value[0].equals("")?"0":value[0]);
        for(int i=0;i<value.length;i++)
        {
            if(Double.valueOf(value[i].equals("")?"0":value[i])>max)   // 判断最大值
                max= Double.valueOf(value[i].equals("")?"0":value[i]);
            if(Double.valueOf(value[i].equals("")?"0":value[i])<min)   // 判断最小值
                min= Double.valueOf(value[i].equals("")?"0":value[i]);
        }
        map.put("max",max);
        map.put("min",min);
        return map;
    }

    /**
     * 获取数组里面的最大值的大值
     * @param value double
     * @return
     */
    public int getValueMaxLargeValue(double value){
//        if(value>0){
//            if(value>5){
//                if(value>10){
//                    if(value>20){
//                        if(value>50){
//                            if(value>100){
//                                return (int)value+100;
//                            }else{
//                                return 100;
//                            }
//                        }else{
//                            return 50;
//                        }
//                    }else{
//                        return 20;
//                    }
//                }else{
//                    return 10;
//                }
//            }else{
//                return 5;
//            }
//        }else{
//            return (int)value;
//        }
        int intValue = new Double(value).intValue();
        String strValue = String.valueOf(intValue);
        if(value>0){
            String laststr = Integer.parseInt(strValue.substring(0,1))+1+"";
            for (int i = 0; i < strValue.length()-1; i++) {
                laststr+="0";
            }
            String laststr2 = Integer.parseInt(strValue.substring(0,1))+"";
            for (int i = 0; i < strValue.length()-1; i++) {
                laststr2+="0";
            }
            if(strValue.equals(laststr2)){
                return Integer.parseInt(laststr2);
            }else{
                return Integer.parseInt(laststr);
            }
        }else{
            return intValue;
        }
    }
    /**
     * 获取数组里面的最大值的大值
     * @param value int
     * @return
     */
    public int getValueMaxLargeValue(int value){
//        if(value>0){
//            if(value>5) {
//                if (value > 10) {
//                    if (value > 20) {
//                        if (value > 50) {
//                            if (value > 100) {
//                                return value + 100;
//                            } else {
//                                return 100;
//                            }
//                        } else {
//                            return 50;
//                        }
//                    } else {
//                        return 20;
//                    }
//                } else {
//                    return 10;
//                }
//            }else{
//                return 5;
//            }
//        }else{
//            return value;
//        }
        String strValue = String.valueOf(value);
        if(value>0){
            String laststr = Integer.parseInt(strValue.substring(0,1))+1+"";
            for (int i = 0; i < strValue.length()-1; i++) {
                laststr+="0";
            }
            String laststr2 = Integer.parseInt(strValue.substring(0,1))+"";
            for (int i = 0; i < strValue.length()-1; i++) {
                laststr2+="0";
            }
            if(strValue.equals(laststr2)){
                return Integer.parseInt(laststr2);
            }else{
                return Integer.parseInt(laststr);
            }
        }else{
            return value;
        }
    }
}
