package com.jiage.battle;

import android.graphics.Point;
import android.util.Base64;
import android.util.Log;

import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDDateUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import org.cocos2d.types.CGPoint;
import org.junit.Test;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() throws Exception {
//        System.out.println(-SurfaceViewUtil.getRotationAngle(200,200,100,100));
//        System.out.println(0%20==0);
//        System.out.println(convertFileSize(1452457878));

//        System.out.println(MD5("123456"));


//        List<String> list = new CopyOnWriteArrayList<>();
//        list.add("one");
//        list.add("two");
//        list.add("three");
//
//        Iterator<String> iterator = list.iterator();
//
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//            list.add("four");
//        }
//        // one
//        // two
//        // three
//        System.out.println("============");
//        iterator = list.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//        String encodedString = Base64.encodeToString("whoislcj".getBytes(), Base64.DEFAULT);
//        System.err.println("Base64---->" + encodedString);
//        String decodedString =new String(Base64.decode(encodedString,Base64.DEFAULT));
//        System.err.println("Base64---->" + decodedString);

//        Observable.create(new ObservableOnSubscribe<Boolean>() { // 第一步：初始化Observable
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
//                Thread.sleep(2000);
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io())//异步任务在IO线程执行
//          .observeOn();//执行结果在主线程运行


//        new RxAsynchronous<String>() {
//            @Override
//            public String onSubscribe(ObservableEmitter<String> observable) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                   e.printStackTrace();
//                }
//                return "顶顶顶顶";
//            }
//
//        };
//        System.out.println(encrypt("123456", "shoukala.1234~~!"));//加密前卡密123456
//        System.out.println("Owj3ElO8tduU0VKJhXfcxQ==");//加密后的结果

//        final long starTime = 1526541479;
//        System.out.println(SDDateUtil.getFormatDataFrom(starTime));
//        int a = 20;
//        for (int i = 0; i < 10000; i++) {
//            int randomAdd = OtherUtil.getRandom(1, 100);
//            if(randomAdd<=a){
//                System.out.println("---------    ------------");
//            }
//
//        }

//        int angle = getAngle(100, 100, 50, 200);
//        System.out.println(angle);
//        int angle5 = getAngle(100, 100, 50, 120);
//        System.out.println(angle5);
//        int angle2 = getAngle(100, 100, 200, 200);
//        System.out.println(angle2);
//        int angle3 = getAngle(100, 100, 200, 50);
//        System.out.println(angle3);
//        int angle4 = getAngle(100, 100, 50, 50);
//        System.out.println(angle4);
//        int angle6 = getAngle(100, 100, 100, 50);
//        System.out.println(angle6);
//        float angle6 = getRotationAngle(100, 100, 80, 200);
//        System.out.println(angle6);
        //827  545.875

        //1001.00006  992

        //100

        //90.91736   90.84249

        CGPoint pointDistance = getPointDistance(827, 545.875f, 1001.00006f, 992, 100);
        System.out.println(pointDistance.x+"  "+pointDistance.y);

        realPoint(new float[]{827, 545.875f},new float[]{1001.00006f, 992},100);
    }
    /**
     * 获取两点之间线上一点
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param distance
     * @return
     */
    public static CGPoint getPointDistance(float x1 , float y1, float x2 , float y2, int distance){
        CGPoint point = new CGPoint();
        if(x1 == x2){
            if(y1>y2){
                point.set(x1,y1-distance);
            }else if(y1<y2){
                point.set(x1,y1+distance);
            }else{
                point.set(x1,y1);
            }
        }else if(y1 == y2){
            if(x1>x2){
                point.set(x1-distance,y2);
            }else if(x1<x2){
                point.set(x1+distance,y2);
            }else{
                point.set(x1,y2);
            }
        }else{
            float hypotenuse = gePointDistance(x1, y1, x2, y2);
            float x = (x2 - x1) / hypotenuse * distance + x1;//横坐标
            float y = (y2 - y1) / hypotenuse * distance + y1;//纵坐标
            point.set(x,y);
        }
        return point;
    }

    private double[] realPoint(float origin[], float target[], float speed) {
        double[] nowPoint = new double[3];
        double hypotenuse;
        hypotenuse = Math.sqrt(Math.pow((target[0] - origin[0]), 2) + Math.pow((target[1] - origin[1]), 2));//计算两点距离
        System.out.println("hypotenuse:" + hypotenuse);
        nowPoint[0] = (target[0] - origin[0]) / hypotenuse * speed + origin[0];//横坐标
        nowPoint[1] = (target[1] - origin[1]) / hypotenuse * speed + origin[1];//纵坐标
        nowPoint[2] = Math.toDegrees(Math.atan((target[0] - origin[0]) / (target[1] - origin[1])));//角度
        System.out.println("x;" + nowPoint[0] + " y:" + nowPoint[1] + " angle:" + nowPoint[2]);
        return nowPoint;
    }
    /**
     * 计算两点之间的距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float gePointDistance(float x1 ,float y1,float x2 ,float y2){
        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
    /**
     * 根据两个坐标获取旋转的角度
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float getRotationAngle(float x1, float y1, float x2, float y2){
        float angle = getAngle(x1, y1, x2, y2);
        if(x1 == x2){
            if(y1>y2){
                angle += 180;
            }
        }else if(y1 == y2){
            if(x1>x2){
                angle += 360;
            }
        }else if(x2>x1&&y2>y1){ //第一区间
        }else if(x1>x2&&y2>y1){//第二区间
            angle = 90-Math.abs(angle)+270;
        }else if(x2<x1&&y2<y1){//第三区间
            angle += 180;
        }else{//第四区间
            angle = 90-Math.abs(angle)+90;
        }
        return angle;
    }

    public static float getAngle(float x1, float y1, float x2, float y2){
        return (float) Math.toDegrees(Math.atan((x2 - x1) / (y2 - y1)));
    }

    private int x = 0;
    private void upx(int ux){
        if(Math.abs(ux-x)<10){
            System.out.println("重新计算   x:"+x+" ux:"+ux);
            upx(OtherUtil.getRandom(0,100));
        }else{
            System.out.println("符合   x:"+x+" ux:"+ux);
            x = ux;
        }
    }


    public static String encrypt(String data, String key) throws Exception {
        String iv = MD5(key).toLowerCase().substring(16);//加密向量
        Cipher cipher = Cipher.getInstance("AES/CBC/Padding");
        int blockSize = cipher.getBlockSize();

        byte[] dataBytes = data.getBytes();
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plaintext);
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    private static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}