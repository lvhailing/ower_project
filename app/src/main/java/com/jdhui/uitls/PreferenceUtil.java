package com.jdhui.uitls;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class PreferenceUtil {

    private static Context mContext;
    private static SharedPreferences user;
    private static SharedPreferences setting;
    public static final String CLIENTTGT = "CLIENTTGT";
    public static final String COOKIE = "COOKIE";

    public static void initialize(Context context) {
        mContext = context;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static SharedPreferences getUserSharedPreferences() {
        if (user == null) {
            user = mContext.getSharedPreferences("user_pre", Context.MODE_PRIVATE);
        }
        return user;
    }

    /**
     * 获取设置信息
     *
     * @return
     */
    public static SharedPreferences getSettingSharedPreferences() {
        if (setting == null) {
            setting = mContext.getSharedPreferences("setting_pre",
                    Context.MODE_PRIVATE);
        }
        return setting;
    }

    /**
     * 是否首次进入应用
     *
     * @return
     */
    public static boolean isFirst() {
        return getUserSharedPreferences().getBoolean("ISFIRST", true);
    }

    /**
     * 设置是否首次进入应用
     *
     * @param isfirst
     */
    public static void setFirst(boolean isfirst) {
        getUserSharedPreferences().edit().putBoolean("ISFIRST", false).commit();
    }

    /**
     * 是否首次登陆应用
     *
     * @return
     */
    public static boolean isFirstLogin() {
        return getUserSharedPreferences().getBoolean(getUserId(), true);
    }

    /**
     * 设置是否首次登陆
     *
     * @param isfirst
     */
    public static void setFirstLogin(boolean isfirst) {
        getUserSharedPreferences().edit().putBoolean(getUserId(), isfirst).commit();
    }

    /**
     * 设置手势密码
     *
     * @param pwd
     */
    public static void setGesturePwd(String pwd) {
//        getSettingSharedPreferences().edit().putString("gesture", pwd).commit();
        getSettingSharedPreferences().edit().putString(getUserId(), pwd).commit();
    }

    /**
     * 获取手势密码
     *
     * @return
     */
    public static String getGesturePwd() {
//        return getSettingSharedPreferences().getString("gesture", "");
        return getSettingSharedPreferences().getString(getUserId(), "");
    }

    /**
     * 是否接收推送
     *
     * @return
     */
    public static boolean isPushEnable() {
        return getSettingSharedPreferences().getBoolean("push_on", true);
    }

    /**
     * 设置是否接收推送
     *
     * @param is
     * @return
     */
    public static boolean setPushEnable(boolean is) {
        return getSettingSharedPreferences().edit().putBoolean("push_on", is)
                .commit();
    }

    /**
     * 判断是否开启防骚扰模式
     *
     * @return
     */
    public static boolean isEnable() {
        boolean r = false;
        if (isPushEnable()) {
            int h = new Date().getHours();
            boolean antiTime = getIsAntiHarassment();
            if (!antiTime || (antiTime && h < 22 && h > 8)) {// 防骚扰
                r = true;
            }
        }
        return r;
    }

    /**
     * 是否打开防骚扰
     *
     * @return boolean
     */
    public static boolean getIsAntiHarassment() {
        return getSettingSharedPreferences().getBoolean("isAntiHarassment",
                true);
    }

    /**
     * 从SharedPreference中获取存入的TGT 默认传空
     *
     * @return
     */
    public static String getClienttgt() {
        return getUserSharedPreferences().getString(CLIENTTGT, "");
    }

    public static void setClienttgt(String s) {
        getUserSharedPreferences().edit().putString(CLIENTTGT, s).commit();
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static String getUserId() {
        return getUserSharedPreferences().getString("userId", "");
    }

    /**
     * 设置用户ID
     *
     * @param userId
     */
    public static void setUserId(String userId) {
        getUserSharedPreferences().edit().putString("userId", userId).commit();
    }

    /**
     * 获取用户信息ID
     *
     * @return
     */
    public static String getUserInfoId() {
        return getUserSharedPreferences().getString("userInfoId", "");
    }

    /**
     * 设置用户信息ID
     *
     * @param userInfoId
     */
    public static void setUserInfoId(String userInfoId) {
        getUserSharedPreferences().edit().putString("userInfoId", userInfoId).commit();
    }

    /**
     * 设置用户昵称
     *
     * @param nickName
     */
    public static void setUserNickName(String nickName) {
        getUserSharedPreferences().edit().putString("userNickName", nickName).commit();
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public static String getUserNickName() {
        return getUserSharedPreferences().getString("userNickName", "");
    }


    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public static void setUserRealName(String realName) {
        getUserSharedPreferences().edit().putString("userRealName", realName)
                .commit();
    }

    /**
     * 获取真实姓名
     *
     * @return
     */
    public static String getUserRealName() {
        return getUserSharedPreferences().getString("userRealName", "");
    }


    /**
     * 是否已经登陆
     *
     * @return
     */
    public static boolean isLogin() {
        return getUserSharedPreferences().getBoolean("isLogin", false);
    }

    /**
     * 设置是否登陆
     *
     * @param isLogin
     */
    public static void setLogin(boolean isLogin) {
        getUserSharedPreferences().edit().putBoolean("isLogin", isLogin).commit();
    }

    /**
     * 设置用户账户名
     *
     * @param name
     */
    public static void setAutoLoginAccount(String name) {
        getUserSharedPreferences().edit().putString("account", name).commit();
    }

    /**
     * 得到用户账户名
     *
     * @return
     */
    public static String getAutoLoginAccount() {
        return getUserSharedPreferences().getString("account", "");
    }

    /**
     * 设置用户密码
     *
     * @param pwd 用户密码
     */
    public static void setAutoLoginPwd(String pwd) {
        getUserSharedPreferences().edit().putString("pwd", pwd).commit();
    }

    /**
     * 得到用户密码
     *
     * @return
     */
    public static String getAutoLoginPwd() {
        return getUserSharedPreferences().getString("pwd", "");
    }

    /**
     * 设置cookie
     *
     * @param cookie
     */
    public static void setCookie(String cookie) {
        getUserSharedPreferences().edit().putString("cookie", cookie).commit();
    }

    /**
     * 获得cookie
     *
     * @return
     */
    public static String getCookie() {
        return getUserSharedPreferences().getString("cookie", "");
    }

    /**
     * 设置用户注册手机号
     *
     * @param phone
     */
    public static void setPhone(String phone) {
        getUserSharedPreferences().edit().putString("phone", phone).commit();
    }

    /**
     * 获取用户注册手机号
     *
     * @return
     */
    public static String getPhone() {
        return getUserSharedPreferences().getString("phone", "");
    }

    /**
     * 是否接收消息
     *
     * @return
     */
    public static boolean isReceviceMessage() {
        return getUserSharedPreferences().getBoolean("getMsg", true);
    }

    /**
     * 设置是否接收消息
     *
     * @param is
     */
    public static void setReceviceMessage(boolean is) {
        getUserSharedPreferences().edit().putBoolean("getMsg", is).commit();
    }

    /**
     * 设置是否开启手势密码
     *
     * @param is
     */
    public static void setGestureChose(boolean is) {
        getUserSharedPreferences().edit().putBoolean("getGestureMsg", is).commit();
    }

    /**
     * 是否开启手势密码
     *
     * @return
     */
    public static boolean isGestureChose() {
        return getUserSharedPreferences().getBoolean("getGestureMsg", true);
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        return getUserSharedPreferences().getString("token", "");
    }

    /**
     * 设置token
     *
     * @param userId
     */
    public static void setToken(String userId) {
        getUserSharedPreferences().edit().putString("token", userId).commit();
    }

    /**
     * 获取资产页面是否展示数据参数
     *
     * @return 返回数据
     */
    public static Boolean getIsShowAsset() {
        return getUserSharedPreferences().getBoolean("isShowAsset", true);
    }

    /**
     * 设置资产页面是否展示数据参数
     *
     * @param isShowAsset 是否展示资产数据
     */
    public static void setIsShowAsset(Boolean isShowAsset) {

        getUserSharedPreferences().edit().putBoolean("isShowAsset", isShowAsset).commit();
    }

    /**
     * 设置问卷调查是否答题
     *
     * @param isAnswer 是否答题
     */
    public static void setIsAnswer(Boolean isAnswer) {

        getUserSharedPreferences().edit().putBoolean("isAnswer", isAnswer).commit();
    }

    /**
     * 获取问卷调查是否答题
     */
    public static Boolean getIsAnswer() {
        return getUserSharedPreferences().getBoolean("isAnswer", true);
    }

    /**
     * 设置是否做过合格投资者判定
     *
     * @param isInvestor 是否做过合格投资者判定
     */
    public static void setIsInvestor(Boolean isInvestor) {

        getUserSharedPreferences().edit().putBoolean("isInvestor", isInvestor).commit();
    }

    /**
     * 获取是否做过合格投资者判定
     */
    public static Boolean getIsInvestor() {
        return getUserSharedPreferences().getBoolean("isInvestor", true);
    }

    /**
     * 设置账户资产是否大于300万
     *
     * @param isTotalAmount 账户资产是否大于300万
     */
    public static void setTotalAmount(Boolean isTotalAmount) {

        getUserSharedPreferences().edit().putBoolean("isTotalAmount", isTotalAmount).commit();
    }

    /**
     * 获取账户资产是否大于300万
     */
    public static Boolean getTotalAmount() {
        return getUserSharedPreferences().getBoolean("isTotalAmount", true);
    }
}

