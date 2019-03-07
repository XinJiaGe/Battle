package com.jiage.battle.surface.defense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jiage.battle.util.OtherUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/12/18
 * 说明：
 */

public class Enemy {
    private int x,y,mapI,mapIndex;
    private int radiu = 10;
    private int seelp = 6;
    private Direction direction = Direction.RIGHT;
    private boolean isToDirection = false;
    private boolean isDead = false;
    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawCircle(x,y,radiu,paint);
    }

    public void logic(Vector<Map> vcMap){
        if(isToDirection) {
            Map map = vcMap.elementAt(mapI);
            boolean right = map.getJ() < map.getColumn() - 1 && vcMap.elementAt(mapI + 1).getIndex() < mapIndex && vcMap.elementAt(mapI + 1).getText() == 1;//右边还有路
            boolean buttom = map.getI() < map.getRow() - 1 && vcMap.elementAt(mapI + map.getColumn()).getIndex() < mapIndex && vcMap.elementAt(mapI + map.getColumn()).getText() == 1;//下边还有路
            boolean left = map.getJ() > 0 && vcMap.elementAt(mapI - 1).getIndex() < mapIndex && vcMap.elementAt(mapI - 1).getText() == 1;//左边还有路
            boolean top = map.getI() > 0 && vcMap.elementAt(mapI - map.getColumn()).getIndex() < mapIndex && vcMap.elementAt(mapI - map.getColumn()).getText() == 1;//上边还有路

            toDirection(right, buttom, left, top);

        }
        switch (direction) {
            case LEFT:
                x -=seelp;
                break;
            case TOP:
                y -=seelp;
                break;
            case RIGHT:
                x +=seelp;
                break;
            case BOTTOM:
                y +=seelp;
                break;
        }

        for (int i = 0; i < vcMap.size(); i++) {
            Map mapi = vcMap.elementAt(i);
            if(x-radiu>=mapi.getX()&&x+radiu<=mapi.getX()+mapi.getW()&&y-radiu>=mapi.getY()&&y+radiu<=mapi.getY()+mapi.getW()){//判断在地图的位置
                if(mapI!=i) isToDirection = true; else isToDirection = false;
                mapI = i;
                mapi.setIndex(1);
                mapIndex = mapi.getIndex();
                if(i == vcMap.size()-1){
                    isDead = true;
                }
                break;
            }
        }
    }

    public enum Direction{
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    private void toDirection(boolean right,boolean buttom,boolean left,boolean top){
        switch (direction) {
            case RIGHT:
                if(top&&!right&&!buttom){
                    direction = Direction.TOP;
                }
                if(!top&&right&&!buttom){
                    direction = Direction.RIGHT;
                }
                if(!top&&!right&&buttom){
                    direction = Direction.BOTTOM;
                }
                if(top&&right&&!buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.TOP; else direction = Direction.RIGHT;
                }
                if(top&&!right&&buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.TOP; else direction = Direction.BOTTOM;
                }
                if(!top&&right&&buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.RIGHT; else direction = Direction.BOTTOM;
                }
                if(top&&right&&buttom){
                    int i = OtherUtil.getRandom(1,3);
                    if(i == 1)
                        direction = Direction.TOP;
                    else if(i == 2)
                        direction = Direction.RIGHT;
                    else
                        direction = Direction.BOTTOM;
                }
                break;
            case BOTTOM:
                if(left&&!right&&!buttom){
                    direction = Direction.LEFT;
                }
                if(!left&&right&&!buttom){
                    direction = Direction.RIGHT;
                }
                if(!left&&!right&&buttom){
                    direction = Direction.BOTTOM;
                }
                if(left&&right&&!buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.LEFT; else direction = Direction.RIGHT;
                }
                if(left&&!right&&buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.LEFT; else direction = Direction.BOTTOM;
                }
                if(!left&&right&&buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.RIGHT; else direction = Direction.BOTTOM;
                }
                if(left&&right&&buttom){
                    int i = OtherUtil.getRandom(1,3);
                    if(i == 1)
                        direction = Direction.LEFT;
                    else if(i == 2)
                        direction = Direction.RIGHT;
                    else
                        direction = Direction.BOTTOM;
                }
                break;
            case LEFT:
                if(top&&!left&&!buttom){
                    direction = Direction.TOP;
                }
                if(!top&&left&&!buttom){
                    direction = Direction.LEFT;
                }
                if(!top&&!left&&buttom){
                    direction = Direction.BOTTOM;
                }
                if(top&&left&&!buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.TOP; else direction = Direction.LEFT;
                }
                if(top&&!left&&buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.TOP; else direction = Direction.BOTTOM;
                }
                if(!top&&left&&buttom){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.LEFT; else direction = Direction.BOTTOM;
                }
                if(top&&left&&buttom){
                    int i = OtherUtil.getRandom(1,3);
                    if(i == 1)
                        direction = Direction.TOP;
                    else if(i == 2)
                        direction = Direction.LEFT;
                    else
                        direction = Direction.BOTTOM;
                }
                break;
            case TOP:
                if(left&&!right&&!top){
                    direction = Direction.LEFT;
                }
                if(!left&&right&&!top){
                    direction = Direction.RIGHT;
                }
                if(!left&&!right&&top){
                    direction = Direction.TOP;
                }
                if(left&&right&&!top){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.LEFT; else direction = Direction.RIGHT;
                }
                if(left&&!right&&top){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.LEFT; else direction = Direction.TOP;
                }
                if(!left&&right&&top){
                    if(OtherUtil.getRandom(1,2) == 1) direction = Direction.RIGHT; else direction = Direction.TOP;
                }
                if(left&&right&&top){
                    int i = OtherUtil.getRandom(1,3);
                    if(i == 1)
                        direction = Direction.LEFT;
                    else if(i == 2)
                        direction = Direction.RIGHT;
                    else
                        direction = Direction.TOP;
                }
                break;
        }
    }

    public int getMapI() {
        return mapI;
    }

    public boolean isDead() {
        return isDead;
    }
}
