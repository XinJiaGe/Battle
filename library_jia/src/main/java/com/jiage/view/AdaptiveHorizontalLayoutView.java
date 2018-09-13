package com.jiage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.zhy.autolayout.AutoLinearLayout;

import static com.jiage.util.JiaUtil.getViewWidth;

/**
 * 作者：李忻佳.
 * 时间：2017/2/22.
 * 说明：自适应horizontal布局
 */

public class AdaptiveHorizontalLayoutView extends AutoLinearLayout {
    private Context mContext;
    private int startIndex = 0;
    private int endIndex = 0;
    private Adapter adapter;
    private int mWidth;
    private AdaptiveHorizontalLayoutView ins;
    private boolean isALine = true; //是否只显示一行
    private onMyClickListener mListener;
    private towLineListener mTowLineListener;

    public AdaptiveHorizontalLayoutView(Context context) {
        super(context);
        init(context);
    }

    public AdaptiveHorizontalLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdaptiveHorizontalLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        mContext = context;
        ins = this;
        setOrientation(LinearLayout.VERTICAL);
    }
    public void startCanvase(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = ins.getWidth();
                updataView();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    /**
     * 初始化view
     */
    public void updataView() {
        if(adapter!=null) {
            startIndex = 0;
            removeAllViews();
            endIndex = adapter.getCount();
            addMyView(startIndex);
        }
    }
    public void updataView(Adapter adapter) {
        if(adapter!=null) {
            this.adapter = adapter;
            startIndex = 0;
            removeAllViews();
            endIndex = adapter.getCount();
            addMyView(startIndex);
        }
    }

    /**
     * 添加view
     * @param intStartIndex
     */
    private void addMyView(int intStartIndex){
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(linearLayout);
        for (int i = intStartIndex; i < endIndex; i++) {
            View v = adapter.getView(i, null, null);
            if(mListener!=null){
                final int finalI = i;
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(finalI,v);
                    }
                });
            }
            //绑定Adapter的数据到LinearLayout布局
            linearLayout.addView(v);
            if(i<endIndex-1){
                int llWidth = getViewWidth(linearLayout);
                int nextIndex = i+1;
                int nextWidth = getViewWidth(adapter.getView(nextIndex, null, null));
                if(llWidth+nextWidth>mWidth){
                    if(mTowLineListener!=null){
                        mTowLineListener.onTowLine();
                    }
                    if(isALine){
                        break;
                    }else{
                        startIndex = i+1;
                        addMyView(startIndex);
                    }
                    break;
                }
            }
        }
    }
    public void setOnMyClickListener(onMyClickListener listener){
        this.mListener = listener;
    }
    public interface onMyClickListener{
        public void onClick(int index, View view);
    }
    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public boolean isALine() {
        return isALine;
    }

    public void setALine(boolean ALine) {
        isALine = ALine;
    }

    public void setTowLineListener(towLineListener lineListener){
        this.mTowLineListener = lineListener;
    }
    public interface towLineListener{
        public void onTowLine();
    }
}
