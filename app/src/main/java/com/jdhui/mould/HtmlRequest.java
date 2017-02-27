package com.jdhui.mould;

import android.content.Context;
import android.content.Intent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.google.gson.Gson;
import com.jdhui.ApplicationConsts;
import com.jdhui.act.LoginActivity;
import com.jdhui.bean.MyPhotoBean;
import com.jdhui.bean.ResultAccountIndexContentBean;
import com.jdhui.bean.ResultAccountProductTendersContentBean;
import com.jdhui.bean.ResultAssetFixedProductDetailContentBean;
import com.jdhui.bean.ResultAssetInsuranceProductDetailContentBean;
import com.jdhui.bean.ResultCheckVersionBean;
import com.jdhui.bean.ResultCodeBean;
import com.jdhui.bean.ResultCycleIndexContentBean;
import com.jdhui.bean.ResultFindPWDbyPhoneBean;
import com.jdhui.bean.ResultFixedProductDetailContentBean;
import com.jdhui.bean.ResultFixedProductListContentBean;
import com.jdhui.bean.ResultInsuranceProductContentBean;
import com.jdhui.bean.ResultInsuranceProductDetailContentBean;
import com.jdhui.bean.ResultLoginOffBean;
import com.jdhui.bean.ResultMessageListContentBean;
import com.jdhui.bean.ResultMoreInfoBean;
import com.jdhui.bean.ResultMyInfoBean;
import com.jdhui.bean.ResultNewsListBean;
import com.jdhui.bean.ResultNoticeListBean;
import com.jdhui.bean.ResultProductIndexContentBean;
import com.jdhui.bean.ResultSentSMSBean;
import com.jdhui.bean.ResultVerifyPassWordBean;
import com.jdhui.bean.mybean.BookingHospitalList1B;
import com.jdhui.bean.mybean.BookingInsurance1B;
import com.jdhui.bean.mybean.BookingProduct1B;
import com.jdhui.bean.mybean.GeneticTestingDetail1B;
import com.jdhui.bean.mybean.GeneticTestingList1B;
import com.jdhui.bean.mybean.GetGolfInfo1B;
import com.jdhui.bean.mybean.GolfDetail1B;
import com.jdhui.bean.mybean.GolfList1B;
import com.jdhui.bean.mybean.LinerDetail1B;
import com.jdhui.bean.mybean.LinerInfo1B;
import com.jdhui.bean.mybean.LinerList1B;
import com.jdhui.bean.mybean.Product1B;
import com.jdhui.bean.mybean.ProductDetail1B;
import com.jdhui.bean.mybean.Service1B;
import com.jdhui.bean.mybean.ServiceDetail1B;
import com.jdhui.bean.mybean.ServicePicture1B;
import com.jdhui.bean.mybean.SubBookingShip1B;
import com.jdhui.bean.mybean.SubGeneticTesting1B;
import com.jdhui.bean.mybean.SubmitBookingHospital1B;
import com.jdhui.http.SimpleHttpClient;
import com.jdhui.mould.types.IMouldType;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class HtmlRequest extends BaseRequester {

    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        // cookieManager.removeSessionCookie();// 移除
        try {
            cookieManager.setCookie(url, DESUtil.decrypt(PreferenceUtil.getCookie()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CookieSyncManager.getInstance().sync();
    }

    public static void cleanCookie(Context context, String url) {

    }

    /**
     * 处理结果
     *
     * @param result 处理数据（正常返回 true，异常返回false）
     * @return 返回值
     */
    public static Boolean resultEncrypt(Context c, String result) {
        if (result.equals("0000")) {
            return true;
        } else {
            if (result.equals("9999")) {
                Intent i_login = new Intent(c, LoginActivity.class);
//				PreferenceUtil.setAutoLoginAccount("");
                PreferenceUtil.setAutoLoginPwd("");
                PreferenceUtil.setLogin(false);
                PreferenceUtil.setFirstLogin(true);
                PreferenceUtil.setPhone("");
                PreferenceUtil.setUserId("");
                PreferenceUtil.setUserNickName("");
                PreferenceUtil.setIsShowAsset(true);
                // i.putExtra("result", "exit");
                // setResult(9, i);
//				Toast.makeText(c, "服务器异常，请重新登录", Toast.LENGTH_LONG).show();
                i_login.putExtra("tomain", "23");
                c.startActivity(i_login);
            }
        }
        return false;
    }

    /**
     * 检查版本
     *
     * @param context
     * @param type     APP 类型
     * @param listener 监听
     * @return
     */
    public static String checkVersion(final Context context, String type, OnRequestListener listener) {
        final String data = HtmlLoadUtil.checkVersion(type);
        final String url = ApplicationConsts.URL_CHECKVERSION;
        String tid = registerId(Constants.TASK_TYPE_CHECKVERSION, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHECKVERSION, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        ResultCheckVersionBean b = json.fromJson(result, ResultCheckVersionBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 登出
     *
     * @param context
     * @param listener
     * @return
     */
    public static String loginoff(final Context context, OnRequestListener listener) {
        final String url = ApplicationConsts.URL_LOGINOFF;
        String tid = registerId(Constants.TASK_TYPE_LOGINOFF, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_LOGINOFF, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                client.get(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        ResultLoginOffBean b = json.fromJson(result, ResultLoginOffBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 显示图片(photo)
     *
     * @param context
     * @param userId   用户id
     * @param listener
     * @return
     */
    public static String getPhotoState(final Context context, String userId, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getPhotoState(userId);
        final String url = ApplicationConsts.URL_GETPHOTOSTATE;
        String tid = registerId(Constants.TASK_TYPE_MYPHOTOSTATE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MYPHOTOSTATE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        // String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        MyPhotoBean b = json.fromJson(result, MyPhotoBean.class);
                        return b;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 发送手机短信
     *
     * @param context    上下文
     * @param userMobile 用户手机号
     * @param busiType   类型
     * @param listener   监听
     * @return 返回数据
     */
    public static String sentSMS(final Context context, String userMobile, String busiType, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getSMS(userMobile, busiType);
        final String url = ApplicationConsts.URL_SMS;
        String tid = registerId(Constants.TASK_TYPE_SMS, url);
        // HtmlRequest.synCookies(context, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SMS, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        ResultSentSMSBean b = json.fromJson(result, ResultSentSMSBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 通过手机号找回密码
     *
     * @param context
     * @param mobile       用户手机号
     * @param newPassword  修改后的密码
     * @param validateCode 验证码
     * @param userName     用户名
     * @param listener
     * @return
     */
    public static String findPWDbyPhone(final Context context, String mobile, String newPassword, String validateCode, String userName, OnRequestListener listener) {
        final String data = HtmlLoadUtil.findPWDbyPhone(mobile, newPassword, userName, validateCode);
        final String url = ApplicationConsts.URL_FINDPWDBYPHONE;
        String tid = registerId(Constants.TASK_TYPE_FINDPWDBYPHONE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_FINDPWDBYPHONE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                // HtmlRequest.synCookies(context, url);
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultFindPWDbyPhoneBean b = json.fromJson(data, ResultFindPWDbyPhoneBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 上传图片
     *
     * @param context
     * @param photo    图片
     * @param listener
     * @return
     */
    public static String upLoadPhoto(final Context context, File photo, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getUpLoadPhoto(photo);
        final String url = ApplicationConsts.URL_UPLOADPHOTO;
        String tid = registerId(Constants.TASK_TYPE_UPLOAD_PHOTO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_UPLOAD_PHOTO, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultMoreInfoBean b = json.fromJson(data, ResultMoreInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--我的信息
     *
     * @param context    上下文
     * @param userInfoId 用户编号
     * @param listener   监听
     * @return
     */
    public static String getMyInfo(final Context context, String userInfoId, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getMyInfo(userInfoId);
        final String url = ApplicationConsts.URL_MY_INFO;
        String tid = registerId(Constants.TASK_TYPE_MY_INFO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MY_INFO, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultMyInfoBean b = json.fromJson(data, ResultMyInfoBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取更多信息
     *
     * @param context
     * @param userId   用户编号
     * @param listener
     * @return
     */
    public static String getMoreInfo(final Context context, String userId, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getMoreInfo(userId);
        final String url = ApplicationConsts.URL_MORE_INFO;
        String tid = registerId(Constants.TASK_TYPE_MORE_INFO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MORE_INFO, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultMoreInfoBean b = json.fromJson(data, ResultMoreInfoBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 资产首页
     *
     * @param context
     * @param userId   用户ID
     * @param listener
     * @return
     */
    public static String accountIndex(final Context context, String userId, OnRequestListener listener) {
//		String token = "";
//		try {
//			token = DESUtil.decrypt(PreferenceUtil.getToken());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		final String data = HtmlLoadUtil.accountIndex(userId,token);
        final String data = HtmlLoadUtil.accountIndex(userId);
        final String url = ApplicationConsts.URL_ACCOUNT_INDEX;
        String tid = registerId(Constants.TASK_TYPE_ACCOUNT_INDEX, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ACCOUNT_INDEX, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultAccountIndexContentBean b = json.fromJson(data, ResultAccountIndexContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 资产--固定收益投资列表
     *
     * @param context  上下文
     * @param page     页码
     * @param userId   用户ID
     * @param type     产品类别{非保险类别(固收:optimum;浮动收益:floating; }{保险:insurance) }
     * @param listener 监听
     * @return
     */
    public static String accountProductTenders(final Context context, String page, String userId, String type, OnRequestListener listener) {
        final String data = HtmlLoadUtil.accountProductTenders(type, page, userId);
        final String url = ApplicationConsts.URL_ACCOUNT_PRODUCT_TENDERS;
        String tid = registerId(Constants.TASK_TYPE_ACCOUNT_PRODUCT_TENDERS, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ACCOUNT_PRODUCT_TENDERS, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultAccountProductTendersContentBean b = json.fromJson(data, ResultAccountProductTendersContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 得到非保险产品列表
     *
     * @param context
     * @param category 产品类型：取值有（optimum:固收，floating:浮动收益）
     * @param page     页码
     * @param listener
     * @return
     */
    public static String getProductList(final Context context, String category, int page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getProductList(category, page);
        final String url = ApplicationConsts.URL_PRODUCT_LIST;
        String tid = registerId(Constants.TASK_TYPE_PRODUCT_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_PRODUCT_LIST, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultFixedProductListContentBean b = json.fromJson(data, ResultFixedProductListContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 得到保险产品列表
     *
     * @param context
     * @param type     保险类型:healthInsurance健康险; accidentInsurance:意外险; lifeInsurance:人寿险; propertyInsurance:财产险; travelInsurance:旅游险;
     * @param page     页码
     * @param listener
     * @return
     */
    public static String getInsuranceList(final Context context, String type, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getInsuranceList(type, page);
        final String url = ApplicationConsts.URL_INSURANCE_PRODUCT_LIST;
        String tid = registerId(Constants.TASK_TYPE_INSURANCE_PRODUCT_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INSURANCE_PRODUCT_LIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultInsuranceProductContentBean b = json.fromJson(data, ResultInsuranceProductContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取非保险产品详情
     *
     * @param context
     * @param productId 产品ID
     * @param listener
     * @return
     */
    public static String getProductDetail(final Context context, String productId, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getFixedProductDetail(productId);
        final String url = ApplicationConsts.URL_PRODUCT_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_PRODUCT_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_PRODUCT_DETAIL, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultFixedProductDetailContentBean b = json.fromJson(data, ResultFixedProductDetailContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取保险产品详情
     *
     * @param context
     * @param productId 产品ID
     * @param listener
     * @return
     */
    public static String getInsuranceProductDetail(final Context context, String productId, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getFixedProductDetail(productId);
        final String url = ApplicationConsts.URL_INSURANCE_PRODUCT_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_INSURANCE_PRODUCT_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INSURANCE_PRODUCT_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultInsuranceProductDetailContentBean b = json.fromJson(data, ResultInsuranceProductDetailContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取非保险投资产品详情
     *
     * @param context
     * @param userId   用户ID
     * @param tenderId 订单编号
     * @param type     固收:optimum;浮动收益:floating;
     * @param listener
     * @return
     */
    public static String getAssetProductDetail(final Context context, String userId, String tenderId, String type, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getAssetFixedProductDetail(userId, tenderId, type);
        final String url = ApplicationConsts.URL_ASSET_PRODUCT_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_ASSET_PRODUCT_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ASSET_PRODUCT_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultAssetFixedProductDetailContentBean b = json.fromJson(data, ResultAssetFixedProductDetailContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取保险投资产品详情
     *
     * @param context
     * @param userId   用户ID
     * @param tenderId 订单编号
     * @param listener
     * @return
     */
    public static String getInsuranceAssetProductDetail(final Context context, String userId, String tenderId, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getAssetInsuranceProductDetail(userId, tenderId);
        final String url = ApplicationConsts.URL_ASSET_INSURANCE_PRODUCT_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_ASSET_INSURANCE_PRODUCT_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ASSET_INSURANCE_PRODUCT_DETAIL, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultAssetInsuranceProductDetailContentBean b = json.fromJson(data, ResultAssetInsuranceProductDetailContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 产品--最新公告和热销产品接口
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getProductIndex(final Context context, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getProductIndex();
        final String url = ApplicationConsts.URL_PRODUCT_INDEX;
        String tid = registerId(Constants.TASK_TYPE_PRODUCT_INDEX, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ACCOUNT_INDEX, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
//						Toast.makeText(context,"result=="+result,Toast.LENGTH_SHORT).show();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
//								Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show();
                        ResultProductIndexContentBean b = json.fromJson(data, ResultProductIndexContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 产品--获取轮播图
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCycleIndex(final Context context, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getCycle("android");
        final String url = ApplicationConsts.URL_CYCLE_ADVERTISE_INDEX;
        String tid = registerId(Constants.TASK_TYPE_CYCLE_ADVICES_INDEX, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CYCLE_ADVICES_INDEX, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultCycleIndexContentBean b = json.fromJson(data, ResultCycleIndexContentBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 资产--获取消息列表
     *
     * @param context  上下文
     * @param userId   用户ID
     * @param page     页码
     * @param listener 监听
     * @return 返回数据
     */
    public static String getMessageList(final Context context, String userId, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getMessageList(userId, page);
        final String url = ApplicationConsts.URL_MESSAGE_LIST_INDEX;
        String tid = registerId(Constants.TASK_TYPE_MESSAGE_LIST_INDEX, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MESSAGE_LIST_INDEX, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
//						Toast.makeText(context,"result=="+result,Toast.LENGTH_SHORT).show();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultMessageListContentBean b = json.fromJson(data, ResultMessageListContentBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--产品预约
     *
     * @param context
     * @param userInfoId 用户ID
     * @param category   产品类型
     * @param status     预约状态 （submit:待确认;confirm:已确认;cancel:无效预约）
     * @param page       页码
     * @param listener
     * @return
     */
    public static String getProductOrderList(final Context context, String userInfoId, String category, String status, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getProductOrderList(userInfoId, category, status, page);
        final String url = ApplicationConsts.URL_PRODUCT_ORDER;
        String tid = registerId(Constants.TASK_TYPE_PRODUCT_ORDER, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_PRODUCT_ORDER, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        Product1B b = json.fromJson(data, Product1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--产品预约详情
     *
     * @param context
     * @param id       用户ID
     * @param category 产品类型
     * @param listener
     * @return
     */
    public static String getProOrderDetail(final Context context, String id, String category, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getProOrderDetail(id, category);
        final String url = ApplicationConsts.URL_PRODUCT_ORDER_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_PRODUCT_ORDER_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_PRODUCT_ORDER_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ProductDetail1B b = json.fromJson(data, ProductDetail1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--服务预约列表
     *
     * @param context
     * @param serviceItems 绿通就医：hospitalBooking  基因检测：geneticBooking  高尔夫球场：golfBooking  公务机包机：airplaneBooking  豪华游轮:luxuryShipBooking
     * @param page         页数
     * @param listener
     * @return
     */
    public static String getServiceOrderList(final Context context, String serviceItems, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getServicetOrderList(serviceItems, page);
        final String url = ApplicationConsts.URL_SERVICE_ORDER;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_ORDER, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_ORDER, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        Service1B b = json.fromJson(data, Service1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--服务预约详情
     *
     * @param context
     * @param serviceItems 绿通就医：hospitalBooking  基因检测：geneticBooking  高尔夫球场：golfBooking  公务机包机：airplaneBooking
     * @param id           服务Id
     * @param listener
     * @return
     */
    public static String getServiceDetail(final Context context, String serviceItems, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getServiceDetail(serviceItems, id);
        final String url = ApplicationConsts.URL_SERVICE_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ServiceDetail1B b = json.fromJson(data, ServiceDetail1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--君德公告
     *
     * @param context  上下文
     * @param page     页码
     * @param listener 监听
     * @return
     */
    public static String getNoticeList(final Context context, int page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getNoticeList(page + "");
        final String url = ApplicationConsts.URL_NOTICE;
        String tid = registerId(Constants.TASK_TYPE_NOTICE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_NOTICE, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);

                       /* Map<String, Object> map1 = JSON.parseObject(r);
                        Object data = map1.get("data");
                        Map<String, Object> mapArray = JSON.parseObject(data.toString());
                        List<ResultNoticeContentBean> list = JSON.parseArray(mapArray.get("list").toString(), ResultNoticeContentBean.class);
                        return (IMouldType) list;*/

                        Gson json = new Gson();
                        ResultNoticeListBean b = json.fromJson(data, ResultNoticeListBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--快讯
     *
     * @param context
     * @param page     页码
     * @param listener
     * @return
     */
    public static String getNewsList(final Context context, int page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getNewsList(page + "");
        final String url = ApplicationConsts.URL_NEWS;
        String tid = registerId(Constants.TASK_TYPE_NEWS, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_NEWS, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultNewsListBean b = json.fromJson(data, ResultNewsListBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 验证登录密码
     *
     * @param context
     * @param userId   用户编号
     * @param password 登录密码
     * @param listener
     * @return
     */
    public static String verifyPassWord(final Context context, String userId, String password, OnRequestListener listener) {
        final String data = HtmlLoadUtil.verifyPassWord(userId, password);
        final String url = ApplicationConsts.URL_VERIFY_PASSWORD;
        String tid = registerId(Constants.TASK_TYPE_VERIFY_PASSWORD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_VERIFY_PASSWORD, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                // HtmlRequest.synCookies(context, url);
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultVerifyPassWordBean b = json.fromJson(data, ResultVerifyPassWordBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存修改后手机号
     *
     * @param context
     * @param mobile       修改后的手机号
     * @param validateCode 验证码
     * @param listener
     * @return
     */
    public static String savePhone(final Context context, String mobile, String validateCode, OnRequestListener listener) {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.savePhone(userId, mobile, validateCode);
        final String url = ApplicationConsts.URL_SAVE_PHONE;
        String tid = registerId(Constants.TASK_TYPE_SAVE_PHONE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SAVE_PHONE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                // HtmlRequest.synCookies(context, url);
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultCodeBean b = json.fromJson(data, ResultCodeBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 修改登录密码
     *
     * @param context
     * @param userId      用户ID
     * @param oldpassword 旧密码
     * @param newpassword 新密码
     * @param listener
     * @return
     */
    public static String changePassword(final Context context, String userId, String oldpassword, String newpassword, OnRequestListener listener) {
        final String data = HtmlLoadUtil.changePassword(userId, oldpassword, newpassword);
        final String url = ApplicationConsts.URL_CHANGEPWD;
        String tid = registerId(Constants.TASK_TYPE_CHANGE_PASSWORD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGE_PASSWORD, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultCodeBean b = json.fromJson(data, ResultCodeBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存修改后地址
     *
     * @param context
     * @param userId   用户编号
     * @param address  地址
     * @param listener
     * @return
     */
    public static String saveAddress(final Context context, String userId, String address, OnRequestListener listener) {
        final String data = HtmlLoadUtil.saveAddress(userId, address);
        final String url = ApplicationConsts.URL_SAVE_ADDRESS;
        String tid = registerId(Constants.TASK_TYPE_SAVE_ADDRESS, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SAVE_ADDRESS, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultCodeBean b = json.fromJson(data, ResultCodeBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 意见反馈
     *
     * @param context
     * @param userId   用户编号
     * @param content  用户反馈内容（200字以内）
     * @param listener
     * @return
     */
    public static String getAdviceData(final Context context, String userId, String content, OnRequestListener listener) {
        final String data = HtmlLoadUtil.feedBack(userId, content);
        final String url = ApplicationConsts.URL_FEED_BACK;
        String tid = registerId(Constants.TASK_TYPE_FEED_BACK, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_FEED_BACK, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCodeBean b = json.fromJson(data, ResultCodeBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我同意该承诺、跳过 接口
     *
     * @param context
     * @param userId            用户编号
     * @param qualifiedInvestor 投资者类型：acceptable:合格投资者;   unacceptable:不合格投资者
     * @param listener
     * @return
     */
    public static String investorJudgeSave(final Context context, String userId, String qualifiedInvestor, OnRequestListener listener) {
        final String data = HtmlLoadUtil.investorJudgeSave(userId, qualifiedInvestor);
        final String url = ApplicationConsts.URL_INVESTOR_JUDGE_SAVE;
        String tid = registerId(Constants.TASK_TYPE_INVESTOR_JUDGE_SAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INVESTOR_JUDGE_SAVE, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCodeBean b = json.fromJson(data, ResultCodeBean.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--提交预约医院
     *
     * @param context          上下文
     * @param userId           当前用户Id
     * @param hospitalId       医院Id
     * @param departments      科室
     * @param doctor           医生
     * @param bookingTime      预约时间
     * @param bakTimeOne       备选时间1
     * @param bakTimeTwo       备选时间2
     * @param illnessCondition 主诉病情
     * @param bookingClient    预约人
     * @param securityNum      社保号码
     * @param clientPhone      预约人电话号
     * @param clientIdNo       预约人身份证号
     * @param listener         监听
     * @return
     */
    public static String submitBookingHospital(final Context context, String userId, String hospitalId, String departments, String doctor, String bookingTime, String bakTimeOne, String bakTimeTwo, String illnessCondition, String bookingClient, String securityNum, String clientPhone, String clientIdNo, OnRequestListener listener) {
        final String data = HtmlLoadUtil.submitBookingHospital(userId, hospitalId, departments, doctor, bookingTime, bakTimeOne, bakTimeTwo, illnessCondition, bookingClient, securityNum, clientPhone, clientIdNo);
        final String url = ApplicationConsts.URL_SERVICE_BOOKINGHOSPITAL;
        String tid = registerId(Constants.TASK_TYPE_BOOKING_HOSPITAL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BOOKING_HOSPITAL, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        SubmitBookingHospital1B b = json.fromJson(data, SubmitBookingHospital1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--展示预约医院列表
     *
     * @param context
     * @param province     省份
     * @param city         市级地区
     * @param hospitalName 医院名称
     * @param page         页码
     * @param listener
     * @return
     */
    public static String getBookingHospitalList(final Context context, String province, String city, String hospitalName, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getBookingHospitalList(province, hospitalName, city, page);
        final String url = ApplicationConsts.URL_SERVICE_BOOKINGHOSPITAL_LIST;
        String tid = registerId(Constants.TASK_TYPE_BOOKING_HOSPITAL_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BOOKING_HOSPITAL_LIST, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        BookingHospitalList1B b = json.fromJson(data, BookingHospitalList1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--展示预约基因检测列表
     *
     * @param context  上下文
     * @param page     页数
     * @param listener 监听
     * @return
     */
    public static String getGeneticTestingList(final Context context, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getGeneticTestingList(page);
        final String url = ApplicationConsts.URL_SERVICE_BOOKINGGENETICTESTING_LIST;
        String tid = registerId(Constants.TASK_TYPE_BOOKING_GENETICTESTING_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BOOKING_GENETICTESTING_LIST, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        GeneticTestingList1B b = json.fromJson(data, GeneticTestingList1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--展示预约高尔夫球场列表
     *
     * @param context
     * @param page     页数
     * @param listener
     * @return
     */
    public static String getGolfList(final Context context, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getGolfList(page);
        final String url = ApplicationConsts.URL_SERVICE_GOLF_LIST;
        String tid = registerId(Constants.TASK_TYPE_GOLF_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_GOLF_LIST, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        GolfList1B b = json.fromJson(data, GolfList1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--展示预约基因检测详情
     *
     * @param context
     * @param id       被点击的item的id
     * @param listener
     * @return
     */
    public static String getGeneticTestingDetail(final Context context, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getGeneticTestingDetail(id);
        final String url = ApplicationConsts.URL_SERVICE_BOOKINGGENETICTESTING_DETAIL;
        String tid = registerId(Constants.TASK_TYPE_BOOKING_GENETICTESTING_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BOOKING_GENETICTESTING_DETAIL, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        GeneticTestingDetail1B b = json.fromJson(data, GeneticTestingDetail1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--提交基因检测预约
     *
     * @param context
     * @param geneticTestingId 基因检测Id
     * @param userSex          预约人性别
     * @param userAge          预约人年龄
     * @param userAddress      预约人地址
     * @param bookingClient    预约人
     * @param clientPhone      预约人电话号
     * @param listener
     * @return
     */
    public static String subGeneticTesting(final Context context, String geneticTestingId, String userSex, String userAge, String userAddress, String bookingClient, String clientPhone, OnRequestListener listener) {
        final String data = HtmlLoadUtil.subGeneticTesting(geneticTestingId, userSex, userAge, userAddress, bookingClient, clientPhone);
        final String url = ApplicationConsts.URL_SERVICE_BOOKINGGENETICTESTING_ADD;
        String tid = registerId(Constants.TASK_TYPE_BOOKING_GENETICTESTING_ADD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BOOKING_GENETICTESTING_ADD, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        SubGeneticTesting1B b = json.fromJson(data, SubGeneticTesting1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保险预约
     *
     * @param context
     * @param productId     产品ID
     * @param userInfoId    用户ID
     * @param bookingRemark 预约备注
     * @param bookingAmount 预约金额
     * @param listener
     * @return
     */
    public static String subBookingInsurance(final Context context, String productId, String userInfoId, String bookingRemark, String bookingAmount, OnRequestListener listener) {
        final String data = HtmlLoadUtil.subBookingInsurance(productId, userInfoId, bookingRemark, bookingAmount);
        final String url = ApplicationConsts.URL_INSURANCE_BOOKING;
        String tid = registerId(Constants.TASK_TYPE_INSURANCE_BOOKING, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INSURANCE_BOOKING, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        BookingInsurance1B b = json.fromJson(data, BookingInsurance1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 非保险预约
     *
     * @param context
     * @param productId     产品ID
     * @param userInfoId    用户ID
     * @param bookingRemark 预约备注
     * @param bookingAmount 预约金额
     * @param type          取值有（optimum:固收，floating:浮动收益）
     * @param listener
     * @return
     */
    public static String subBookingProduct(final Context context, String productId, String userInfoId, String bookingRemark, String bookingAmount, String type, OnRequestListener listener) {
        final String data = HtmlLoadUtil.subBookingProduct(productId, userInfoId, bookingRemark, bookingAmount, type);
        final String url = ApplicationConsts.URL_PRODUCT_BOOKING;
        String tid = registerId(Constants.TASK_TYPE_PRODUCT_BOOKING, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_PRODUCT_BOOKING, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        BookingProduct1B b = json.fromJson(data, BookingProduct1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--高尔夫球场详情
     *
     * @param context
     * @param id       高尔夫球场Id
     * @param listener
     * @return
     */
    public static String getGolfDetail(final Context context, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getGolfDetail(id);
        final String url = ApplicationConsts.URL_SERVICE_GOLF_VIEW;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_GOLF_VIEW, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_GOLF_VIEW, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        GolfDetail1B b = json.fromJson(data, GolfDetail1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--获取高尔夫提交预约时的客户姓名和身份证号
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getInfo(final Context context, OnRequestListener listener) {
        final String url = ApplicationConsts.URL_GET_INFO;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_GET_INFO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_GET_INFO, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        GetGolfInfo1B b = json.fromJson(data, GetGolfInfo1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--提交高尔夫球场预约
     *
     * @param context
     * @param bookingTime 预约时间
     * @param clientPhone 预约电话
     * @param golfId      高尔夫球场Id
     * @param peersOne    同行人1
     * @param peersTwo    同行人2
     * @param listener
     * @return
     */
    public static String submitGolfDetail(final Context context, String bookingTime, String clientPhone, String golfId, String peersOne, String peersTwo, OnRequestListener listener) {
        final String data = HtmlLoadUtil.submitGolf(bookingTime, clientPhone, golfId, peersOne, peersTwo);
        final String url = ApplicationConsts.BOOKING_GOLF;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_BOOKING_GOLF, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_BOOKING_GOLF, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        SubmitBookingHospital1B b = json.fromJson(data, SubmitBookingHospital1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--显示服务首页图片
     *
     * @param context  上下文
     * @param id       userId(可传可不传)
     * @param listener 监听
     * @return 返回数据
     */
    public static String getServicePicture(final Context context, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getServicePicture(id);
        final String url = ApplicationConsts.URL_TURN_SERVICE_PICTURE;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_PICTURE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_PICTURE, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ServicePicture1B b = json.fromJson(data, ServicePicture1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 更多--取消预约（服务详情页待确认状态时显示取消按钮）
     *
     * @param context
     * @param id           服务id
     * @param serviceItems 服务类型
     * @param name         医院名称
     * @param departments  科室
     * @param bookingTime  预约时间
     * @param golfName     高尔夫球场名称
     * @param listener
     * @return
     */
    public static String cancelBooking(final Context context, String id, String serviceItems, String name, String departments, String bookingTime, String golfName, OnRequestListener listener) {
        final String data = HtmlLoadUtil.cancelBooking(id, serviceItems, name, departments, bookingTime, golfName);
        final String url = ApplicationConsts.URL_SERVICE_BOOKING_CANCEL;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_BOOKING_CANCEL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_BOOKING_CANCEL, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        BookingInsurance1B b = json.fromJson(data, BookingInsurance1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--展示豪华游轮列表
     *
     * @param context
     * @param page     页数
     * @param listener
     * @return
     */
    public static String getLinerList(final Context context, String page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getLinerList(page);
        final String url = ApplicationConsts.URL_SERVICE_SHIP_LIST;
        String tid = registerId(Constants.TASK_TYPE_LINER_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_LINER_LIST, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        LinerList1B b = json.fromJson(data, LinerList1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 服务--游轮详情
     *
     * @param context
     * @param id       游轮Id
     * @param listener
     * @return
     */
    public static String getLinerDetail(final Context context, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getLinerDetail(id);
        final String url = ApplicationConsts.URL_SERVICE_SHIP_VIEW;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_LINER_VIEW, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_LINER_VIEW, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        LinerDetail1B b = json.fromJson(data, LinerDetail1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 游轮详情之游轮信息
     * @param context
     * @param id
     * @param listener
     * @return
     */
    public static String getLinerInfo(final Context context, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getLinerInfo(id);
        final String url = ApplicationConsts.URL_SERVICE_SHIP_VIEW_INFO;
        String tid = registerId(Constants.TASK_TYPE_SERVICE_LINER_VIEW_INFO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SERVICE_LINER_VIEW_INFO, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        LinerInfo1B b = json.fromJson(data, LinerInfo1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }


    public static String subBookingShip(final Context context, String clientPhone,  String shipId, String clientName, OnRequestListener listener) {
        final String data = HtmlLoadUtil.subBookingShip(clientPhone, shipId, clientName);
        final String url = ApplicationConsts.URL_SERVICE_BOOKINGLUXURYSHIP_ADD;
        String tid = registerId(Constants.TASK_TYPE_BOOKING_SHIP, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BOOKING_SHIP, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        SubBookingShip1B b = json.fromJson(data, SubBookingShip1B.class);
                        resultEncrypt(context, b.getCode());
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

}
