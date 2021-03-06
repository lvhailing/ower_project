package com.jdhui;

public class ApplicationConsts {
    public static final int REL_DEBUG = 1;// 线下服务器
    public static final int REL_OFFICIAL = 2;// 正式服务器

    public static int RELEASE_FLAG = REL_DEBUG;            //测试环境
//	public static int RELEASE_FLAG = REL_OFFICIAL;          //正式环境

    // 线下服务器开关,线下地址优先级高于预发布地址
    public static final boolean DEBUG = RELEASE_FLAG == REL_DEBUG;

    // 正式服务器开关
    public static final boolean OFFICIAL = RELEASE_FLAG == REL_OFFICIAL;

    //代树理
//	public static final String URL_DEBUG ="http://192.168.1.106:9999/junde-hui-app/";

    // 张殿洋
//    public static final String URL_DEBUG = "http://192.168.1.138:9999/junde-hui-app/";

    //张亚磊
//	public static final String URL_DEBUG ="http://192.168.1.193:9996/junde-hui-app/";

    // 沈南
	public static final String URL_DEBUG ="http://192.168.1.138:9999/junde-hui-app/";


    //测试环境
//	public static final String URL_DEBUG ="http://192.168.1.86:82/";

// 张殿阳
//	public static final String URL_DEBUG ="http://192.168.1.138:9999/junde-hui-app/";

    //张亚磊
//	public static final String URL_DEBUG ="http://192.168.1.193:9996/junde-hui-app/";

    // 邢玉洁
//    public static final String URL_DEBUG = "http://192.168.1.125:9999/junde-hui-app/";

    // 正式环境IP M版
	 public static final String URL_DEBUG_M ="http://m.vjinke.com/";


    // 正式环境IP
//    public static final String URL_OFFICIAL = "http://app.jundehui.cf360.com/";
    public static final String URL_OFFICIAL = "https://jdhapp.cf360.com/";

    // 下载更新IP地址
    public static final String URL_DOWNLOAD = "https://console.vjinketest.com/";

    // 开发使用
    public static final String EC_HOST_T = URL_DEBUG;
    // 开发使用
    public static final String EC_HOST_P = URL_OFFICIAL;

    public static final String EC_HOST = DEBUG ? EC_HOST_T : EC_HOST_P;

    // 图片路径
    public static final String IMG_HOSE = "http://192.168.1.90:9999/customerApp/";

    // 轮播图图片路径
    public static final String IMG_INPAGER = "http://192.168.1.89:90/WjkPictureServer/";
    /***
     * 邀请奖励规则
     */
    public static final String URL_INVITE_AWARD_RULE = EC_HOST + "invitefriends/inviterewardrule";
    /***
     * 专享产品说明
     */
    public static final String URL_AGREEMENT_ZHUANHU_STATE = EC_HOST + "product/checkcode/product/exc_products_shuom";
    /***
     * 专享投资协议
     */
    public static final String URL_AGREEMENT_ZHUANHU = EC_HOST + "product/checkcode/product/agreement";
    /***
     * 首页
     */
    public static final String URL_HOME = EC_HOST + "index";
    /***
     * 意见反馈
     */
    public static final String URL_ADVICE = EC_HOST + "problem/reply/save";

    /**
     * 充值
     */
    public static final String URL_RECHARGE = EC_HOST + "account/qp/recharge/do";

    /**
     * 找回密码
     */
    public static final String URL_FINDPWDBYPHONE = EC_HOST + "account/password/find";
    /**
     * 更多--获取我的信息
     */
    public static final String URL_MY_INFO = EC_HOST + "user/person/info";
    /**
     * 获取更多信息
     */
    public static final String URL_MORE_INFO = EC_HOST + "more/info";
    /**
     * 资产首页
     */
    public static final String URL_ACCOUNT_INDEX = EC_HOST + "account/index";

    /**
     * 投资列表
     */
    public static final String URL_ACCOUNT_PRODUCT_TENDERS = EC_HOST + "account/product/tenders";

    /**
     * 非保险产品列表
     */
    public static final String URL_PRODUCT_LIST = EC_HOST + "product/list";

    /**
     * 保险产品列表
     */
    public static final String URL_INSURANCE_PRODUCT_LIST = EC_HOST + "insurance/list";

    /**
     * 非保险产品详情
     */
    public static final String URL_PRODUCT_DETAIL = EC_HOST + "product/detail";

    /**
     * 保险产品详情
     */
    public static final String URL_INSURANCE_PRODUCT_DETAIL = EC_HOST + "insurance/detail";
    /**
     * 保险产品详情图文详情
     */
    public static final String URL_INSURANCE_PRODUCT_DETAIL_DES = EC_HOST + "insurance/detail/graphicDetails/";
    /**
     * 年度报告
     */
    public static final String URL_INSURANCE_PRODUCT_DETAIL_REPORT = EC_HOST + "product/detail/annualReport/";
    /**
     * 非保险投资产品详情
     */
    public static final String URL_ASSET_PRODUCT_DETAIL = EC_HOST + "account/product/tenders/details";

    /**
     * 保险投资产品详情
     */
    public static final String URL_ASSET_INSURANCE_PRODUCT_DETAIL = EC_HOST + "account/insurance/tenders/details";

    /**
     * 产品首页
     */
    public static final String URL_PRODUCT_INDEX = EC_HOST + "website/hotsell/list";

    /**
     * 轮播图
     */
    public static final String URL_CYCLE_ADVERTISE_INDEX = EC_HOST + "turn/advertise";

    /**
     * 资产--消息列表
     */
    public static final String URL_MESSAGE_LIST_INDEX = EC_HOST + "user/message/list";
    /**
     * 消息详情
     */
    public static final String URL_MESSAGE_DETAILS = EC_HOST + "user/message/detail/";

    /***
     * 修改手机号验证码验证
     */
    public static final String URL_CHANGEPHONEVERIFY = EC_HOST + "account/mobile/verify";

    /**
     * 修改手机号
     */
    public static final String URL_BINDPHONE = EC_HOST + "account/mobile/bind";

    /**
     * 修改密码
     */
    public static final String URL_CHANGEPWD = EC_HOST + "account/password/modify";

    /**
     * 找回密码验证
     */
    public static final String URL_FINDPWDVERIFY = EC_HOST + "password/verify";

    /***
     * 注册
     */
    public static final String URL_SIGNUP = EC_HOST + "android/user/register";

    /**
     * 登陆
     */
    // public static final String URL_LOGIN = EC_HOST + "user/login";
    public static final String URL_LOGIN = EC_HOST + "android/user/login";

    /**
     * 检查版本
     */
    public static final String URL_CHECKVERSION = EC_HOST + "version/check";

    /**
     * 获取轮播图
     * */
//	public static final String URL_GETUPDATEPHOTOSTATE = EC_HOST + "turn/advertise";

    /**
     * 获取图片
     */
    public static final String URL_GETPHOTOSTATE = EC_HOST + "turn/advertise";
    /**
     * 上传头像
     */
    public static final String URL_UPLOADPHOTO = EC_HOST + "android/account/photo/upload";

    /**
     * 登出
     */
    public static final String URL_LOGINOFF = EC_HOST + "account/user/logoff";

    /**
     * 发送手机短信
     */
    public static final String URL_SMS = EC_HOST + "user/mobile/send/verifycode";
    /**
     * 更多--产品预约
     */
    public static final String URL_PRODUCT_ORDER = EC_HOST + "/product/booking/list";

    /**
     * 产品预约详情
     */
    public static final String URL_PRODUCT_ORDER_DETAIL = EC_HOST + "/product/booking/details";

    /**
     * 更多--服务预约列表
     */
    public static final String URL_SERVICE_ORDER = EC_HOST + "service/bookingList/list";

    /**
     * 服务预约详情
     */
    public static final String URL_SERVICE_DETAIL = EC_HOST + "/service/bookingList/view";

    /**
     * 更多--君德公告
     */
    public static final String URL_NOTICE = EC_HOST + "website/bulletin/list";

    /**
     * 君德公告详情
     */
    public static final String URL_NOTICE_DETAILS = EC_HOST + "website/bulletin/detail/";

    /**
     * 快讯
     */
    public static final String URL_NEWS = EC_HOST + "news/list";

    /**
     * 快讯详情
     */
    public static final String URL_NEWS_DETAILS = EC_HOST + "news/detail/";

    /**
     * 验证登录=密码
     */
    public static final String URL_VERIFY_PASSWORD = EC_HOST + "account/user/password/valid";

    /**
     * 保存修改后手机号
     */
    public static final String URL_SAVE_PHONE = EC_HOST + "account/mobile/bind";

    /**
     * 验证修改手机号
     */
    public static final String URL_MOBILE_CHECK = EC_HOST + "account/mobile/check";

    /**
     * 保存修改后的地址
     */
    public static final String URL_SAVE_ADDRESS = EC_HOST + "account/user/address/save";

    /**
     * 意见反馈
     */
    public static final String URL_FEED_BACK = EC_HOST + "problem/reply/save";

    /**
     * 我同意该承诺、跳过 接口
     */
    public static final String URL_INVESTOR_JUDGE_SAVE = EC_HOST + "user/questionnaires/investor/judge/save";

    /**
     * 问卷调查
     */
    public static final String URL_SURVEY = EC_HOST + "user/questionnaires/survey/";

    /**
     * 填写问卷
     */
    public static final String URL_WRITTEN = EC_HOST + "android/user/questionnaires/written/";

    /**
     * 问卷评估
     */
    public static final String URL_ASSESSMENT = EC_HOST + "user/questionnaires/assessment/";

    /**
     * 投资者承诺函
     */
    public static final String URL_INVESTOR_COMMITMENT = EC_HOST + "user/questionnaires/investor/commitment/";

    /**
     * 投资者判定
     */
    public static final String URL_INVESTOR_JUDGE = EC_HOST + "user/questionnaires/investor/judge/";

    /**
     * 关于我们
     */
    public static final String URL_ABOUT_US = EC_HOST + "regarding/us";

    /**
     * 服务条款
     */
    public static final String URL_SERVERS = EC_HOST + "servers/list ";

    /**
     * 隐私协议
     */
    public static final String URL_AGREEMENTS = EC_HOST + "privacy/agreements";

    /**
     * 版本号
     */
    public static final String URL_VERSION_NUMBER = EC_HOST + "android/version?num=";

    /**
     *  海外医疗(三个H5页面)
     */
    public static final String URL_OVERSEAS_MEDICAL = EC_HOST + "service/bookingOverseas/";

    /**
     * 提交预约医院
     */
    public static final String URL_SERVICE_BOOKINGHOSPITAL = EC_HOST + "service/bookingHospital/add";

    /**
     * 展示预约医院列表
     */
    public static final String URL_SERVICE_BOOKINGHOSPITAL_LIST = EC_HOST + "service/bookingHospital/list";

    /**
     * 展示预约高尔夫球场地列表
     */
    public static final String URL_SERVICE_GOLF_LIST = EC_HOST + "service/golf/list";

    /**
     * 展示预约基因检测列表
     */
    public static final String URL_SERVICE_BOOKINGGENETICTESTING_LIST = EC_HOST + "service/bookingGeneticTesting/list";

    /**
     * 展示预约基因检测详情
     */
    public static final String URL_SERVICE_BOOKINGGENETICTESTING_DETAIL = EC_HOST + "service/bookingGeneticTesting/view";

    /**
     * 提交基因检测预约
     */
    public static final String URL_SERVICE_BOOKINGGENETICTESTING_ADD = EC_HOST + "service/bookingGeneticBooking/add";

    /**
     * 提交海外医疗预约
     */
    public static final String URL_SERVICE_BOOKINGOVERSEAS_ADD = EC_HOST + "service/bookingOverseas/add";

    /**
     * 提交私人摄影预约
     */
    public static final String URL_SERVICE_BOOKINGPHOTOGRAPHY_ADD = EC_HOST + "service/bookingPhotography/add";

    /**
     * 保险预约
     */
    public static final String URL_INSURANCE_BOOKING = EC_HOST + "insurance/booking";

    /**
     * 非保险预约
     */
    public static final String URL_PRODUCT_BOOKING = EC_HOST + "product/booking";

    /**
     * 高尔夫球场详情
     */
    public static final String URL_SERVICE_GOLF_VIEW = EC_HOST + "service/golf/view";

    /**
     * 提交高尔夫预约
     */
    public static final String BOOKING_GOLF = EC_HOST + "service/bookingGolf/add";

    /**
     * 获取高尔夫提交预约时的客户姓名和身份证
     */
    public static final String URL_GET_INFO = EC_HOST + "service/golf/userMsg";

    /**
     * 显示服务首页图片
     */
//    public static final String URL_TURN_SERVICE_PICTURE = EC_HOST + "turn/servicePicture";
    public static final String URL_TURN_SERVICE_PICTURE = EC_HOST + "turn/servicePicture/NewVersion";

    /**
     * 更多--取消服务预约
     */
    public static final String URL_SERVICE_BOOKING_CANCEL = EC_HOST + "service/booking/cancel";

    /**
     * 展示豪华游轮列表
     */
    public static final String URL_SERVICE_SHIP_LIST = EC_HOST + "service/luxuryShip/list";

    /**
     * 游轮详情
     */
    public static final String URL_SERVICE_SHIP_VIEW = EC_HOST + "service/luxuryShip/view";

    /**
     * 游轮详情
     */
    public static final String URL_SERVICE_SHIP_VIEW_INFO = EC_HOST + "service/luxuryShip/view/info";

    /**
     * 游轮提交预约
     */
    public static final String URL_SERVICE_BOOKINGLUXURYSHIP_ADD = EC_HOST + "service/bookingLuxuryShip/add";

    /**
     * 更多模块 是否显示小红点
     */
    public static final String URL_TAB_MORE = EC_HOST + "website/bulletin/getBulletinUnreadCount";

    /**
     * 服务 -- 海外项目列表
     */
    public static final String URL_PROJECT_LIST = EC_HOST + "project/list";

    /**
     * 服务 -- 海外项目详情
     */
    public static final String URL_PROJECT_DETAIL = EC_HOST + "project/detail";

    /**
     * 提交海外项目预约
     */
    public static final String URL_SERVICE_BOOKINGHOUSE_ADD = EC_HOST + "service/bookingHouse/add";



    // 手势密码点的状态
    public static final int POINT_STATE_NORMAL = 0; // 正常状态

    public static final int POINT_STATE_SELECTED = 1; // 按下状态

    public static final int POINT_STATE_WRONG = 2; // 错误状态

    /**
     * register:用户注册
     */
    public static final String REGISTER = "register";
    /**
     * loginRet:登录密码找回
     */
    public static final String LOGINRET = "loginRet";
    /**
     * mobileBind:绑定手机
     */
    public static final String MOBILEBIND = "mobileBind";
    /**
     * mobileEdit:修改手机
     */
    public static final String MOBILEEDIT = "mobileEdit";

    public static final String SUCCESS = "0000";
    public static final String FAIL_NOLOGIN = "1000";

    public static final String ACTIVITY_SPLASH = "activity_splash";
    public static final String ACTIVITY_GESEDIT = "activity_gesedit";
    public static final String ACTIVITY_GESVERIFY = "activity_gesverify";
    public static final String ACTIVITY_ACCOUNTSET = "activity_accountset";
    public static final String ACTIVITY_ACCOUNT = "activity_account";
    public static final String ACTIVITY_CHANGE_GESTURE = "activity_change_gesture";

    /**
     * 预约公务机包机
     */
    public static final String URL_AIR_PLANE_BOOKING = EC_HOST + "airplaneBooking/edit/android/";

}
