package com.jiage.battle.handler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.jiage.battle.common.SDActivityManager;
import com.jiage.battle.util.SDFileUtil;
import com.jiage.battle.util.SDIntentUtil;
import com.jiage.battle.util.SDToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoHandler extends OnActivityResultHandler {
    public static final String TAKE_PHOTO_FILE_DIR_NAME = "take_photo";
    public static final int REQUEST_CODE_GET_PHOTO_FROM_CAMERA = 16542;
    public static final int PHOTO_REQUEST_CUT = 300;
    public static final int REQUEST_CODE_GET_PHOTO_FROM_ALBUM = REQUEST_CODE_GET_PHOTO_FROM_CAMERA + 1;

    private PhotoHandlerListener mListener;
    private File mTakePhotoFile;
    private File mTakePhotoDir;
    private boolean isZoom = false;

    public void setmListener(PhotoHandlerListener mListener) {
        this.mListener = mListener;
    }

    public PhotoHandler(Fragment mFragment) {
        super(mFragment);
        init();
    }

    public PhotoHandler(FragmentActivity mActivity) {
        super(mActivity);
        init();
    }

    private void init() {
        File saveFileDir = SDActivityManager.getInstance().getLastActivity().getExternalCacheDir();
        if (saveFileDir != null) {
            mTakePhotoDir = new File(saveFileDir, TAKE_PHOTO_FILE_DIR_NAME);
            mTakePhotoDir.mkdirs();
        }
    }

    public void getPhotoFromAlbum() {
        Intent intent = SDIntentUtil.getIntentSelectLocalImage();
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public void getPhotoFromCamera() {
        if (mTakePhotoDir == null) {
            if (mListener != null) {
                mListener.onFailure("获取SD卡缓存目录失败");
            }
        } else {
            File takePhotoFile = createTakePhotoFile();
            getPhotoFromCamera(takePhotoFile);
        }
    }

    private File createTakePhotoFile() {
        long current = System.currentTimeMillis();
        File takePhotoFile = new File(mTakePhotoDir, current + "");
        try {
            while (takePhotoFile.exists()) {
                current++;
                takePhotoFile = new File(mTakePhotoDir, current + "");
            }
        } catch (Exception e) {
            if (mListener != null) {
                mListener.onFailure("创建照片文件失败:" + e.toString());
            }
        }
        return takePhotoFile;
    }

    public void getPhotoFromCamera(File saveFile) {
        mTakePhotoFile = saveFile;
        Intent intent = SDIntentUtil.getIntentTakePhoto(saveFile);
        startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_CAMERA);
    }

    public void deleteTakePhotoFiles() {
        if (mTakePhotoDir != null) {
            SDFileUtil.deleteFile(mTakePhotoDir);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_GET_PHOTO_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    if (mListener != null) {
                        if (mTakePhotoFile != null) {
                            if(isZoom)
                                startPhotoZoom(Uri.fromFile(mTakePhotoFile), 200);
                            else
                                mListener.onResultFromAlbum(mTakePhotoFile);
                        } else {

                        }
                    }
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (resultCode == Activity.RESULT_OK) {
                    startPhotoZoom(data.getData(), 200);
                }
                break;
            case REQUEST_CODE_GET_PHOTO_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = bundle.getParcelable("data");
                        File imgFileDir = new File(mActivity.getExternalCacheDir().getPath() + "/img");
                        File imgFile = new File(mActivity.getExternalCacheDir().getPath() + "/img", System.currentTimeMillis() + ".jpg");
                        deleteDir(imgFileDir);
                        if (!imgFileDir.exists() && !imgFileDir.isDirectory()) {
                            imgFileDir.mkdir();
                        }
                        try {
                            imgFile.createNewFile();
                            FileOutputStream fOut = new FileOutputStream(imgFile);
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            SDToast.showToast("照片创建失败!");
                        }
                        if (mListener != null) {
                            if (!imgFile.exists()) {
                                mListener.onFailure("从相册获取图片失败");
                            } else {
                                int max_image_size = 10240;
                                if (SDFileUtil.getAFileSize(imgFile) / 1024 > max_image_size) {
                                    mListener.onFailure("照片允许上传最大为" + max_image_size + "kb");
                                } else {
                                    mListener.onResultFromAlbum(imgFile);
                                }
                            }
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_ALBUM);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public interface PhotoHandlerListener {
        public void onResultFromAlbum(File file);

        public void onResultFromCamera(File file);

        public void onFailure(String msg);
    }

    public boolean isZoom() {
        return isZoom;
    }

    public void setZoom(boolean zoom) {
        isZoom = zoom;
    }
}
