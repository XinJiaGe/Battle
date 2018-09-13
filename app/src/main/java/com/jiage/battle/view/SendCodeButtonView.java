package com.jiage.battle.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.jia.dialog.LoadingDialog;
import com.jiage.battle.R;
import com.jiage.battle.dialog.SDMyDialog;
import com.jiage.battle.entity.BaseEntity;
import com.jiage.battle.http.InterfaceServer;
import com.jiage.battle.http.SDRequestCallBack;
import com.jiage.battle.model.RequestModel;
import com.jiage.battle.util.SDTimerUtil;

/**
 * 作者：李忻佳
 * 日期：2017/11/30/030.
 * 说明：发送验证码
 */

public class SendCodeButtonView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private Context mContext;
    private int maxSecond = 60;
    private SDTimerUtil timer = new SDTimerUtil();
    private LoadingDialog loading;
    private onGetPhoneListener mGetPhoneListener;

    public SendCodeButtonView(Context context) {
        super(context);
        init(context);
    }

    public SendCodeButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SendCodeButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setText("获取验证码");
        setTextColor(Color.parseColor("#FF9A55"));
        setGravity(Gravity.CENTER);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setTextColor(Color.parseColor("#FF9A55"));
        setEnabled(false);
        send();
    }

    private void send() {
        if (mGetPhoneListener != null) {
            final String phone = mGetPhoneListener.getPhone();
            if (phone == null || "".equals(phone)) {
                Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_LONG).show();
                setBackgroundResource(R.drawable.layer_yellow_corner_item_single);
                setEnabled(true);
            } else {
                InterfaceServer.getInstance().InterfaceGet(new InterfaceServer.RequestSetGetParamsBack() {
                    @Override
                    public RequestModel setParams(RequestModel requestModel) {
                        requestModel.setApi("Agent/GetVerificationCode");
                        requestModel.put("phone", phone);
                        requestModel.put("type", 1);
                        return requestModel;
                    }
                }, new SDRequestCallBack<BaseEntity>() {
                    @Override
                    public void onStart() {
                        loading = SDMyDialog.loading("请稍后");
                    }

                    @Override
                    public void onSuccess(BaseEntity entity) {
                        timer.startWork(0, 1000, new SDTimerUtil.SDTimerListener() {
                            @Override
                            public void onWork() {

                            }

                            @Override
                            public void onWorkMain() {
                                setText(maxSecond + "秒");
                                maxSecond--;

                                if (maxSecond == 0) {
                                    timer.stopWork();
                                    maxSecond = 60;
                                    setText("获取验证码");
                                    setTextColor(Color.parseColor("#FF9A55"));
                                    setEnabled(true);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error, String msg) {
                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                        setTextColor(Color.parseColor("#FF9A55"));
                        setEnabled(true);
                    }

                    @Override
                    public void onFinish() {
                        if (loading != null) {
                            loading.cancel();
                        }
                    }
                });
            }
        }
    }

    public void setPhoneListener(onGetPhoneListener getPhoneListener) {
        this.mGetPhoneListener = getPhoneListener;
    }

    public interface onGetPhoneListener {
        public String getPhone();
    }
}
