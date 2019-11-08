package com.jiage.battle.activity;

import android.view.View;

import com.jiage.battle.R;
import com.jiage.battle.cocos2d.aircraft3.SickTo;
import com.jiage.battle.cocos2d.dota.Dota;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-11-08
 * 描述：
 */
public class DotaActivity extends BaseActivit{
    private CCGLSurfaceView surfaceView;

    @Override
    public void initBar() {
        setNotTitle(true);
        setSwipeBackEnable(false);
    }
    @Override
    public View bindView() {
        surfaceView = new CCGLSurfaceView(this);
        return surfaceView;
    }
    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initView(View view) {
    }

    private void init(){
        // CCDirector 导演，负责管理和切换场景，负责初始化OPENGL的各项参数
        // CCDirector 采用单例模式，通过sharedDirector()方法获取其唯一的实例
        // attachInView 与OpenGL的SurfaceView进行连接，意思是将surfaceView交给cocos2d来管理
        CCDirector.sharedDirector().attachInView(surfaceView);
        // 设置为横屏显示
        CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
//        // 设置横屏后的分辨率
//        CCDirector.sharedDirector().setScreenSize(1920, 1080);
        // 显示fps，需要添加fps_images.png到assets中，否则会报nullpointer，在CCDirector源码中可以看出
        CCDirector.sharedDirector().setDisplayFPS(true);
        // 设置帧率 60帧每秒，人的肉眼识别帧率为60，所以设置为60最为合理
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
        //获取幕(CCScene)对象
        CCScene scene = CCScene.node();
        //创建GameLayer对象
        CCColorLayer layer = new Dota(ccColor4B.ccc4(255, 255, 255, 255));
        //将Layer放到Scene中
        scene.addChild(layer);//创建一个场景，用来显示游戏界面
        CCDirector.sharedDirector().runWithScene(scene);// 运行场景
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
        CCDirector.sharedDirector().pause();//暂停，游戏切出时候调用
    }

    @Override
    public void onResume() {
        super.onResume();
        CCDirector.sharedDirector().resume();//恢复游戏运行
    }

    @Override
    public void onStop() {
        super.onStop();
        CCDirector.sharedDirector().end();// 结束，游戏退出时调用
    }
}
