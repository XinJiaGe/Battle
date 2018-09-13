package com.jiage.battle.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * 文件操作util
 */
public class SDFileUtil {
    /**
     * 判断文件是否存在，不存在就创建
     *
     * @param file
     * @return
     */
    public static void isFileExists(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断文件夹是否存在，不存在就创建
     *
     * @param file
     * @return
     */
    public static void isFileJiaExists(File file) {
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }

    /**
     * sd卡是否存在
     *
     * @return
     */
    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得sd卡根目录file对象引用
     *
     * @return
     */
    public static File getSdCardRootFile() {
        if (isSdcardExist()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    public static boolean copy(String oldFilePath, String newFilePath) {
        if (!TextUtils.isEmpty(oldFilePath) && !TextUtils.isEmpty(newFilePath)) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel channelIn = null;
            FileChannel channelOut = null;
            try {
                File oldFile = new File(oldFilePath);
                File newFile = new File(newFilePath);
                if (newFile.exists()) {
                    newFile.delete();
                }
                newFile.createNewFile();
                fis = new FileInputStream(oldFile);
                fos = new FileOutputStream(newFile);
                channelIn = fis.getChannel();
                channelOut = fos.getChannel();
                channelIn.transferTo(0, channelIn.size(), channelOut);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IoUtils.closeSilently(fis);
                IoUtils.closeSilently(channelIn);
                IoUtils.closeSilently(fos);
                IoUtils.closeSilently(channelOut);
            }
        }
        return false;
    }

    /**
     * 往文件里加内容
     *
     * @param content
     * @param filePath
     * @return
     */
    public static boolean saveStringToFile(String content, String filePath) {
        if (content == null || TextUtils.isEmpty(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            PrintWriter pw = null;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                if (file.exists()) {
                    pw = new PrintWriter(file);
                    // pw = new PrintWriter(new BufferedWriter(
                    // new FileWriter(file)));
                    pw.write(content);
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            } finally {
                IoUtils.closeSilently(pw);
            }

        }
    }

    /**
     * 读取文件里内容
     *
     * @param filePath
     * @return
     */
    public static String readStringFromFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (file.exists()) {
            try {

                StringBuffer strBuffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String strLine = "";
                while ((strLine = reader.readLine()) != null) {
                    strBuffer.append(strLine);
                }
                return strBuffer.toString();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    public static long getAFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return size;
    }

    /**
     * 获得文件夹下所有文件的大小
     *
     * @param dirFile
     * @return
     */
    public static long getFileSize(File dirFile) {
        long size = 0;
        File[] flist = dirFile.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param dirPath
     * @return
     */
    public static long getFileSize(String dirPath) {
        File dirFile = new File(dirPath);
        if (dirFile.exists() && dirFile.isDirectory()) {
            return getFileSize(dirFile);
        } else {
            return 0;
        }
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                return file.delete();
            } else {
                File[] files = file.listFiles();
                boolean flag = true;
                if (files == null) {
                    flag = false;
                } else {
                    for (int i = 0; i < files.length; i++) {
                        if (!deleteFile(files[i])) {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    return file.delete();
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 格式化文件大小
     *
     * @param fileLength
     * @return
     */
    public static String formatFileSize(long fileLength) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLength <= 0) {
            fileSizeString = "0.00B";
        } else if (fileLength < 1024) {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576) {
            fileSizeString = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824) {
            fileSizeString = df.format((double) fileLength / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileLength / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获取某个文件目录下的文件夹数量
     *
     * @param dirFile
     * @return
     */
    public static long getDirFileNumber(File dirFile) {
        long size = 0;
        File flist[] = dirFile.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getDirFileNumber(flist[i]);
                size--;
            }
        }
        return size;
    }

    public static String getExtString(File file) {
        String extString = null;
        if (file != null && file.exists()) {
            String path = file.getAbsolutePath();
            int dotIndex = path.lastIndexOf(".");
            if (dotIndex >= 0) {
                extString = path.substring(dotIndex + 1);
            }
        }
        return extString;
    }

    public static String getMimeType(File file) {
        String mime = null;
        String extString = getExtString(file);
        mime = MimeTypeUtil.getMimeType(extString);
        return mime;
    }
}
