package com.jdhui.net;

import java.io.UnsupportedEncodingException;
import java.util.Observable;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdhui.ApplicationConsts;
import com.jdhui.bean.ResultUserLoginBean;
import com.jdhui.http.AsyncHttpClient;
import com.jdhui.http.AsyncHttpResponseHandler;
import com.jdhui.http.PersistentCookieStore;
import com.jdhui.mould.HtmlLoadUtil;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

public class UserLogin extends Observable {
	private static UserLogin instance;
	private static final int MODE_PRIVATE = 0;
	private UserLogin() {

	}

	public void notifyObservers(Object data) {
		this.setChanged();
		super.notifyObservers(data);
	}

	public static UserLogin getInstance() {
		if (instance == null) {
			instance = new UserLogin();
		}
		return instance;
	}

	public void userlogining(final Context context, final String username,
			final String password, String token) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		// asyncHttpClient.addHeader("x-client-version",
		// SystemInfo.sVersionName);
		// asyncHttpClient.addHeader("x-platform", "android");
		// asyncHttpClient.addHeader("x-machine-id",
		// SystemInfo.getImei(context));
		// asyncHttpClient.addHeader("client-tgt", "");
		
		asyncHttpClient.addHeader("accept", "application/json");
		
//		asyncHttpClient.addHeader("content-Type", "application/json");
		
		asyncHttpClient.setTimeout(6000);
		
		String STORE_NAME = "PushTestReceiver"; 
	    SharedPreferences settings =context.getSharedPreferences(STORE_NAME, MODE_PRIVATE);
	    String appid = settings.getString("channelId","");
		
	    
	    
		String data = HtmlLoadUtil.frontLogin(username, password, "",appid);
//		String data = HtmlLoadUtil.frontLogin(username, password, "");
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		asyncHttpClient.setCookieStore(myCookieStore);
		HttpEntity entity = null;
		try {
			entity = new StringEntity(data);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		asyncHttpClient.post(context, ApplicationConsts.URL_LOGIN, entity,
				"application/json", new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String content) {
						super.onSuccess(content);

					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						for (int i = 0; i < headers.length; i++) {
							if (headers[i].getName().equals("Set-Cookie")) {
								String id = headers[i].getValue();
								try {
									PreferenceUtil.setCookie(DESUtil
											.encrypt(id));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						String result = null;
						try {
							result = DESUtil.decrypt(content.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}

						Gson json = new Gson();
						ResultUserLoginBean b = json.fromJson(
								result.toString(), ResultUserLoginBean.class);
						if (b.getData() != null) {
							if (Boolean.parseBoolean(b.getData().getFlag())) {
								try {
									PreferenceUtil.setAutoLoginAccount(DESUtil
											.encrypt(username));
									PreferenceUtil.setAutoLoginPwd(DESUtil
											.encrypt(password));
									PreferenceUtil.setPhone(DESUtil.encrypt(b
											.getData().getMobile()));
									PreferenceUtil.setUserId(DESUtil.encrypt(b
											.getData().getUserId()));
									PreferenceUtil.setUserNickName(b.getData()
											.getNickName());
//									PreferenceUtil.setToken(DESUtil.encrypt(b.getData()
//											.getToken()));
									PreferenceUtil.setLogin(true);
									PreferenceUtil.setGestureChose(true);

									PreferenceUtil.setIsAnswer(Boolean.parseBoolean(b.getData().getQuestionnaireRecordFlag()));
									PreferenceUtil.setIsInvestor(Boolean.parseBoolean(b.getData().getQualifiedInvestorFlag()));
									PreferenceUtil.setTotalAmount(Boolean.parseBoolean(b.getData().getTotalAmountFlag()));

								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								Toast.makeText(context, b.getData().getMessage(),
										Toast.LENGTH_LONG).show();
							}
							
						} else {
							
							PreferenceUtil.setAutoLoginAccount("");
							PreferenceUtil.setAutoLoginPwd("");
							PreferenceUtil.setLogin(false);
							PreferenceUtil.setPhone("");
							PreferenceUtil.setUserId("");
							PreferenceUtil.setUserNickName("");
							PreferenceUtil.setCookie("");
						}
						notifyObservers(b.getData());
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						super.onFailure(error);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						ResultUserLoginBean b = new ResultUserLoginBean();
						b.setMsg("登陆失败");
//						Toast.makeText(context, content, Toast.LENGTH_LONG)
//								.show();
						Toast.makeText(context, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();//加载失败，请确认网络通畅
						PreferenceUtil.setAutoLoginAccount("");
						PreferenceUtil.setAutoLoginPwd("");
						PreferenceUtil.setLogin(false);
						PreferenceUtil.setPhone("");
						PreferenceUtil.setUserId("");
						PreferenceUtil.setUserNickName("");
						PreferenceUtil.setCookie("");
						notifyObservers(b.getData());
					}

				});
	}
}
