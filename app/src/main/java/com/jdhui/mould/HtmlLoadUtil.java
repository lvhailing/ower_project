package com.jdhui.mould;

import com.google.gson.Gson;
import com.jdhui.bean.AppTypeBean;
import com.jdhui.bean.AssetInsuranceProductIdBean;
import com.jdhui.bean.AssetProductIdBean;
import com.jdhui.bean.ChangePWDBean;
import com.jdhui.bean.CheckPhoneBean;
import com.jdhui.bean.CheckVersionBean;
import com.jdhui.bean.EcryptBean;
import com.jdhui.bean.FeedBackBean;
import com.jdhui.bean.FindPWDbyPhoneBean;
import com.jdhui.bean.InsuranceListBean;
import com.jdhui.bean.InvestorJudgeBean;
import com.jdhui.bean.MoreInfoBean;
import com.jdhui.bean.MyAccountBean;
import com.jdhui.bean.NoticeListBean;
import com.jdhui.bean.ProductIdBean;
import com.jdhui.bean.ProductListBean;
import com.jdhui.bean.SaveAddressBean;
import com.jdhui.bean.SavePhoneBean;
import com.jdhui.bean.SentSMSBean;
import com.jdhui.bean.UpLoadPhotoBean;
import com.jdhui.bean.UserIDBean;
import com.jdhui.bean.UserLoginBean;
import com.jdhui.bean.UserPageBean;
import com.jdhui.bean.UserProductTendersBean;
import com.jdhui.bean.VerifyPassWordBean;
import com.jdhui.bean.mybean.BookingHospitalList0B;
import com.jdhui.bean.mybean.BookingInsurance0B;
import com.jdhui.bean.mybean.BookingProduct0B;
import com.jdhui.bean.mybean.CancelBooking0B;
import com.jdhui.bean.mybean.GeneticTestingDetail0B;
import com.jdhui.bean.mybean.GeneticTestingList0B;
import com.jdhui.bean.mybean.GolfDetail0B;
import com.jdhui.bean.mybean.GolfList0B;
import com.jdhui.bean.mybean.LinerDetail0B;
import com.jdhui.bean.mybean.LinerInfo0B;
import com.jdhui.bean.mybean.LinerList0B;
import com.jdhui.bean.mybean.OverseaProjectDetail0B;
import com.jdhui.bean.mybean.OverseaProjectList0B;
import com.jdhui.bean.mybean.Product0B;
import com.jdhui.bean.mybean.ProductDetail0B;
import com.jdhui.bean.mybean.Service0B;
import com.jdhui.bean.mybean.ServiceDetail0B;
import com.jdhui.bean.mybean.ServicePicture0B;
import com.jdhui.bean.mybean.SubBookingShip0B;
import com.jdhui.bean.mybean.SubGeneticTesting0B;
import com.jdhui.bean.mybean.SubOverseaProject0B;
import com.jdhui.bean.mybean.SubOverseasMedical0B;
import com.jdhui.bean.mybean.SubPhotography0B;
import com.jdhui.bean.mybean.SubmitBookingGolf0B;
import com.jdhui.bean.mybean.SubmitBookingHospital0B;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.MD5;

import java.io.File;

public class HtmlLoadUtil {

    private static String getResult(Object data) {
        Gson gson = new Gson();
        String md5 = MD5.stringToMD5(gson.toJson(data));
        String result = null;
        try {
            EcryptBean b = new EcryptBean(md5, data);
            String encrypt = gson.toJson(b);
            result = DESUtil.encrypt(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getResultNoEncrypt(Object data) {
        Gson gson = new Gson();
        String md5 = MD5.stringToMD5(gson.toJson(data));
        String result = null;
        try {
            EcryptBean b = new EcryptBean(md5, data);
            result = gson.toJson(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 检查版本
     *
     * @param type
     * @return
     */
    public static String checkVersion(String type) {
        CheckVersionBean b = new CheckVersionBean(type);
        return getResult(b);
    }

    /**
     * 登陆
     *
     * @param userName
     * @param password
     * @param token
     * @return
     */
    public static String frontLogin(String userName, String password, String token, String appid) {
        UserLoginBean bean = new UserLoginBean(userName, password, token, appid);
        return getResult(bean);
    }

    /**
     * 显示图片
     *
     * @param userId
     * @return
     */
    public static String getPhotoState(String userId) {
        MyAccountBean b = new MyAccountBean(userId);
        return getResultNoEncrypt(b);
    }

    /**
     * 获取验证码
     *
     * @param userMobile
     * @param busiType
     * @return
     */
    public static String getSMS(String userMobile, String busiType) {
        SentSMSBean b = new SentSMSBean(userMobile, busiType);
        return getResultNoEncrypt(b);
    }

    /**
     * 找回密码
     *
     * @param validateCode
     * @return
     */
    public static String findPWDbyPhone(String mobile, String newPassword, String userName, String validateCode) {
        FindPWDbyPhoneBean b = new FindPWDbyPhoneBean(mobile, newPassword, userName, validateCode);
        return getResult(b);
    }

    /**
     * 上传头像
     *
     * @return
     */
    public static String getUpLoadPhoto(File photo) {
        UpLoadPhotoBean b = new UpLoadPhotoBean(photo);
        return getResultNoEncrypt(b);
    }

    /**
     * 我的信息
     *
     * @param userInfoId
     * @return
     */
    public static String getMyInfo(String userInfoId) {
        MoreInfoBean b = new MoreInfoBean(userInfoId);
        return getResult(b);
    }

    /**
     * 更多信息
     *
     * @param userId 用户id
     * @return
     */
    public static String getMoreInfo(String userId) {
        MoreInfoBean b = new MoreInfoBean(userId);
        return getResult(b);
    }

    /**
     * 资产首页
     *
     * @param userId 用户id
     * @return
     */
    public static String accountIndex(String userId, String token) {
        UserIDBean b = new UserIDBean(userId, token);
        return getResult(b);
    }

    public static String accountIndex(String userId) {
        UserIDBean b = new UserIDBean(userId);
        return getResult(b);
    }

    /**
     * 投资列表
     *
     * @param userId 用户id
     * @return
     */
    public static String accountProductTenders(String type, String page, String userId) {
        UserProductTendersBean b = new UserProductTendersBean(type, page, userId);
        return getResult(b);
    }

    /**
     * 非保险投资列表
     *
     * @param category 用户id
     * @return
     */
    public static String getProductList(String category, int page) {
        ProductListBean b = new ProductListBean(category, page);
        return getResult(b);
    }

    /**
     * 非保险投资列表
     *
     * @param type 用户id
     * @return
     */
    public static String getInsuranceList(String type, String page) {
        InsuranceListBean b = new InsuranceListBean(type, page);
        return getResult(b);
    }

    /**
     * 非保险产品详情
     *
     * @param productId 用户id
     * @return
     */
    public static String getFixedProductDetail(String productId) {
        ProductIdBean b = new ProductIdBean(productId);
        return getResult(b);
    }

    /**
     * 非保险投资产品详情
     *
     * @param userId 用户id
     * @return
     */
    public static String getAssetFixedProductDetail(String userId, String tenderId, String type) {
        AssetProductIdBean b = new AssetProductIdBean(userId, tenderId, type);
        return getResult(b);
    }

    /**
     * 保险投资产品详情
     *
     * @param userId 用户id
     * @return
     */
    public static String getAssetInsuranceProductDetail(String userId, String tenderId) {
        AssetInsuranceProductIdBean b = new AssetInsuranceProductIdBean(userId, tenderId);
        return getResult(b);
    }

    /**
     * 最新公告和热销产品接口
     *
     * @return a
     */
    public static String getProductIndex() {
//		NullBean b = new UserIDBean(userId);
        String b = "";
        return getResult(b);
    }

    /**
     * 获取轮播图
     *
     * @return a
     */
    public static String getCycle(String appType) {
        AppTypeBean b = new AppTypeBean(appType);
        return getResult(b);
    }

    /**
     * 资产--消息列表（入参）
     *
     * @param userId 用户ID
     * @param page   页码
     * @return 返回数据
     */
    public static String getMessageList(String userId, String page) {
        UserPageBean b = new UserPageBean(userId, page);
        return getResult(b);
    }

    /**
     * 更多--产品预约
     */
    public static String getProductOrderList(String userInfoId, String category, String status, String page) {
        Product0B b = new Product0B(userInfoId, category, status, page);
        return getResult(b);
    }

    /**
     * 更多--产品预约详情
     *
     * @param id       用户id
     * @param category 产品类型
     * @return
     */
    public static String getProOrderDetail(String id, String category) {
        ProductDetail0B b = new ProductDetail0B(id, category);
        return getResult(b);
    }

    /**
     * 更多--服务预约
     */
    public static String getServicetOrderList(String serviceItems, String page) {
        Service0B b = new Service0B(serviceItems, page);
        return getResult(b);
    }

    /**
     * 更多--服务预约详情
     *
     * @param id           服务id
     * @param serviceItems 服务类型
     * @return
     */
    public static String getServiceDetail(String serviceItems, String id) {
        ServiceDetail0B b = new ServiceDetail0B(serviceItems, id);
        return getResult(b);
    }

    /**
     * 君德公告
     *
     * @param page
     * @return
     */
    public static String getNoticeList(String page) {
        NoticeListBean b = new NoticeListBean(page);
        return getResult(b);
    }

    /**
     * 快讯
     *
     * @param page
     * @return
     */
    public static String getNewsList(String page) {
        NoticeListBean b = new NoticeListBean(page);
        return getResult(b);
    }

    /**
     * 验证登录密码
     *
     * @param password
     * @return
     */
    public static String verifyPassWord(String userId, String password) {
        VerifyPassWordBean b = new VerifyPassWordBean(userId, password);
        return getResult(b);
    }

    /**
     * 保存修改后手机号(修改手机号（二）)
     *
     * @return
     */
    public static String savePhone(String userId, String mobile, String validateCode) {
        SavePhoneBean b = new SavePhoneBean(userId, mobile, validateCode);
        return getResult(b);
    }

    // 修改手机号（一）
    public static String checkPhone(String userId, String mobile) {
        CheckPhoneBean b = new CheckPhoneBean(userId, mobile);
        return getResult(b);
    }

    /**
     * 修改密码
     *
     * @return
     */
    public static String changePassword(String userId, String oldPassword, String password) {
        ChangePWDBean b = new ChangePWDBean(userId, oldPassword, password);
        return getResult(b);
    }

    /**
     * 保存修改后地址
     *
     * @return
     */
    public static String saveAddress(String userId, String address) {
        SaveAddressBean b = new SaveAddressBean(userId, address);
        return getResult(b);
    }

    /**
     * 意见反馈
     *
     * @return
     */
    public static String feedBack(String userId, String content) {
        FeedBackBean b = new FeedBackBean(userId, content);
        return getResult(b);
    }

    /**
     * 意见反馈
     *
     * @return
     */
    public static String investorJudgeSave(String userId, String qualifiedInvestor) {
        InvestorJudgeBean b = new InvestorJudgeBean(userId, qualifiedInvestor);
        return getResult(b);
    }

    /**
     * 服务--提交预约医院
     */
    public static String submitBookingHospital(String userId, String hospitalId, String departments, String doctor, String bookingTime, String bakTimeOne, String bakTimeTwo, String illnessCondition, String bookingClient, String securityNum, String clientPhone, String clientIdNo) {
        SubmitBookingHospital0B b = new SubmitBookingHospital0B(userId, hospitalId, departments, doctor, bookingTime, bakTimeOne, bakTimeTwo, illnessCondition, bookingClient, securityNum, clientPhone, clientIdNo);
        return getResult(b);
    }

    /**
     * 服务--展示预约医院列表
     *
     * @return
     */
    public static String getBookingHospitalList(String province, String city, String hospitalName, String page) {
        BookingHospitalList0B b = new BookingHospitalList0B(province, hospitalName, city, page);
        return getResult(b);
    }

    /**
     * 服务--展示预约基因检测列表
     *
     * @return
     */
    public static String getGeneticTestingList(String page) {
        GeneticTestingList0B b = new GeneticTestingList0B(page);
        return getResult(b);
    }

    /**
     * 服务--展示预约基因检测详情
     *
     * @return
     */
    public static String getGeneticTestingDetail(String id) {
        GeneticTestingDetail0B b = new GeneticTestingDetail0B(id);
        return getResult(b);
    }

    /**
     * 服务--提交基因检测预约
     *
     * @return
     */
    public static String subGeneticTesting(String geneticTestingId, String userSex, String userAge, String userAddress, String bookingClient, String clientPhone) {
        SubGeneticTesting0B b = new SubGeneticTesting0B(geneticTestingId, userSex, userAge, userAddress, bookingClient, clientPhone);
        return getResult(b);
    }

    /**
     * 服务--取消预约
     *
     * @return
     */
    public static String cancelBooking(String id, String serviceItems, String name, String departments, String bookingTime, String golfName) {
        CancelBooking0B b = new CancelBooking0B(id, serviceItems, name, departments, bookingTime, golfName);
        return getResult(b);
    }

    /**
     * 保险预约
     *
     * @return
     */
    public static String subBookingInsurance(String productId, String userInfoId, String bookingRemark, String bookingAmount) {
        BookingInsurance0B b = new BookingInsurance0B(productId, userInfoId, bookingRemark, bookingAmount);
        return getResult(b);
    }

    /**
     * 非保险预约
     *
     * @return
     */
    public static String subBookingProduct(String productId, String userInfoId, String bookingRemark, String bookingAmount, String type) {
        BookingProduct0B b = new BookingProduct0B(productId, userInfoId, bookingRemark, bookingAmount, type);
        return getResult(b);
    }

    /**
     * 服务--展示预约高尔夫球场列表
     *
     * @return
     */
    public static String getGolfList(String page) {
        GolfList0B b = new GolfList0B(page);
        return getResult(b);
    }

    /**
     * 服务--高尔夫球场详情
     *
     * @return
     */
    public static String getGolfDetail(String id) {
        GolfDetail0B b = new GolfDetail0B(id);
        return getResult(b);
    }

    /**
     * 服务--提交高尔夫球场预约
     *
     * @return
     */
    public static String submitGolf(String bookingTime, String clientPhone, String golfId, String peersOne, String peersTwo) {
        SubmitBookingGolf0B b = new SubmitBookingGolf0B(bookingTime, clientPhone, golfId, peersOne, peersTwo);
        return getResult(b);
    }

    /**
     * 服务--显示服务首页图片
     *
     * @return
     */
    public static String getServicePicture(String id) {
        ServicePicture0B b = new ServicePicture0B(id);
        return getResult(b);
    }

    /**
     * 服务--展示豪华游轮列表
     *
     * @return
     */
    public static String getLinerList(String page) {
        LinerList0B b = new LinerList0B(page);
        return getResult(b);
    }

    /**
     * 服务--游轮详情
     *
     * @return
     */
    public static String getLinerDetail(String id) {
        LinerDetail0B b = new LinerDetail0B(id);
        return getResult(b);
    }

    /**
     * 服务--游轮详情之游轮信息
     *
     * @return
     */
    public static String getLinerInfo(String id) {
        LinerInfo0B b = new LinerInfo0B(id);
        return getResult(b);
    }

    /**
     * 邮轮提交预约
     *
     * @return
     */
    public static String subBookingShip(String clientPhone, String shipId, String clientName) {
        SubBookingShip0B b = new SubBookingShip0B(clientPhone, shipId, clientName);
        return getResult(b);
    }

    /**
     * 更多模块是否显示小红点
     *
     * @return
     */
    public static String getBulletinUnreadCount() {
        String b = "";
        return getResult(b);
    }

    /**
     * 服务--展示海外项目列表
     *
     * @return
     */
    public static String getOverseaList(String page) {
        OverseaProjectList0B b = new OverseaProjectList0B(page);
        return getResult(b);
    }

    /**
     * 服务--展示海外项目详情
     *
     * @return
     */
    public static String getOverseaDetail(String pid) {
        OverseaProjectDetail0B b = new OverseaProjectDetail0B(pid);
        return getResult(b);
    }

    /**
     * 服务--提交基因检测预约
     *
     * @return
     */
    public static String subOverseaProject(String client, String clientPhone, String houseId, String financial) {
        SubOverseaProject0B b = new SubOverseaProject0B(client, clientPhone, houseId, financial);
        return getResult(b);
    }

    /**
     * 服务--提交海外医疗预约
     *
     * @param client // 预约人
     * @param clientPhone // 预约电话
     * @param overseasType // 海外医疗预约类型
     * @param financial // 专属理财师
     * @return
     */
    public static String subOverseasMedical(String client, String clientPhone, String overseasType, String financial) {
        SubOverseasMedical0B b = new SubOverseasMedical0B(client, clientPhone, overseasType, financial);
        return getResult(b);
    }

    /**
     *
     * @param client  // 预约人
     * @param clientPhone // 预约电话
     * @param financial // 专属理财师
     * @return
     */
    public static String subPhotography(String client, String clientPhone,String financial) {
        SubPhotography0B b = new SubPhotography0B(client, clientPhone,financial);
        return getResult(b);
    }


}
