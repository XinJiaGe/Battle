package com.jiage.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：李忻佳.
 * 时间：2017/2/10.
 * 说明：
 */

public class JiaUtil {
    /**
     * 去掉小数点
     * @param str
     * @return
     */
    public static String deleteDecimal(String str){
        try {
            int intstr = Integer.parseInt(str);
            double doublestr = Double.parseDouble(str);
            if(doublestr>intstr){
                return str;
            }else{
                return intstr+"";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return str;
        }
    }
    /**
     * 去掉小数点
     * @param str
     * @return
     */
    public static String deleteDecimal(double str){
        try {
            int intstr = (int)str;
            if(str>intstr){
                return str+"";
            }else{
                return intstr+"";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return str+"";
        }
    }

    /**
     * 获取控件的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        int width = 0;
        width = view.getWidth();
        if (width <= 0) {
            measureView(view);
            width = view.getMeasuredWidth();
        }
        return width;
    }

    /**
     * 测量角度
     * @param v
     */
    public static void measureView(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }
    /**
     * 获取控件包含控件内的宽度
     * @param view
     * @return
     */
    public static int getViewWidthAll(View view)
    {
        int width = getViewWidth(view);
        ViewGroup.MarginLayoutParams params = getViewMarginLayoutParams(view);
        if (params != null)
        {
            width = width + params.leftMargin + params.rightMargin;
        }
        return width;
    }
    /**
     * 获取控件的MarginLayoutParams
     * @param view
     * @return
     */
    public static ViewGroup.MarginLayoutParams getViewMarginLayoutParams(View view)
    {
        ViewGroup.MarginLayoutParams result = null;
        if (view != null)
        {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params != null && params instanceof ViewGroup.MarginLayoutParams)
            {
                result = (ViewGroup.MarginLayoutParams) params;
            }
        }
        return result;
    }

    //根据xml的设定获取宽度
    public static int measureWidth(int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == View.MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == View.MeasureSpec.EXACTLY){

        }
        return specSize;
    }
    //根据xml的设定获取高度
    public static int measureHeight(int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == View.MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == View.MeasureSpec.EXACTLY){

        }
        return specSize;
    }
}
