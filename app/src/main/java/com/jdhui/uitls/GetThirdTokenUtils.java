package com.jdhui.uitls;

import android.content.Context;

/**
 * 根据第三方的ID获取Token,以用来登录
 * 
 * @author 131395
 */
public class GetThirdTokenUtils {

	public interface GetTokenListener {
		public void getToken(String token);
	};

	public interface GetTokenListener2 {
		public void getToken(Context c, String token);
	};

	/**
	 * 检查用户是否已经注册
	 * 
	 * @param l
	 * 
	 *            public static void CheckUserIsReg(String openId, final
	 *            GetTokenListener l) {
	 * 
	 *            int qqOrsina =
	 *            PreferenceUtil.getUserIdPrefer().getInt(PreferenceUtil
	 *            .THIRD_SOURCE, 0);
	 * 
	 *            //获取用户信息 ThirdLoginRequester.getUserInfo(openId, qqOrsina, new
	 *            OnRequestListener() {
	 * @Override public void onRequestFinished(BaseParams params) {
	 * 
	 *           if (params.result == null) { l.getToken(null); return; }
	 *           ThirdRegInfo mRegInfo = (ThirdRegInfo) params.result; if
	 *           ("-9".equals(mRegInfo.resultCode)) { //失败 l.getToken(null); }
	 *           else { //成功 l.getToken(mRegInfo.value); } } }); }
	 */
	/**
	 * 检查用户是否已经注册
	 * 
	 * @param l
	 * 
	 *            public static void CheckUserIsReg(final Context c, String
	 *            openId, final GetTokenListener2 l) {
	 * 
	 *            int qqOrsina =
	 *            PreferenceUtil.getUserIdPrefer().getInt(PreferenceUtil
	 *            .THIRD_SOURCE, 0);
	 * 
	 *            //获取用户信息 ThirdLoginRequester.getUserInfo(openId, qqOrsina, new
	 *            OnRequestListener() {
	 * @Override public void onRequestFinished(BaseParams params) {
	 * 
	 *           if (params.result == null) { l.getToken(c, null); return; }
	 *           ThirdRegInfo mRegInfo = (ThirdRegInfo) params.result; if
	 *           ("-9".equals(mRegInfo.resultCode)) { //失败 l.getToken(c, null);
	 *           } else { //成功 l.getToken(c, mRegInfo.value); } } }); }
	 */
}
