package com.jiage.battle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zhy.autolayout.utils.ScreenUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：李忻佳
 * 日期：2018/1/5/005.
 * 说明：
 */

public class BitmapUtils {
    public BitmapUtils() {

    }

    public static Bitmap getBitmapThumbnail(String path, int width, int height){
        Bitmap bitmap = null;
        //这里可以按比例缩小图片：
                /*BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 4;//宽和高都是原来的1/4
                bitmap = BitmapFactory.decodeFile(path, opts); */

                /*进一步的，
                    如何设置恰当的inSampleSize是解决该问题的关键之一。BitmapFactory.Options提供了另一个成员inJustDecodeBounds。
                   设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，即opts.width和opts.height。
                   有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。*/
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = Math.max((int)(opts.outHeight / (float) height), (int)(opts.outWidth / (float) width));
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }

    public static Bitmap getBitmapThumbnail(Bitmap bmp,int width,int height){
        Bitmap bitmap = null;
        if(bmp != null ){
            int bmpWidth = bmp.getWidth();
            int bmpHeight = bmp.getHeight();
            if(width != 0 && height !=0){
                Matrix matrix = new Matrix();
                float scaleWidth = ((float) width / bmpWidth);
                float scaleHeight = ((float) height / bmpHeight);
                matrix.postScale(scaleWidth, scaleHeight);
                bitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
            }else{
                bitmap = bmp;
            }
        }
        return bitmap;
    }

    //Bitmap对象保存味图片文件
    public static File saveBitmapFile(Bitmap bitmap, String path){
        File file=new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 处理游戏图片
     * @param bitmap
     * @return
     */
    public static Bitmap getBitmap(Bitmap bitmap,float sx,float sy){
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        Bitmap tempBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return tempBmp;
    }
    /**
     * 按照大小处理图片
     */
    public static Bitmap getBitmap(Bitmap bitmap,int w,int h){
        Matrix matrix = new Matrix();
        float bw = bitmap.getWidth();
        float bh = bitmap.getHeight();
        float sw = w / bw;
        float sh = h / bh;
        matrix.postScale(sw,sh);
        return getBitmap(bitmap,sw,sh);
    }
    /**
     * 镜像垂直翻转图片
     */
    public static Bitmap getScaleMap(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    /**
     * 读取资源图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap ReadBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    /**
     * 读取资源图片（Assets）
     * @param context
     * @param resname
     * @return
     */
    public static Bitmap ReadBitMap(Context context,String resname) {
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = context.getAssets().open(resname);
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 按照比例处理图片
     * @param bitmap
     * @return
     */
    public static Bitmap getBitmap(Context context,Bitmap bitmap,int type){
        Bitmap tempBmp = null;
        try {
            Matrix matrix = new Matrix();
            float scaleW = (float) SDViewUtil.getScreenWidth()/bitmap.getWidth();
            float scaleH = (float) SDViewUtil.getScreenHeight()/bitmap.getHeight();
            if(type==1){
                matrix.postScale(scaleW, scaleW);
            }else if(type==2){
                scaleW = ((float)bitmap.getWidth())/2/100;
                matrix.postScale(scaleW, scaleW);
            }else if(type==3){
                scaleW = scaleW/2;
                matrix.postScale(scaleW, scaleW);
            }else if(type==4){
                matrix.postScale(1, scaleH);
            }else{
                matrix.postScale(scaleW, scaleH);
            }
            tempBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempBmp;
    }
    /**
     * 图片切割器 按照宽度切割
     */
    public static Bitmap[] widthSplit(Bitmap bitMap, int spite){
        Bitmap[] bits = new Bitmap[spite];
        int bw = bitMap.getWidth();
        int bh = bitMap.getHeight();
        int piecW = bw / spite;
        for (int j = 0; j <spite; j++) {
            int xv = j * piecW;
            bits[j] = Bitmap.createBitmap(bitMap,
                    xv,
                    0,
                    piecW,
                    bh);
        }
        return bits;
    }
    /**
     * 图片切割器 按照高度切割
     */
    public static Bitmap[] heightSpite(Bitmap bitMap,int spite){
        Bitmap[] bits = new Bitmap[spite];
        int bw = bitMap.getWidth();
        int bh = bitMap.getHeight();
        int piecH = bh / spite;
        int xy=0;
        for (int j = 0; j <spite; j++) {
            xy = j*piecH;
            bits[j] = Bitmap.createBitmap(bitMap,
                    0,
                    xy,
                    bw,
                    piecH);
        }
        return bits;
    }
}
