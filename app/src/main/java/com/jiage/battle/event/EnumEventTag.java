package com.jiage.battle.event;


/**
   使用方法：
 发送：
     SDEventManager.post(EnumEventTag.REFRESH_CONVERSATION.ordinal());
 接收：
     @Override
     public void onEventMainThread(SDBaseEvent event) {
         super.onEventMainThread(event);
         switch (EnumEventTag.valueOf(event.getTagInt())) {
            case OUT_GROUP:
                doView();
            break;
         }
     }
 */
public enum EnumEventTag {

    /**
     * 刷新消息点
     */
    UPDATE_MESSAGE_POINT,
    /**
     * 刷新个人中心
     */
    UPDATA_USERINFO,
    /**
     * IM服务器连接中
     */
    IMSERVER_CONNECTION,
    /**
     * IM服务器断开中
     */
    IMSERVER_DISCONNECTION,
    /**
     * 有新申请好友消息
     */
    NEW_FRIEND,
    /**
     * 删除群聊记录
     */
    DELET_CHAT_RECORD_GROUP,
    /**
     * 清除群聊记录
     */
    CLEAR_CHAT_RECORD_GROUP,
    /**
     * 删除聊天记录
     */
    DELET_CHAT_RECORD,
    /**
     * 清除聊天记录
     */
    CLEAR_CHAT_RECORD,
    /**
     * 发红包
     */
    SEND_RED_ENVELOPE,
    /**
     * 退群
     */
    OUT_GROUP,
    /**
     * 刷新会话
     */
    REFRESH_CONVERSATION,
    /**
     * 刷新聊天
     */
    REFRESH_CHAT,
    /**
     * 手机号修改成功
     */
    UPDATA_PHONE,
    /**
     * 删除好友
     */
    DELET_RRIENDS,
    /**
     * 添加好友
     */
    ADD_RRIENDS,
    /**
     * 上传头像成功
     */
    UPLOAD_USER_HEAD_SUCCESS,
    /**
     * 登录成功
     */
    LOGIN_SUCCESSFUL,
    /**
     * 退出登录
     */
    EXIT_LOGIN,

    /**
     * 刷新我的银行卡页面
     */
    BANK_REFRESH;


    public static EnumEventTag valueOf(int index) {
        if (index >= 0 && index < values().length) {
            return values()[index];
        } else {
            return null;
        }
    }
}
