package com.jiage.battle.util;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.jiage.battle.entity.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class LocalImageFinder {

    public static final String ALL_IMAGE_URI = "content://media/external/images/media";

    private FragmentActivity mActivity;

    public LocalImageFinder(FragmentActivity activity) {
        this.mActivity = activity;
    }

    @SuppressWarnings("deprecation")
    public List<String> getLocalImage() {
        Cursor cursor = mActivity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);

        List<String> listPath = getImagePathFromCursor(cursor);
        return listPath;
    }

    public void getLocalImage(final LocalImageFinderListener listener) {
        getLocalImage(Uri.parse(ALL_IMAGE_URI), listener);
    }

    public void getLocalImage(final Uri uri, final LocalImageFinderListener listener) {
        if (listener != null) {
            listener.onStart();
        }

        if (uri == null) {
            if (listener != null) {
                listener.onFailure("uri为空");
            }
            return;
        }

        Loader<Cursor> loader = mActivity.getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
                CursorLoader cursorLoader = new CursorLoader(mActivity, uri, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                loader.stopLoading();
                List<String> listPath = getImagePathFromCursor(cursor);
                if (listener != null) {
                    listener.onFinished(listPath);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
        loader.startLoading();
    }

    public void getLocalImage(final LocalImageFindListener listener) {
        getLocalImage(Uri.parse(ALL_IMAGE_URI), listener);
    }

    public void getLocalImage(final Uri uri, final LocalImageFindListener listener) {
        Loader<Cursor> loader = mActivity.getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
                CursorLoader cursorLoader = new CursorLoader(mActivity, uri, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                loader.stopLoading();

                List<ImageModel> listImage = getImageFromCursor(cursor);
                if (listener != null) {
                    listener.onResult(listImage);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
        loader.startLoading();
    }

    public List<String> getImagePathFromCursor(Cursor cursor) {
        List<String> listPath = new ArrayList<String>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                if (!TextUtils.isEmpty(data)) {
                    listPath.add(data);
                }
            }
        }
        return listPath;
    }

    public List<ImageModel> getImageFromCursor(Cursor cursor) {
        List<ImageModel> listImage = new ArrayList<ImageModel>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ImageModel model = new ImageModel();

                long date_added = cursor.getLong(cursor.getColumnIndex((MediaStore.MediaColumns.DATE_ADDED)));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

                model.setDate_added(date_added);
                model.setData(data);

                listImage.add(model);
            }
        }
        return listImage;
    }

    public interface LocalImageFinderListener {
        public void onStart();

        public void onFailure(String msg);

        public void onFinished(List<String> listPath);
    }

    public interface LocalImageFindListener {
        public void onResult(List<ImageModel> listPath);
    }

}
