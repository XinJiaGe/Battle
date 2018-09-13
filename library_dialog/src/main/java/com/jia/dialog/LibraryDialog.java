package com.jia.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
import android.view.KeyEvent;

public class LibraryDialog {
	private static TipsToast tipsToast;
	private static LoadingDialog dialog;

	/**
	 * 加载中
	 * @param context 
	 * @return
	 */
	public static LoadingDialog loading(final Context context){
		dialog = new LoadingDialog(context);
		dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
                    dialog.dismiss();
                }
                return false;
            }
        });
		dialog.show();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
		return dialog;
	}
	/**
	 * 加载中
	 * @param context
	 * @param msg	加载描述
	 * @return
	 */
	public static LoadingDialog loading(final Context context, String msg){
		dialog = new LoadingDialog(context,msg);
		dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
                    dialog.dismiss();
                }
                return false;
            }
        });
		dialog.show();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
		return dialog;
	}
	/**
	 * 警告
	 * @param context
	 * @return
	 */
	public static TipsToast warning(Context context){
		tipsToast = showTips(context, R.drawable.tips_warning,"");
		return tipsToast;
	}
	/**
	 * 警告
	 * @param context
	 * @param msg	警告描述
	 * @return
	 */
	public static TipsToast warning(Context context,String msg){
		tipsToast = showTips(context, R.drawable.tips_warning, msg);
		return tipsToast;
	}
	/**
	 * 成功
	 * @param context
	 * @return
	 */
	public static TipsToast success(Context context){
		tipsToast = showTips(context, R.drawable.icon_s, "");
		return tipsToast;
	}
	/**
	 * 成功
	 * @param context
	 * @param msg	成功描述
	 * @return
	 */
	public static TipsToast success(Context context,String msg){
		tipsToast = showTips(context, R.drawable.icon_s, msg);
		return tipsToast;
	}
	/**
	 * 失败
	 * @param context	
	 * @return
	 */
	public static TipsToast fail(Context context){
		tipsToast = showTips(context, R.drawable.icon_f, "");
		return tipsToast;
	}
	/**
	 * 失败
	 * @param context
	 * @param msg	 失败描述
	 * @return
	 */
	public static TipsToast fail(Context context,String msg){
		tipsToast = showTips(context, R.drawable.icon_f, msg);
		return tipsToast;
	}
	/**
	 * 笑脸
	 * @param context
	 * @return
	 */
	public static TipsToast laugh(Context context){
		tipsToast = showTips(context, R.drawable.tips_smile, null);
		return tipsToast;
	}
	private static TipsToast showTips(Context context ,int iconResId, String msgResId) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = TipsToast.makeText(context.getApplicationContext(), msgResId==null?"":msgResId, TipsToast.LENGTH_SHORT);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(Html.fromHtml(msgResId==null?"":msgResId));
		return tipsToast;
	}
}
