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

public class ImgAdaptationView extends AutoLinearLayout {
    private Context mContext;
    private ImgAdaptationView ins;
    private int mWidth;
    private String text = "";
    private int textSize = 15;
    private int textColor = Color.BLACK;
    private TextView textView;
    private ImageView imageView;
    private int imageBg = 0;

    public ImgAdaptationView(Context context) {
        super(context);
        init(context);
    }

    public ImgAdaptationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImgAdaptationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        ins = this;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        startCanvas();
    }
    private void canvase(){
        LayoutParams params = null;
        if(textView!=null&&imageView!=null){
            int textViewWidth = JiaUtil.getViewWidth(textView);
            int imgViewWidth = JiaUtil.getViewWidth(imageView);
            if(imgViewWidth<new AdaptationTool(ins.getContext()).setCanvasAdaptation(40)){
                imgViewWidth = new AdaptationTool(ins.getContext()).setCanvasAdaptation(40);
            }
            if(textViewWidth+imgViewWidth>mWidth){
                params = new LayoutParams((mWidth - imgViewWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
            }else{
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            textView.setLayoutParams(params);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mWidth = r-l;
        canvase();
    }

    public void startCanvas(){
        removeAllViews();
        textView = new TextView(mContext);
        textView.setText(text);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setSingleLine(true);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(new AdaptationTool(ins.getContext()).setCanvasAdaptation(10),0,0,0);
        addView(textView,params);
        imageView = new ImageView(mContext);
        if(imageBg!=0){
            imageView.setBackgroundResource(imageBg);
        }
        addView(imageView,params);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = ins.getWidth();
//                mWidth = getViewWidthAll(ins);
                canvase();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    public void updataText(String text){
        if(textView!=null){
            textView.setText(text);
        }
    }
    public void setText(String text) {
        this.text = text;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setImageBg(int imageBg) {
        this.imageBg = imageBg;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
