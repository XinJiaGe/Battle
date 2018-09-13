package com.jiage.battle.common;

import com.jiage.battle.MyApplication;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

/**
 * 作者：李忻佳
 * 时间：2017/11/28/
 * 说明：xutils数据库管理类
 */
public class DbManagerX {
    /**
     * 数据库名
     */
    public static String dbName = "hongbao.db";
    /**
     * 数据库版本
     */
    public static String dbVersion = MyApplication.getApplication().DbVersion;

    public static DbManager getDb() {
        return x.getDb(configDefault);
    }

    /**
     * 默认数据库配置
     */
    private static final DbManager.DaoConfig configDefault = new DbManager.DaoConfig().setDbName(dbName).setDbVersion(Integer.parseInt(dbVersion))
            .setAllowTransaction(true).setDbOpenListener(new org.xutils.DbManager.DbOpenListener() {
                                                             @Override
                                                             public void onDbOpened(org.xutils.DbManager db) {
                                                                 // 开启WAL, 对写入加速提升巨大
                                                                 db.getDatabase().enableWriteAheadLogging();
                                                             }
                                                         }
            ).setTableCreateListener(new org.xutils.DbManager.TableCreateListener() {
                @Override
                public void onTableCreated(org.xutils.DbManager db, TableEntity<?> table) {

                }

            }).setDbUpgradeListener(new org.xutils.DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion) {

                }

            });
}
