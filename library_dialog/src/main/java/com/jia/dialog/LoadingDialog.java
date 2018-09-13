package com.jia.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jia.dialog.util.MyAnimationDrawable;


/**
 * 加载中Dialog
 *
 * @author xm
 */
public class LoadingDialog extends AlertDialog {

    private Context mContext;
    private TextView tips_loading_msg;

    private String message = null;
    private ImageView tips_loading_gif;
    private ProgressBar tips_loading_probar;
    private MyAnimationDrawable animationDrawable;

    public LoadingDialog(Context context) {
        super(context);
        message = "请稍后";
        mContext = context;
        this.setCancelable(false);
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        mContext = context;
        this.setCancelable(false);
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        mContext = context;
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_probar = (ProgressBar) findViewById(R.id.tips_loading_probar);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_gif = (ImageView) findViewById(R.id.tips_loading_gif);
        tips_loading_msg.setText(this.message);

//        tips_loading_gif.setImageResource(R.drawable.frame_rabbit);
//        animationDrawable = (AnimationDrawable) tips_loading_gif.getDrawable();
//        if(animationDrawable!=null){
//            animationDrawable.start();
//        }
        animationDrawable = MyAnimationDrawable.getAnimationDrawable().animateRawManuallyFromXML(R.drawable.frame_rabbit, tips_loading_gif,true, new Runnable() {
            @Override
            public void run() {
                // 动画开始时回调
            }
        }, new Runnable() {
            @Override
            public void run() {
                // 动画结束时回调
            }
        });
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    @Override
    public void cancel() {
        if(animationDrawable!=null){
            animationDrawable.stop();
        }
        super.cancel();
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(animationDrawable!=null){
                animationDrawable.stop();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}