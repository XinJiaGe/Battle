package com.jiage.battle.http;


import android.util.Log;

import com.jiage.battle.common.SDActivityManager;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.entity.UserEntity;
import com.jiage.battle.model.RequestModel;
import com.jiage.battle.util.JsonUtil;
import com.jiage.battle.util.SDToast;
import com.jiage.battle.util.TANetWorkUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接口请求类
 */
public class InterfaceServer {
    private Callback.Cancelable cancelable;

    private static final class Holder {
        static final InterfaceServer mInstance = new InterfaceServer();
    }

    private InterfaceServer() {
    }

    public static InterfaceServer getInstance() {
        return Holder.mInstance;
    }


    /**
     * 下载文件
     * @param saveFilePath    保存路径
     * @param setParams
     * @param requestCallBack
     */
    public Callback.Cancelable InterfaceDownload(String saveFilePath, RequestSetGetParamsBack setParams, SDRequestCallBack<File> requestCallBack) {
        return requestInterfaceDownLoad(saveFilePath, setParams, requestCallBack);
    }
    /**
     * 上传文件
     * @param setParams
     * @param requestCallBack
     */
    public Callback.Cancelable InterfaceUpload(RequestSetGetParamsBack setParams, RequestCallBack<String> requestCallBack) {
        return requestInterfaceUpload(setParams, requestCallBack);
    }

    /**
     * Get请求
     *
     * @param setParams
     */
    public void InterfaceGet(RequestSetGetParamsBack setParams, RequestCallBack<String> requestCallBack) {
        requestInterface(null, HttpMethod.GET, setParams, requestCallBack);
    }

    /**
     * Get请求
     *
     * @param url
     * @param setParams
     */
    public void InterfaceGet(String url, RequestSetGetParamsBack setParams, RequestCallBack<String> requestCallBack) {
        requestInterface(url, HttpMethod.GET, setParams, requestCallBack);
    }

    /**
     * post请求
     *
     * @param setParams
     */
    public void InterfacePost(RequestSetGetParamsBack setParams, RequestCallBack<String> requestCallBack) {
        requestInterface(null, HttpMethod.POST, setParams, requestCallBack);
    }

    /**
     * GET  POST  请求
     *
     * @param url
     * @param httpMethod 请求方法
     * @param setParams  请求参数
     */
    private void requestInterface(String url, HttpMethod httpMethod, RequestSetGetParamsBack setParams, RequestCallBack<String> requestCallBack) {
//        if (TANetWorkUtil.isNetworkAvailable(MyApplication.getApplication())) {
            RequestModel requestModel = new RequestModel();
            RequestModel requestParamsModel = setParams.setParams(requestModel);
            if (requestParamsModel != null) {
                RequestParams requestParams = getRequestParams(url, httpMethod, requestParamsModel);
                UserEntity user = JsonDbModelDao.getInstance().query(UserEntity.class, true);
                if (user != null) {
                    requestParams.addHeader("Authorization", "type " + user.getToken());
                }
                x.http().request(httpMethod, requestParams, new RequestCallBackProxy(requestCallBack));
            } else {
                if (requestCallBack != null) {
                    requestCallBack.onFailure("", "没带参数");
                    SDToast.showToast("没带参数");
                    requestCallBack.onFinish();
                }
            }
//        } else {
//            if (requestCallBack != null) {
//                SDToast.showToast("网络不可用！");
//                requestCallBack.onFinish();
//            }
//        }
    }

    /**
     * 下载
     *
     * @param saveFilePath    下载文件保存路径
     * @param setParams       请求参数
     * @param requestCallBack
     */
    private Callback.Cancelable requestInterfaceDownLoad(String saveFilePath, RequestSetGetParamsBack setParams, final SDRequestCallBack<File> requestCallBack) {
        if (TANetWorkUtil.isNetworkAvailable(SDActivityManager.getInstance().getLastActivity().getApplication())) {
            RequestModel requestModel = new RequestModel();
            RequestModel requestParamsModel = setParams.setParams(requestModel);
            if (requestParamsModel != null) {
                RequestParams requestParams = new RequestParams(requestParamsModel.getDownloadUrl());
                requestParams.setAutoResume(true);//设置是否在下载是自动断点续传
                requestParams.setAutoRename(false);//设置是否根据头信息自动命名文件
                requestParams.setSaveFilePath(saveFilePath);
                requestParams.setCancelFast(true);//是否可以被立即停止.
                UserEntity user = JsonDbModelDao.getInstance().query(UserEntity.class, true);
                if (user != null) {
                    requestParams.addHeader("Authorization", "type " + user.getToken());
                }
                cancelable = x.http().get(requestParams, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onCancelled(CancelledException arg0) {
                        requestCallBack.onCancelled();//取消
                    }

                    @Override
                    public void onError(Throwable arg0, boolean arg1) {
                        requestCallBack.onFailure("", arg0.getMessage());
                    }

                    @Override
                    public void onFinished() {
                        requestCallBack.onFinish();
                    }

                    @Override
                    public void onSuccess(File arg0) {
                        requestCallBack.onSuccess(arg0);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        if (isDownloading) {
                            requestCallBack.onLoading(total, current, isDownloading);
                        }
                    }

                    @Override
                    public void onStarted() {
                        requestCallBack.onStart();
                    }

                    @Override
                    public void onWaiting() {
                    }
                });
                return cancelable;
            } else {
                if (requestCallBack != null) {
                    requestCallBack.onFailure("", "没带参数");
                    requestCallBack.onFinish();
                }
            }
        } else {
            if (requestCallBack != null) {
                requestCallBack.onFailure("", "网络不可用");
                requestCallBack.onFinish();
            }
        }
        return null;
    }
    /**
     * 上传
     *
     * @param setParams       请求参数
     * @param requestCallBack
     */
    private Callback.Cancelable requestInterfaceUpload(RequestSetGetParamsBack setParams, final RequestCallBack<String> requestCallBack) {
        if (TANetWorkUtil.isNetworkAvailable(SDActivityManager.getInstance().getLastActivity().getApplication())) {
            RequestModel requestModel = new RequestModel();
            RequestModel requestParamsModel = setParams.setParams(requestModel);
            if (requestParamsModel != null) {
                StringBuffer urlBuff = new StringBuffer();
                RequestParams requestParams = new RequestParams(ApkConstant.getApiUrl() + requestParamsModel.getUploadUrl() + "?");
                List<KeyValue> list = new ArrayList<>();

                Map<String, Object> dataFile = requestParamsModel.getmUploadFile();
                if(dataFile!=null){
                    for (Map.Entry<String, Object> item : dataFile.entrySet()) {
                        if (item != null) {
                            list.add(new KeyValue(item.getKey(),item.getValue()));
                        }
                    }
                }
                MultipartBody body=new MultipartBody(list,"UTF-8");
                requestParams.setRequestBody(body);
                UserEntity user = JsonDbModelDao.getInstance().query(UserEntity.class, true);
                if (user != null) {
                    requestParams.addHeader("Authorization", "type " + user.getToken());
                }
                requestParams.addHeader("Accept", "application/json");
                cancelable = x.http().request(HttpMethod.POST, requestParams, new RequestCallBackLodingProxy(requestCallBack));
                return cancelable;
            } else {
                if (requestCallBack != null) {
                    requestCallBack.onFailure("", "没带参数");
                    requestCallBack.onFinish();
                }
            }
        } else {
            if (requestCallBack != null) {
                requestCallBack.onFailure("", "网络不可用");
                requestCallBack.onFinish();
            }
        }
        return null;
    }

    public interface RequestSetGetParamsBack {
        RequestModel setParams(RequestModel requestModel);
    }

    private RequestParams getRequestParams(String url, HttpMethod method, RequestModel model) {
        StringBuffer urlBuff = new StringBuffer();
        RequestParams requestParams = new RequestParams(url == null ? ApkConstant.getApiUrl() + model.getApi() + "?" : url);

        Map<String, Object> data = model.getData();
        if (data != null) {
            if (method == HttpMethod.GET) {
                for (Map.Entry<String, Object> item : data.entrySet()) {
                    if (item != null) {
                        urlBuff.append(item.getKey() + "=" + String.valueOf(item.getValue()) + "&");
                        requestParams.addQueryStringParameter(item.getKey(), String.valueOf(item.getValue()));
                    }
                }
                Log.e("MYHTTP", "URL:   " + requestParams.getUri() + urlBuff.toString());
            } else {
                String jsonString = JsonUtil.obj2JsonString(data);
                requestParams.setAsJsonContent(true);
                requestParams.setBodyContent(jsonString);
                Log.e("MYHTTP", "URL:   " + requestParams.getUri() + " " + jsonString);
            }
        }
        return requestParams;
    }
}
