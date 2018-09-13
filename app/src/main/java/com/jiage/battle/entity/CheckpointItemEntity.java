package com.jiage.battle.entity;

import java.io.Serializable;

/**
 * 作者：李忻佳
 * 日期：2018/8/23
 * 说明：
 */

public class CheckpointItemEntity implements Serializable {
    private int[][] checkpoint;
    private int heght;
    private int lie;
    private int hang;
    private int screenH;

    public int getScreenH() {
        return screenH;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }

    public int getLie() {
        return lie;
    }

    public void setLie(int lie) {
        this.lie = lie;
    }

    public int getHang() {
        return hang;
    }

    public void setHang(int hang) {
        this.hang = hang;
    }

    public int getHeght() {
        return heght;
    }

    public void setHeght(int heght) {
        this.heght = heght;
    }

    public int[][] getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int[][] checkpoint) {
        this.checkpoint = checkpoint;
    }
}
