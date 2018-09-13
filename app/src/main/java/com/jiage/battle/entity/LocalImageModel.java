package com.jiage.battle.entity;

import android.text.TextUtils;

import com.jiage.battle.adapter.SDAdapter;

import java.io.Serializable;

public class LocalImageModel implements Serializable ,SDAdapter.SDSelectable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String path;

    private String pathLoad;

    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPathLoad() {
        return pathLoad;
    }

    public void setPathLoad(String pathLoad) {
        this.pathLoad = pathLoad;
    }

    public boolean isAddImage() {
        return path == null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        if (!TextUtils.isEmpty(path)) {
            if ("http://".equals(path.substring(0, 7)) || "https://".equals(path.substring(0, 8))) {
                this.pathLoad = path;
            } else {
                this.pathLoad = "file://" + path;
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof LocalImageModel)) {
            return false;
        }

        LocalImageModel otherModel = (LocalImageModel) other;
        String otherPath = otherModel.getPath();
        if (TextUtils.isEmpty(otherPath)) {
            return false;
        }

        if (!otherPath.equals(path)) {
            return false;
        }

        return true;
    }

}
