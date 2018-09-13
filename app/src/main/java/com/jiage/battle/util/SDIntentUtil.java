package com.jiage.battle.util;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * IntentUtil
 */
public class SDIntentUtil {

    public static String mCameraFilePath;

    public static Intent getIntentQQChat(String qqNumber) {
        Intent intent = null;
        if (!TextUtils.isEmpty(qqNumber)) {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        }
        return intent;
    }

    public static Intent getIntentGetContent() {
        // Create and return a chooser with the default OPENABLE
        // actions including the camera, camcorder and sound
        // recorder where available.
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");

        Intent chooser = createChooserIntent(createCameraIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        return chooser;
    }

    private static Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                File.separator + "browser-photos");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }

    private static Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
        return chooser;
    }

    public static Intent getIntentChooser(String title, Intent... intents) {
        // 显示一个供用户选择的应用列表
        Intent intent = new Intent(Intent.ACTION_CHOOSER);
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        return intent;
    }

    public static Intent getIntentOpenBrowser(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        return intent;
    }

    /**
     * 获得打开本地图库的intent
     *
     * @return
     */
    public static Intent getIntentSelectLocalImage() {
        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // intent.setType("image/*");
        // intent.putExtra("crop", true);
        // intent.putExtra("return-data", true);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * 获调用拍照的intent
     *
     * @return
     */
    public static Intent getIntentTakePhoto(File saveFile) {
        if (saveFile == null) {
            return null;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(saveFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 获调发送邮件的intent
     *
     * @return
     */
    public static Intent getIntentSendEmail(String email, String subject) {
        if (TextUtils.isEmpty(email)) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        return intent;
    }

    public static Intent getIntentCallPhone(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
    }

    public static boolean isIntentAvailable(Intent intent) {
        if (intent != null) {
            List<ResolveInfo> activities = SDLibrary.getInstance().getApplication().getPackageManager()
                    .queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            return activities.size() != 0;
        }
        return false;
    }

    public static Intent getIntentLocalMap(String latitude, String longitude, String name) {
        Intent intent = null;
        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
            double lat = SDTypeParseUtil.getDouble(latitude);
            double lon = SDTypeParseUtil.getDouble(longitude);
            intent = getIntentLocalMap(lat, lon, name);
        }
        return intent;
    }

    public static Intent getIntentLocalMap(double latitude, double longitude, String name) {
        String uriString = "geo:" + latitude + "," + longitude;
        if (!TextUtils.isEmpty(name)) {
            uriString = uriString + "?q=" + name;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        if (!isIntentAvailable(intent)) // 没有自带地图
        {
            String uriPre = "http://ditu.google.cn/maps?hl=zh&mrt=loc&q=";
            uriString = uriPre + latitude + "," + longitude;
            if (!TextUtils.isEmpty(name)) {
                uriString = uriString + "(" + name + ")";
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        }
        return intent;
    }

}
