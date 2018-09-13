package com.jiage.battle.dao;

import com.jiage.battle.common.DbManagerX;
import com.jiage.battle.entity.UserEntity;
import com.jiage.battle.sharedPreference.SharedPreference;

import org.xutils.db.sqlite.WhereBuilder;

/**
 * 作者：李忻佳
 * 时间：2018/3/23/023.
 * 描述：用户Dao
 */

public class UserDbDao {
    private static UserDbDao mInstance = null;

    private UserDbDao() {
    }

    public static UserDbDao getInstance() {
        if (mInstance == null) {
            synchronized (UserDbDao.class) {
                if (mInstance == null) {
                    mInstance = new UserDbDao();
                }
            }
        }
        return mInstance;
    }
    public UserEntity query(String id) {
        if (id != null&&!id.equals("")) {
            try {
                UserEntity user = DbManagerX.getDb().selector(UserEntity.class).where("agent_no", "=", id).findFirst();
                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public UserEntity queryPhone(String phone) {
        if (phone != null&&!phone.equals("")) {
            try {
                UserEntity user = DbManagerX.getDb().selector(UserEntity.class).where("phone", "=", phone).findFirst();
                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private boolean insert(UserEntity model) {
        if (model != null) {
            try {
                DbManagerX.getDb().save(model);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean insertOrUpdate(UserEntity model) {
        if (model != null) {
            if(delete(model.getAgent_no())){
                return insert(model);
            }else{
                return false;
            }
        } else {
            return false;
        }
    }
    public boolean delete(String id) {
        if (id != null&&!id.equals("")) {
            try {
                DbManagerX.getDb().delete(UserEntity.class, WhereBuilder.b("agent_no", "=", id));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
