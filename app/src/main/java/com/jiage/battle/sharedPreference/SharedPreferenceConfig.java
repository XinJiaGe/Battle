package com.jiage.battle.sharedPreference;

/**
 * 作者：李忻佳
 * 日期：2017/11/30/030.
 * 说明：SharedPreference配置
 */

public class SharedPreferenceConfig extends SharedPreference {
    private String ISONE = "isOne";//是否第一次进入app
    private String ARKANOIDBULLETSOUND = "arkanoidBulletSound";//打砖块击中声音
    private String ARKANOIDMAXBULLET = "arkanoidMaxBullet";//打砖块最大子弹数
    private String ARKANOIDFUNCTIONS2 = "arkanoidFunctions2";//打砖块2点获取几率
    private String ARKANOIDFUNCTIONS3 = "arkanoidFunctions3";//打砖块3点获取几率
    private String ARKANOIDFUNCTIONS4 = "arkanoidFunctions4";//打砖块4点获取几率

    public int getIsOne() {
        return getInt(ISONE, 0);
    }
    public void setIsOne(int isOne) {
        setInt(ISONE, isOne);
    }
    public int getArkanoidMaxBullet() {
        return getInt(ARKANOIDMAXBULLET, 100);
    }
    public void setArkanoidMaxBullet(int arkanoidMaxBullet) {
        setInt(ARKANOIDMAXBULLET, arkanoidMaxBullet);
    }
    public boolean isArkanoidBulletSound(){
        return getBoolean(ARKANOIDBULLETSOUND,true);
    }
    public void setArkanoidBulletSound(boolean arkanoidBulletSound){
        setBoolean(ARKANOIDBULLETSOUND,arkanoidBulletSound);
    }
    public int getArkanoidFunctions2() {
        return getInt(ARKANOIDFUNCTIONS2, 3);
    }
    public void setArkanoidFunctions2(int arkanoidFunctions2) {
        setInt(ARKANOIDFUNCTIONS2, arkanoidFunctions2);
    }
    public int getArkanoidFunctions3() {
        return getInt(ARKANOIDFUNCTIONS3, 2);
    }
    public void setArkanoidFunctions3(int arkanoidFunctions3) {
        setInt(ARKANOIDFUNCTIONS3, arkanoidFunctions3);
    }
    public int getArkanoidFunctions4() {
        return getInt(ARKANOIDFUNCTIONS4, 1);
    }
    public void setArkanoidFunctions4(int arkanoidFunctions4) {
        setInt(ARKANOIDFUNCTIONS4, arkanoidFunctions4);
    }
}
