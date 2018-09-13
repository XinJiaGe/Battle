package com.jiage.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.util.JiaUtil;
import com.zhy.autolayout.AutoLinearLayout;

import static com.jiage.util.JiaUtil.measureWidth;

/**
 * 作者：李忻佳
 * 时间：2017/5/3
 * 说明：带单位的自适应
 */

public class UnitAdaptationView extends AutoLinearLayout {
    private Context mContext;
    private int mWidth = 0;
    private String text = "";
    private int textSize = 16;
    private int textColor = Color.BLACK;
    private String nuitText = "";
    private int nuitTextSize = 16;
    private int nuitTextColor = Color.BLACK;
    private TextView textView;
    private TextView nuitTextView;
    private boolean isSex = false;

    public UnitAdaptationView(Context context) {
        super(context);
        init(context);
    }

    public UnitAdaptationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UnitAdaptationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mWidth = measureWidth(widthMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
            int textViewWidth = JiaUtil.getViewWidth(textView);
            int nuitTextViewWidth = JiaUtil.getViewWidth(nuitTextView);
            if (textViewWidth + nuitTextViewWidth > mWidth) {
                LayoutParams params = new LayoutParams((mWidth - nuitTextViewWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
            } else {
                if(!isSex) {
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    isSex = true;
                }
            }

    }

    public void startCanvas(){
        removeAllViews();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView = new TextView(mContext);
        textView.setText(text);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setSingleLine(true);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        addView(textView,params);
        nuitTextView = new TextView(mContext);
        nuitTextView.setText(nuitText);
        nuitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, nuitTextSize);
        nuitTextView.setTextColor(nuitTextColor);
        nuitTextView.setMaxLines(1);
        addView(nuitTextView,params);
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

    public void setNuitText(String nuitText) {
        this.nuitText = nuitText;
    }

    public void setNuitTextSize(int nuitTextSize) {
        this.nuitTextSize = nuitTextSize;
    }

    public void setNuitTextColor(int nuitTextColor) {
        this.nuitTextColor = nuitTextColor;
    }
}
