package com.jiage.battle;

import android.util.Base64;

import com.jiage.battle.util.SDDateUtil;

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
//                    e.printStackTrace();
//                }
//                return "顶顶顶顶";
//            }
//
//        };
//        System.out.println(encrypt("123456", "shoukala.1234~~!"));//加密前卡密123456
//        System.out.println("Owj3ElO8tduU0VKJhXfcxQ==");//加密后的结果

        final long starTime = 1526541479;
        System.out.println(SDDateUtil.getFormatDataFrom(starTime));
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