package com.jiage.battle.common;

/**
 * 作者：李忻佳
 * 日期：2018/1/11/011.
 * 说明：
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.jiage.battle.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * 图片配置文件
 */
public class ImgConfig {
    public static void initImgConfig(Context context) {
        File cacheDir =new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hongbao/imageloader");
        //ImageLoaderConfiguration是针对图片缓存的全局配置，主要有线程类、缓存大小、磁盘大小、图片下载与解析、日志方面的配置。
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))
                // 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);
    }
    //人物头像的加载
    //DisplayImageOptions用于指导每一个Imageloader根据网络图片的状态（空白、下载错误、正在下载）显示对应的图片，是否将缓存加载到磁盘上，下载完后对图片进行怎么样的处理。

    public static DisplayImageOptions getPortraitOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .build();
        return options;
    }
    //人物头像的加载
    public static DisplayImageOptions getPortraitLargeOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .build();
        return options;
    }
    //通讯录头像的加载
    public static DisplayImageOptions getFriendsPortraitLargeOption(boolean sex){
        DisplayImageOptions.Builder option = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true);                          //启用EXIF和JPEG图像格式
        if(sex)
            //加载失败时的图片 男
            return option.showImageOnFail(R.drawable.icon_register).build();
        else
            //加载失败时的图片 女
            return option.showImageOnFail(R.drawable.icon_register_information).build();
    }
    //大图的加载
    public static DisplayImageOptions getBigImgOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .build();
        return options;
    }
    //相册的加载
    public static DisplayImageOptions getAlbumImgOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .bitmapConfig(Bitmap.Config.RGB_565)                 //设置图片编码格式
                .build();
        return options;
    }
    //相册的加载
    public static DisplayImageOptions getAlbumImgDefOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .cacheInMemory(false)                               //启用内存缓存
                .cacheOnDisk(false)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }
    //Card图片的加载
    public static DisplayImageOptions getCardImgOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .cacheInMemory(false)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
        return options;
    }
    //BannerCard图片的加载
    public static DisplayImageOptions getBannerImgOption(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon_nopic)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_nopic_expression)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .build();
        return options;
    }
}
