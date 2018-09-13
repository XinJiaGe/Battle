package com.jiage.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.tool.AdaptationTool;
import com.jiage.util.JiaUtil;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * 作者：李忻佳
 * 时间：2017/5/3
 * 说明：带图片的自适应
 */

public class ImagAdaptationView extends AutoLinearLayout {
    private Context mContext;
    private ImagAdaptationView ins;
    private int mWidth;
    private int textSize = 16;
    private int textColor = Color.BLACK;
    private TextView textView;
    private ImageView imagView;
    private int maxWidth;
    private AdaptationTool adaptationTool;

    public ImagAdaptationView(Context context) {
        super(context);
        init(context);
    }

    public ImagAdaptationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImagAdaptationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        ins = this;
        adaptationTool = new AdaptationTool(context);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        textView = new TextView(mContext);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(textView,params);
        imagView = new ImageView(mContext);
        addView(imagView,params);
    }
    private void canvase(){
        int textViewWidth = JiaUtil.getViewWidth(textView);
        int nuitTextViewWidth = JiaUtil.getViewWidth(imagView);
        if(textViewWidth+nuitTextViewWidth>mWidth){
            textView.setLayoutParams(new LayoutParams(maxWidth-nuitTextViewWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
    public void startCanvas(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = ins.getWidth();
                canvase();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImagView() {
        return imagView;
    }

    public void setImagView(ImageView imagView) {
        this.imagView = imagView;
    }
}
