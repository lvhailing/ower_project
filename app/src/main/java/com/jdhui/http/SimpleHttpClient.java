package com.jdhui.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.GetThirdTokenUtils.GetTokenListener2;
import com.jdhui.uitls.PreferenceUtil;

public class SimpleHttpClient extends AsyncHttpClient {
	public static final int RESULT_BYTE = 0;
	public static final int RESULT_STRING = 1;
	public static final int RESULT_HTTPENTITY = 2;

	private int responseCode = -1;
	/*
	 * as this is a synchronous request this is just a helping mechanism to pass
	 * the result back to this method. Therefore the result object has to be a
	 * field to be accessible
	 */
	protected Object result;
	protected Header[] mHeaders;
	protected int mType = -1;

	protected AsyncHttpResponseHandler responseHandler = null;
	protected Throwable exception = null;

	private Intent it;

	private Context c;

	public SimpleHttpClient(int resultType) {
		mType = resultType;
		if (resultType == RESULT_BYTE) {
			responseHandler = new BinaryHttpResponseHandler(null) {
				void sendResponseMessage(HttpResponse response) {
					mHeaders = response.getAllHeaders();
					super.sendResponseMessage(response);
				}

				protected void sendSuccessMessage(int statusCode,
						byte[] responseBody) {
					responseCode = statusCode;
					result = responseBody;
				}

				@Override
				protected void sendFailureMessage(Throwable e,
						byte[] responseBody) {
					exception = e;
				}
			};
		} else if (resultType == RESULT_HTTPENTITY) {
			responseHandler = new HttpEntityResponseHandler() {
				public void onSuccess(int statusCode, Header[] headers,
						HttpEntity entity) {
					mHeaders = headers;
					result = entity;
				}

				public void onFailure(Throwable e, HttpEntity entity) {
					exception = e;
				}

			};
		} else {
			responseHandler = new AsyncHttpResponseHandler() {

				protected void sendSuccessMessage(int statusCode,
						Header[] headers, String responseBody) {
					mHeaders = headers;
					responseCode = statusCode;
					result = responseBody;
				}

				protected void sendFailureMessage(Throwable e,
						String responseBody) {
					exception = e;
				}

				protected void sendFailureMessage(Throwable e,
						byte[] responseBody) {
					exception = e;
				}

				protected void sendStartMessage() {
				}

				protected void sendFinishMessage() {
				}
			};
		}
	}

	public SimpleHttpClient(final Context c, int resultType) {
		this.c = c;
		mType = resultType;
		final String oldTgt = PreferenceUtil.getClienttgt().trim();
		addHttpHeader(oldTgt);

		if (resultType == RESULT_BYTE) {
			responseHandler = new BinaryHttpResponseHandler(null) {
				void sendResponseMessage(HttpResponse response) {
					mHeaders = response.getAllHeaders();
					super.sendResponseMessage(response);
				}

				protected void sendSuccessMessage(int statusCode,
						byte[] responseBody) {
					responseCode = statusCode;
					result = responseBody;
				}

				@Override
				protected void sendFailureMessage(Throwable e,
						byte[] responseBody) {
					exception = e;
				}
			};
		} else if (resultType == RESULT_HTTPENTITY) {
			responseHandler = new HttpEntityResponseHandler() {
				public void onSuccess(int statusCode, Header[] headers,
						HttpEntity entity) {
					mHeaders = headers;
					result = entity;
				}

				public void onFailure(Throwable e, HttpEntity entity) {
					exception = e;
				}

			};
		} else {
			String cookie = null;
			try {
				String activity = c.getClass().getSimpleName();
				if (activity.equals("SignupActivity")) {
					
					cookie = PreferenceUtil.getCookie();
					
				} else {

					cookie = DESUtil.decrypt(PreferenceUtil.getCookie());
				}

				this.addHeader("Cookie", cookie);
				PersistentCookieStore myCookieStore = new PersistentCookieStore(
						c);
				this.setCookieStore(myCookieStore);
				BasicClientCookie newCookie = new BasicClientCookie(
						"JSESSIONID", cookie);
				myCookieStore.addCookie(newCookie);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.addHeader("cookies", cookie);
			responseHandler = new AsyncHttpResponseHandler() {

				protected void sendSuccessMessage(int statusCode,
						Header[] headers, String responseBody) {
					mHeaders = headers;

//					for (int i = 0; i < headers.length; i++) {
//						if (headers[i].getName().equals("SET-COOKIE")) {
//							String id = headers[i].getValue();
//							try {
////								PreferenceUtil.setCookie(DESUtil
////										.encrypt(id));
//
//								String ids[] = id.split(";");
//								String sessionid = ids[0];
//								String sessionid_this = DESUtil.decrypt(PreferenceUtil.getCookie());
//								if(!TextUtils.isEmpty(sessionid_this)){
//									if(!sessionid.equals(sessionid_this)){
//										Intent i_login = new Intent(c, LoginActivity.class);
//										PreferenceUtil.setAutoLoginAccount("");
//										PreferenceUtil.setAutoLoginPwd("");
//										PreferenceUtil.setLogin(false);
//										PreferenceUtil.setFirstLogin(true);
//										PreferenceUtil.setPhone("");
//										PreferenceUtil.setUserId("");
//										PreferenceUtil.setUserNickName("");
//
//										PreferenceUtil.setIsShowAsset(true);
//										// i.putExtra("result", "exit");
//										// setResult(9, i);
////										Toast.makeText(c, "服务器异常，请重新登录", Toast.LENGTH_LONG).show();
//										i_login.putExtra("tomain", "23");
//										c.startActivity(i_login);
//									}
//								}
//
////								Toast.makeText(c,sessionid,Toast.LENGTH_SHORT).show();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}

					// for (Header h : mHeaders) {
					// if (h.getName().equals("x-code")) {
					// if (h.getValue().equals("-11")) {
					// if (SystemInfo.DEBUG) {
					// Log.i("SimpleHttpClient----",
					// "x-code的值是-11，需要判断一下");
					// }
					//
					// boolean isSend = Userlanding
					// .isSendBroadcast(oldTgt);
					// if (isSend) {
					//
					// responseCode = statusCode;
					// result = null;
					// if (c != null
					// && PreferenceUtil.getCmsId() > 0) {
					// /**
					// * 清理用户ID 停用服务
					// */
					// // PreferenceUtil.clearUserIds();
					// // it = new Intent(
					// // "com.vjinke.service.MessageService");
					// // // it.setClass(c,
					// // MessageService.class);
					// //
					// it.setAction(MessageService.ACTION_SHUT);
					// // c.startService(it);
					// // // 发送广播通知 MainActivity 进行显示登录对话框
					// // c.sendBroadcast(new Intent(
					// //
					// com.vjinke.downloader.util.Constants.ACTION_RELOGIN));
					// }
					// return;
					// }
					// } else if (h.getValue().equals(String.valueOf(-12)))
					// {// 如果x-code的值为-12，说明TGT过期，则自动登录
					// if (SystemInfo.DEBUG) {
					// Log.i("SimpleHttpClient----",
					// "x-code的值是-12直接自动登录");
					// }
					// // String openId = PreferenceUtil
					// // .getUserIdPrefer().getString(
					// // PreferenceUtil.OPENID, "");
					// // if (!TextUtils.isEmpty(openId)) {
					// // GetThirdTokenUtils.CheckUserIsReg(c,
					// // openId, tokenListener2);
					// // return;
					// // }
					// // NameAndPassword m = LogonInfoManager
					// // .getInstance().getUser();
					// // Userlanding.getInstance().userlanding(c,
					// // m.getUserName(), m.getPassword(), "");
					// // m = null;
					// // return;
					// }
					// }
					// }

					responseCode = statusCode;
					result = responseBody;
					try {
						if (result != null) {
							String str = (String) result;
							String data = DESUtil.decrypt(str);
							Log.i("aaa", data);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				protected void sendFailureMessage(Throwable e,
						String responseBody) {
					exception = e;
				}

				protected void sendFailureMessage(Throwable e,
						byte[] responseBody) {
					exception = e;
				}

				protected void sendStartMessage() {
				}

				protected void sendFinishMessage() {
				}
			};
		}
	}

	private GetTokenListener2 tokenListener2 = new GetTokenListener2() {

		@Override
		public void getToken(Context c, String token) {
			if (token != null) {
				// Userlanding.getInstance().userlanding(c, "name", "password",
				// token);
			}
		}
	};

	private void addHttpHeader(String oldTgt) {
		// int uid = Integer.parseInt(PreferenceUtil.getUserId());
		// String imei = SystemInfo.sDeviceId;
		// String cookie = PreferenceUtil.getCookie();
		// if (uid > 0) {
		// addHeader("x-uid", String.valueOf(uid));
		// }
		// addHeader("x-client-version", SystemInfo.sVersionName);
		// addHeader("x-platform", "android");
		// addHeader("x-machine-id", imei);
		// // 父类中已经添加过了
		// addHeader("client-tgt", oldTgt);
		// if (!TextUtils.isEmpty(cookie)) {
		// addHeader("x-cookie", cookie);
		// }
		// addHeader("Content-Type","application/xml");
		// addHeader("Content-Type",
		// "application/x-www-form-urlencoded; charset=utf-8");
		// header("Content-type: text/html; charset=utf-8");
	}

	/**
	 * @return the response code for the last request, might be usefull
	 *         sometimes
	 */
	public int getResponseCode() {
		return responseCode;
	}

	public Header[] getHeaders() {
		return mHeaders;
	}

	public Object getResult() {
		return result;
	}

	// Private stuff
	@Override
	protected void sendRequest(DefaultHttpClient client,
			HttpContext httpContext, HttpUriRequest uriRequest,
			String contentType, AsyncHttpResponseHandler responseHandler,
			Context context) {
		if (contentType != null) {
			uriRequest.addHeader("Content-Type", contentType);
		}

		/*
		 * will execute the request directly
		 */
		new AsyncHttpRequest(client, httpContext, uriRequest, responseHandler)
				.run();
	}

	public void delete(String url, RequestParams queryParams,
			AsyncHttpResponseHandler responseHandler) {
		// TODO what about query params??
		delete(url, responseHandler);
	}

	public void get(String url, RequestParams params) {
		this.get(url, params, responseHandler);
		/*
		 * the response handler will have set the result when this line is
		 * reached
		 */
	}

	public void get(String url) {
		this.get(url, null, responseHandler);
	}

	public void put(String url, RequestParams params) {
		this.put(url, params, responseHandler);
	}

	public void put(String url) {
		this.put(url, null, responseHandler);
	}

	public void post(String url, RequestParams params) {
		this.post(url, params, responseHandler);
	}

	public void post(String url) {
		this.post(url, null, responseHandler);
	}

	public void post(String url, HttpEntity entity) {
		this.post(c, url, entity, null, responseHandler);
	}

	public void delete(String url, RequestParams params) {
		this.delete(url, params, responseHandler);
	}

	public void delete(String url) {
		this.delete(url, null, responseHandler);
	}
}
