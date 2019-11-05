package com.jiage.battle.cocos2d

import org.cocos2d.opengl.CCDrawingPrimitives
import org.cocos2d.types.CGPoint
import javax.microedition.khronos.opengles.GL10


/**
 * 作者：忻佳
 * 日期：2019-11-05
 * 描述：
 */
object DrawUtil {
    @JvmStatic
    fun circle(gl: GL10, center: CGPoint, r: Float, a: Float = 255f, segments: Int = 4, drawLineToCenter: Boolean = false) {
        CCDrawingPrimitives.ccDrawCircle(gl,center,r,a,segments,drawLineToCenter);
    }
}
