package com.jiage.battle.surface.defense;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/12/21
 * 说明：路线集合
 */

public class Route {
    private int mapIndex;
    private int identification;
    private Enemy.Direction direction;
    private List<routeItem> item;

    public static class routeItem{
        private int x;
        private int y;
        private int i;
        private int j;
        private int text;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getText() {
            return text;
        }

        public void setText(int text) {
            this.text = text;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }
    }

    public List<routeItem> getItem() {
        return item;
    }

    public void setItem(List<routeItem> item) {
        this.item = item;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }

    public int getIdentification() {
        return identification;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public Enemy.Direction getDirection() {
        return direction;
    }

    public void setDirection(Enemy.Direction direction) {
        this.direction = direction;
    }
}
