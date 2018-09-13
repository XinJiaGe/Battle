package com.jiage.battle.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.jiage.battle.R;
import com.jiage.battle.activity.MainActivity;
import com.jiage.battle.common.SDActivityManager;
import com.jiage.battle.rxjava.RxAsynchronous;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.ObservableEmitter;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 作者：李忻佳
 * 日期：2017/12/20/020.
 * 说明：
 */

public class NoticeUtil {
    public static NotificationManager mNotificationManager;
    public static NotificationCompat.Builder mBuilder;
    private static Context mContext;

    /**
     * 通知
     *
     * @param title
     * @param ontent
     * @return notifyId
     */
    public static int notice(Context context, String title, String ontent, Intent intent) {
        int id = (int) System.currentTimeMillis();
        notice(context, title, ontent, null, id, intent, Notification.FLAG_AUTO_CANCEL, 0, "通知", Notification.PRIORITY_DEFAULT, true, false,
                Notification.DEFAULT_ALL, null, Notification.GROUP_ALERT_ALL, null, true, null, 0, 0, false);
        return id;
    }
    /**
     * 通知
     *
     * @param title
     * @param ontent
     * @return notifyId
     */
    public static NotificationManager notice(Context context, String title, String ontent, int notifyId, Intent intent) {
        return notice(context, title, ontent, null, notifyId, intent, Notification.FLAG_AUTO_CANCEL, 0, ontent, Notification.PRIORITY_DEFAULT, true, false,
                Notification.DEFAULT_ALL, null, Notification.GROUP_ALERT_ALL, null, true, null, 0, 0, false);
    }
    /**
     * 自定义通知
     *
     * @param title
     * @param ontent
     * @param imgUrl
     * @return notifyId
     */
    public static int notice(Context context, String title, String ontent, String imgUrl, Intent intent) {
        int id = (int) System.currentTimeMillis();
        notice(context, title, ontent, imgUrl, id, intent, Notification.FLAG_AUTO_CANCEL, 0, "通知", Notification.PRIORITY_DEFAULT, true, false,
                Notification.DEFAULT_VIBRATE, null, Notification.GROUP_ALERT_ALL, null, true, null, 0, 0, false);
        return id;
    }

    /**
     * 下载通知
     *
     * @param context
     * @param title
     * @param text
     * @param max
     * @param progress
     * @return notifyId
     */
    public static int noticeDownload(Context context, String title, String text, int max, int progress) {
        notice(context, title, text, null, 100001, null, Notification.FLAG_ONGOING_EVENT, 0, "开始下载",
                Notification.PRIORITY_MAX, false, true, Notification.DEFAULT_ALL, null, Notification.GROUP_ALERT_ALL, null,
                false, null, max, progress, false);
        return 100001;
    }

    /**
     * 通知
     *  @param context        Context
     * @param title          标题
     * @param text           内容
     * @param notifyId       notifyId
     * @param intent         通知栏点击跳转
     * @param flags          设置通知栏点击意图
*                       Notification.FLAG_SHOW_LIGHTS              //三色灯提醒，在使用三色灯提醒时候必须加该标志符
*                       Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
*                       Notification.FLAG_INSISTENT   //让声音、振动无限循环，直到用户响应 （取消或者打开）
*                       Notification.FLAG_ONLY_ALERT_ONCE  //发起Notification后，铃声和震动均只执行一次
*                       Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
*                       Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
*                       Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务
     * @param number         设置通知集合的数量
     * @param ticker         通知首次出现在通知栏，带上升动画效果的
     * @param pri            设置该通知优先级
*                       Notification.PRIORITY_DEFAULT	重要而紧急的通知，通知用户这个事件是时间上紧迫的或者需要立即处理的。
*                       Notification.PRIORITY_HIGH	高优先级用于重要的通信内容，例如短消息或者聊天，这些都是对用户来说比较有兴趣的。
*                       Notification.PRIORITY_LOW	默认优先级用于没有特殊优先级分类的通知。
*                       Notification.PRIORITY_MAX	低优先级可以通知用户但又不是很紧急的事件。
*                       Notification.PRIORITY_MIN	用于后台消息 (例如天气或者位置信息)。最低优先级通知将只在状态栏显示图标，只有用户下拉通知抽屉才能看到内容。
     * @param autoCancel     设置这个标志当用户单击面板就可以让通知将自动取消
     * @param ongoing        设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
     * @param defaults       向通知添加声音、闪灯和振动效果
*                       Notification.DEFAULT_VIBRATE    //添加默认震动提醒  需要 VIBRATE permission
*                       Notification.DEFAULT_SOUND    // 添加默认声音提醒
*                       Notification.DEFAULT_LIGHTS// 添加默认三色灯提醒
*                       Notification.DEFAULT_ALL// 添加默认以上3种全部提醒
     * @param tricolorLamp   三色灯提醒
     * @param led            三色灯参数
*                       ledARGB = led[0];//0xff0000ff;//ledARGB 表示灯光颜色
*                       ledOnMS = led[1];//ledOnMS 亮持续时间
*                       ledOffMS = led[2];//ledOffMS 暗的时间
     * @param defaultTheBell 是否默认铃声
     * @param max            进度条最大数值
     * @param progress       当前进度
     * @param indeterminate  表示进度是否不确定
     */
    private static NotificationManager notice(Context context, String title, String text, String imgUrl, int notifyId, Intent intent, int flags, int number, String ticker, int pri,
                                              boolean autoCancel, boolean ongoing, int defaults, long[] shock, int tricolorLamp, int[] led, boolean defaultTheBell,
                                              Uri uri, int max, int progress, boolean indeterminate) {
        if (context == null) {
            mContext = context;
            mNotificationManager = (NotificationManager) SDActivityManager.getInstance().getLastActivity().getSystemService(NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(SDActivityManager.getInstance().getLastActivity());
        } else {
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);
        }
        if (tricolorLamp == 1) {
            flags = Notification.FLAG_SHOW_LIGHTS;
        }
        mBuilder.setContentTitle(title)//设置通知栏标题
                .setContentIntent(getDefalutIntent(context, intent, flags)) //设置通知栏点击意图
                .setTicker(ticker) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(pri) //设置该通知优先级
                .setAutoCancel(autoCancel)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(ongoing)//设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        if (imgUrl != null) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_notice);
            remoteViews.setTextViewText(R.id.view_notice_title, title);
            remoteViews.setTextViewText(R.id.view_notice_content, text);
            set(remoteViews, notifyId, title, text, imgUrl);
            mBuilder.setCustomContentView(remoteViews);
        }
        if (text != null) {
            mBuilder.setContentText(text); //设置通知栏显示内容
        }
        Notification notify = mBuilder.build();
        if (shock != null) {
            notify.vibrate = shock;//new long[]{0, 300, 500, 700};//设置震动方式。实现效果：延迟0ms，然后振动300ms，在延迟500ms，接着在振动700ms。
        }
        notify.flags = tricolorLamp;//三色灯提醒
        if (led != null) {
            notify.ledARGB = led[0];//0xff0000ff;//ledARGB 表示灯光颜色
            notify.ledOnMS = led[1];//ledOnMS 亮持续时间
            notify.ledOffMS = led[2];//ledOffMS 暗的时间
        }
        if (defaultTheBell) {
            mBuilder.setDefaults(defaults);//获取默认铃声
        } else {
            if (uri != null) {
                mBuilder.setSound(uri);//获取自定义铃声Uri.parse("file:///sdcard/xx/xx.mp3"),获取Android多媒体库内的铃声Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "5")
            }
        }
        if (number != 0) {
            mBuilder.setNumber(number); //设置通知集合的数量
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (max != 0 && progress != 0) {
                mBuilder.setProgress(max, progress, indeterminate);
            }
        }
        mNotificationManager.notify(notifyId, mBuilder.build());
        return mNotificationManager;
    }

    /**
     * 通知栏点击意图
     *
     * @param context
     * @param flags
     * @return
     */
    private static PendingIntent getDefalutIntent(Context context, Intent intent, int flags) {
        PendingIntent pendingIntent;
        if (intent == null) {
            intent = new Intent(context, MainActivity.class);
        }
        int requestCode = (int) SystemClock.uptimeMillis();
        if (context == null) {
            pendingIntent = PendingIntent.getActivity(SDActivityManager.getInstance().getLastActivity(), requestCode, intent, flags);
        } else {
            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags);
        }
        return pendingIntent;
    }


    public static void set(final RemoteViews remoteViews, final int notifyId, final String title, final String text, final String urlStr) {
        new RxAsynchronous<Bitmap>() {
            @Override
            public Bitmap onSubscribe(ObservableEmitter<Bitmap> observable) {
                HttpURLConnection conn = null;
                try {
                    URL mURL = new URL(urlStr);
                    conn = (HttpURLConnection) mURL.openConnection();
                    conn.setRequestMethod("GET"); //设置请求方法
                    conn.setConnectTimeout(10000); //设置连接服务器超时时间
                    conn.setReadTimeout(5000);  //设置读取数据超时时间
                    conn.connect(); //开始连接
                    int responseCode = conn.getResponseCode(); //得到服务器的响应码
                    if (responseCode == 200) {
                        //访问成功
                        InputStream is = conn.getInputStream(); //获得服务器返回的流数据
                        Bitmap bitmap = BitmapFactory.decodeStream(is); //根据流数据 创建一个bitmap对象
                        return bitmap;
                    } else {
                        //访问失败
                        return null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if (conn != null) {
                        conn.disconnect(); //断开连接
                    }
                }
            }

            @Override
            public void onResult(Bitmap result) {
                if (result != null) {
                    remoteViews.setTextViewText(R.id.view_notice_title, title);
                    remoteViews.setTextViewText(R.id.view_notice_content, text);
                    remoteViews.setImageViewBitmap(R.id.view_notice_image, result);
                    mBuilder.setCustomContentView(remoteViews);
                    mNotificationManager.notify(notifyId, mBuilder.build());
                }
            }
        };
    }
}
