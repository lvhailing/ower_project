package com.jdhui.act.ac;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.BookingInsurance2B;
import com.jdhui.bean.mybean.ServiceDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 更多--服务预约详情（绿通就医，基因检测，高尔夫球场，豪华邮轮游，海外资产配置，海外医疗）
 */
public class ServiceOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_order_service; //预约服务
    private TextView tv_order_project; // 预约项目
    private TextView tv_order_status; //预约状态
    private TextView tv_order_name; //预约人
    private TextView tv_travel_name; //游行名称
    private TextView tv_sex; //性别
    private TextView tv_age; //年龄
    private TextView tv_genetic_test_name; //基因 检测套餐
    private TextView tv_genetic_test_address; //基因 通讯地址
    private TextView tv_social_security_num; //社保号码
    private TextView tv_id_num; //身份证号
    private TextView tv_phone; //联系电话
    private TextView tv_spare_time1; //备选时间1
    private TextView tv_spare_time2; //备选时间2
    private TextView tv_order_hospital; //预约医院
    private TextView tv_order_department; //预约科室

    private TextView tv_order_doctor; //预约医生
    private TextView tv_desc_illness; //主诉病情
    private TextView tv_order_time; //预约时间
    private TextView tv_sub_time; //提交时间
    private TextView tv_venue_name; //场馆名称

    private String id; //服务id
    private String serviceItems; //服务类型
    private ServiceDetail2B detail2B;

    private LinearLayout ll_order_name; // 预约人
    private LinearLayout ll_travel_name; // 游行名称
    private LinearLayout ll_security_num; // 社保号
    private LinearLayout ll_id_num; // 身份证号
    private LinearLayout ll_phone; // 联系电话
    private LinearLayout ll_order_time; // 预约时间
    private LinearLayout ll_spare_time1; // 备选时间1
    private LinearLayout ll_spare_time2; // 备选时间2
    private LinearLayout ll_order_hospital; // 预约医院
    private LinearLayout ll_order_department; // 预约科室
    private LinearLayout ll_order_doctor; // 预约医生
    private LinearLayout ll_desc_illness; //主诉病情

    private LinearLayout ll_sex; //性别
    private LinearLayout ll_age; //年龄
    private LinearLayout ll_genetic_test; //检测套餐
    private LinearLayout ll_address; //通讯地址
    private LinearLayout ll_financial_planner; // 专属理财师

    private LinearLayout ll_venue_name; //场馆名称
    private LinearLayout ll_order_status; //预约状态
    private LinearLayout ll_sub_time; //提交时间
    private LinearLayout ll_together1; //同行人1
    private LinearLayout ll_together2; //同行人2
    private TextView tv_together1;
    private TextView tv_together2;
    private RelativeLayout rl_tips; //顶部提示语布局
    private TextView tv_title;//顶部提示语
    private Button btn_cancel;
    private TextView tv_id_num_name; // 证件名称
    private TextView tv_financial_planner; // 理财师名称
    private LinearLayout ll_order_project; // 预约项目 布局
    private String overseasType; // 预约的海外医疗项目类型


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_service_order_detail);

        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_order_service = (TextView) findViewById(R.id.tv_order_service);
        tv_order_name = (TextView) findViewById(R.id.tv_order_name);
        tv_travel_name = (TextView) findViewById(R.id.tv_travel_name);
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_order_project = (TextView) findViewById(R.id.tv_order_project);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_genetic_test_name = (TextView) findViewById(R.id.tv_genetic_test_name);
        tv_genetic_test_address = (TextView) findViewById(R.id.tv_genetic_test_address);
        tv_social_security_num = (TextView) findViewById(R.id.tv_social_security_num);
        tv_id_num = (TextView) findViewById(R.id.tv_id_num);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_spare_time1 = (TextView) findViewById(R.id.tv_spare_time1);
        tv_spare_time2 = (TextView) findViewById(R.id.tv_spare_time2);
        tv_order_hospital = (TextView) findViewById(R.id.tv_order_hospital);
        tv_order_department = (TextView) findViewById(R.id.tv_order_department);
        tv_order_doctor = (TextView) findViewById(R.id.tv_order_doctor);
        tv_desc_illness = (TextView) findViewById(R.id.tv_desc_illness);
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);
        tv_sub_time = (TextView) findViewById(R.id.tv_sub_time);
        tv_venue_name = (TextView) findViewById(R.id.tv_venue_name);
        tv_together1 = (TextView) findViewById(R.id.tv_together1);
        tv_together2 = (TextView) findViewById(R.id.tv_together2);
        tv_id_num_name = (TextView) findViewById(R.id.tv_id_num_name);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_financial_planner = (TextView) findViewById(R.id.tv_financial_planner);

        ll_order_project = (LinearLayout) findViewById(R.id.ll_order_project);
        ll_order_name = (LinearLayout) findViewById(R.id.ll_order_name);
        ll_travel_name = (LinearLayout) findViewById(R.id.ll_travel_name);
        ll_security_num = (LinearLayout) findViewById(R.id.ll_security_num);
        ll_id_num = (LinearLayout) findViewById(R.id.ll_id_num);
        ll_spare_time1 = (LinearLayout) findViewById(R.id.ll_spare_time1);
        ll_spare_time2 = (LinearLayout) findViewById(R.id.ll_spare_time2);
        ll_order_hospital = (LinearLayout) findViewById(R.id.ll_order_hospital);
        ll_order_department = (LinearLayout) findViewById(R.id.ll_order_department);
        ll_order_doctor = (LinearLayout) findViewById(R.id.ll_order_doctor);
        ll_desc_illness = (LinearLayout) findViewById(R.id.ll_desc_illness);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        ll_sex = (LinearLayout) findViewById(R.id.ll_sex);
        ll_age = (LinearLayout) findViewById(R.id.ll_age);
        ll_genetic_test = (LinearLayout) findViewById(R.id.ll_genetic_test);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_order_status = (LinearLayout) findViewById(R.id.ll_order_status);
        ll_venue_name = (LinearLayout) findViewById(R.id.ll_venue_name);
        ll_sub_time = (LinearLayout) findViewById(R.id.ll_sub_time);
        ll_together1 = (LinearLayout) findViewById(R.id.ll_together1);
        ll_together2 = (LinearLayout) findViewById(R.id.ll_together2);
        ll_order_time = (LinearLayout) findViewById(R.id.ll_order_time);
        ll_financial_planner = (LinearLayout) findViewById(R.id.ll_financial_planner);
        rl_tips = (RelativeLayout) findViewById(R.id.rl_tips);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        iv_back.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        serviceItems = getIntent().getStringExtra("serviceItems");

        requestDetailData();
    }

    /**
     * 请求服务详情页数据（公务机除外）
     */
    private void requestDetailData() {
        HtmlRequest.getServiceDetail(this, serviceItems, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    detail2B = (ServiceDetail2B) params.result;
                    if (detail2B != null) {
                        setView();
                    }
                }
            }
        });
    }

    private void setView() {
        //绿通就医：hospitalBooking; 基因检测：geneticBooking; 高尔夫球场：golfBooking; 豪华游轮:luxuryShipBooking;  海外资产配置：houseBooking;

        if (detail2B.getHospitalBooking() != null) {
            //是绿通就医
            tv_order_service.setText("绿通就医");

            ll_order_name.setVisibility(View.VISIBLE); // 预约人
            ll_security_num.setVisibility(View.VISIBLE); // 社保号
            ll_id_num.setVisibility(View.VISIBLE); // 身份证号
            ll_phone.setVisibility(View.VISIBLE); // 联系电话
            ll_order_time.setVisibility(View.VISIBLE);  // 预约时间
            ll_spare_time1.setVisibility(View.VISIBLE);  //备选时间1
            ll_spare_time2.setVisibility(View.VISIBLE); //备选时间2
            ll_order_hospital.setVisibility(View.VISIBLE); // 预约医院
            ll_order_department.setVisibility(View.VISIBLE); // 预约科室
            ll_order_doctor.setVisibility(View.VISIBLE); // 预约医生
            ll_desc_illness.setVisibility(View.VISIBLE); //主诉病情
            ll_sub_time.setVisibility(View.VISIBLE); //提交时间

            String status = detail2B.getHospitalBooking().getBookingStatus();
            changeStatus(status, detail2B.getHospitalBooking().getBookingRemark());
            tv_order_name.setText(detail2B.getHospitalBooking().getBookingClient());  //预约人
            tv_social_security_num.setText(detail2B.getHospitalBooking().getSecurityNum()); //社保号码
            tv_id_num.setText(detail2B.getHospitalBooking().getClientIdNo()); //身份证号
            tv_phone.setText(detail2B.getHospitalBooking().getClientPhone()); //联系电话
            tv_order_time.setText(detail2B.getHospitalBooking().getBookingTime()); //预约时间

            tv_spare_time1.setText(!TextUtils.isEmpty(detail2B.getHospitalBooking().getBakTimeOne()) ? detail2B.getHospitalBooking().getBakTimeOne() : "--"); //备选时间1
            tv_spare_time2.setText(!TextUtils.isEmpty(detail2B.getHospitalBooking().getBakTimeTwo()) ? detail2B.getHospitalBooking().getBakTimeTwo() : "--"); //备选时间2
            tv_order_hospital.setText(detail2B.getHospitalBooking().getName()); //预约医院
            tv_order_department.setText(detail2B.getHospitalBooking().getDepartments()); //预约科室
            tv_order_doctor.setText(!TextUtils.isEmpty(detail2B.getHospitalBooking().getDoctor()) ? detail2B.getHospitalBooking().getDoctor() : "--"); //预约医生
            tv_desc_illness.setText(!TextUtils.isEmpty(detail2B.getHospitalBooking().getIllnessCondition()) ? detail2B.getHospitalBooking().getIllnessCondition() : "--"); //主诉病情
            tv_sub_time.setText(detail2B.getHospitalBooking().getCreateTime()); //提交时间

        } else if (detail2B.getGeneticBooking() != null) {
            //是基因检测
            tv_order_service.setText("基因检测");

            ll_order_name.setVisibility(View.VISIBLE); // 预约人
            ll_sex.setVisibility(View.VISIBLE); // 性别
            ll_age.setVisibility(View.VISIBLE); // 年龄
            ll_genetic_test.setVisibility(View.VISIBLE); // 检测套餐
            ll_phone.setVisibility(View.VISIBLE); // 联系电话
            ll_address.setVisibility(View.VISIBLE); // 通讯地址
            ll_sub_time.setVisibility(View.VISIBLE); // 提交时间

            String status = detail2B.getGeneticBooking().getBookingStatus();
            changeStatus(status, detail2B.getGeneticBooking().getBookingRemark());
            tv_order_name.setText(detail2B.getGeneticBooking().getBookingClient());  //预约人
            tv_sex.setText(detail2B.getGeneticBooking().getUserSex());  //预约人性别
            tv_age.setText(detail2B.getGeneticBooking().getUserAge());  //预约人年龄
            tv_genetic_test_name.setText(detail2B.getGeneticBooking().getName());  //基因 检测套餐
            tv_phone.setText(detail2B.getGeneticBooking().getClientPhone()); //联系电话
            tv_genetic_test_address.setText(detail2B.getGeneticBooking().getUserAddress());  //基因 通讯地址
            tv_sub_time.setText(detail2B.getGeneticBooking().getBookingTime()); //提交时间

        } else if (detail2B.getGolfBooking() != null) {
            //是高尔夫
            tv_order_service.setText("高尔夫球场地");

            String golfright = detail2B.getGolfBooking().getGolfRight();
            if (golfright.equals("A2") || golfright.equals("VIP")) { //如果是会员显示同行人，其他权限则隐藏同行人
                ll_together1.setVisibility(View.VISIBLE);
                ll_together2.setVisibility(View.VISIBLE);

                tv_together1.setText(!TextUtils.isEmpty(detail2B.getGolfBooking().getPeersOne()) ? detail2B.getGolfBooking().getPeersOne() : "--");  //同行人1
                tv_together2.setText(!TextUtils.isEmpty(detail2B.getGolfBooking().getPeersTwo()) ? detail2B.getGolfBooking().getPeersTwo() : "--");  //同行人2
            }

            ll_order_name.setVisibility(View.VISIBLE);
            ll_id_num.setVisibility(View.VISIBLE);
            ll_phone.setVisibility(View.VISIBLE);
            ll_venue_name.setVisibility(View.VISIBLE);
            ll_order_time.setVisibility(View.VISIBLE);
            ll_sub_time.setVisibility(View.VISIBLE);

            String status = detail2B.getGolfBooking().getBookingStatus();
            changeStatus(status, detail2B.getGolfBooking().getBookingRemark());
            tv_order_name.setText(detail2B.getGolfBooking().getName());  //预约人

            //idType  String     idCard: 身份证号  passport：护照  agencyCode：机构代码
            if (!TextUtils.isEmpty(detail2B.getGolfBooking().getIdType())) {
                if (detail2B.getGolfBooking().getIdType().equals("idCard")) {
                    tv_id_num_name.setText("身份证号:");
                } else if (detail2B.getGolfBooking().getIdType().equals("passport")) {
                    tv_id_num_name.setText("护        照:");
                } else if (detail2B.getGolfBooking().getIdType().equals("agencyCode")) {
                    tv_id_num_name.setText("机构代码:");
                }
            }

            tv_id_num.setText(detail2B.getGolfBooking().getIdNo());  //身份证


            tv_phone.setText(detail2B.getGolfBooking().getClientPhone());  //联系电话
            tv_venue_name.setText(detail2B.getGolfBooking().getGolfName());  //场馆名称
            tv_order_time.setText(detail2B.getGolfBooking().getBookingTime()); //预约时间
            tv_sub_time.setText(detail2B.getGolfBooking().getCreateTime()); //提交时间
        } else if (detail2B.getLuxuryShipBooking() != null) {
            //是豪华邮轮游
            tv_order_service.setText("豪华邮轮游");

            ll_travel_name.setVisibility(View.VISIBLE);//游行名称
            ll_order_name.setVisibility(View.VISIBLE);//预约人
            ll_phone.setVisibility(View.VISIBLE);//联系电话布局
            ll_sub_time.setVisibility(View.VISIBLE);//提交时间布局

            String status = detail2B.getLuxuryShipBooking().getBookingStatus();
            changeStatus(status, detail2B.getLuxuryShipBooking().getBookingRemark());

            tv_travel_name.setText(detail2B.getLuxuryShipBooking().getRouteName());  //游行名称
            tv_order_name.setText(detail2B.getLuxuryShipBooking().getClientName());  //预约人
            tv_phone.setText(detail2B.getLuxuryShipBooking().getClientPhone());  //联系电话
            tv_sub_time.setText(detail2B.getLuxuryShipBooking().getCreateTime()); //提交时间

        } else if (detail2B.getHouseBooking() != null) {
            // 海外资产配置
            tv_order_service.setText("海外资产配置"); // 预约的服务

            ll_order_name.setVisibility(View.VISIBLE);//预约人
            ll_phone.setVisibility(View.VISIBLE);//联系电话布局
            ll_financial_planner.setVisibility(View.VISIBLE); // 专属理财师布局
            ll_sub_time.setVisibility(View.VISIBLE);//提交时间布局

            // 预约状态
            String status = detail2B.getHouseBooking().getBookingStatus();
            changeStatus(status, detail2B.getHouseBooking().getBookingRemark());

            tv_financial_planner.setText(detail2B.getHouseBooking().getFinancial());  // 专属理财师
            tv_order_name.setText(detail2B.getHouseBooking().getClient());  //预约人
            tv_phone.setText(detail2B.getHouseBooking().getClientPhone());  //联系电话
            tv_sub_time.setText(detail2B.getHouseBooking().getCreateTime()); //提交时间
        } else if (detail2B.getOverseasBooking() != null) {
            // 海外医疗
            tv_order_service.setText("海外医疗"); // 预约的服务
            ll_order_project.setVisibility(View.VISIBLE); // 预约项目 布局
            ll_order_name.setVisibility(View.VISIBLE);//预约人 布局
            ll_phone.setVisibility(View.VISIBLE);//联系电话 布局
            ll_financial_planner.setVisibility(View.VISIBLE); // 专属理财师布局
            ll_sub_time.setVisibility(View.VISIBLE);//提交时间布局


            if (detail2B.getOverseasBooking().getOverseasType() != null) {
                overseasType = detail2B.getOverseasBooking().getOverseasType();
            }
            String type = "";
            if (overseasType.equals("examination")) {
                type = "海外体检";
            } else if (overseasType.equals("hospital")) {
                type = "海外就医";
            } else if (overseasType.equals("consultation")) {
                type = "国际远程会诊";
            }
            tv_order_project.setText(type); // 预约项目
            // 预约状态
            String status = detail2B.getOverseasBooking().getBookingStatus();
            changeStatus(status, detail2B.getOverseasBooking().getBookingRemark());
            tv_financial_planner.setText(detail2B.getOverseasBooking().getFinancial());  // 专属理财师
            tv_order_name.setText(detail2B.getOverseasBooking().getClient());  //预约人
            tv_phone.setText(detail2B.getOverseasBooking().getClientPhone());  //联系电话
            tv_sub_time.setText(detail2B.getOverseasBooking().getCreateTime()); //提交时间
        }

    }

    // 预约状态（待确认，未完成，已驳回等状态时，显示提示信息）
    private void changeStatus(String status, String bookingRemark) {
        if (status.equals("submit")) {
            tv_order_status.setText("待确认");

            rl_tips.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
        } else if (status.equals("confirm")) {
            tv_order_status.setText("已确认");
        } else if (status.equals("finish")) {
            tv_order_status.setText("已完成");
        } else if (status.equals("unfinish")) {
            tv_order_status.setText("未完成");

            rl_tips.setVisibility(View.VISIBLE);
            tv_title.setText(bookingRemark);
        } else if (status.equals("refuse")) {
            tv_order_status.setText("已驳回");

            rl_tips.setVisibility(View.VISIBLE);
            tv_title.setText(bookingRemark);
        } else if (status.equals("cancel")) {
            tv_order_status.setText("已取消");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: // 返回
                finish();
                break;
            case R.id.btn_cancel: // 取消
                cancel();
                break;
        }
    }

    private void cancel() {
        //让按钮不可点
        btn_cancel.setEnabled(false);
        btn_cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_button_gray_gray));

        String serviceItems = "";   //服务类型
        String name = "";   //医院名称
        String departments = "";   //科室
        String bookingTime = "";   //预约时间
        String golfName = "";   //高尔夫球场名称

        if (detail2B.getHospitalBooking() != null) {
            // 是绿通就医
            serviceItems = "hospitalBooking";
            name = detail2B.getHospitalBooking().getName();
            departments = detail2B.getHospitalBooking().getDepartments();
            bookingTime = detail2B.getHospitalBooking().getBookingTime();
        } else if (detail2B.getGeneticBooking() != null) {
            // 是基因检测
            serviceItems = "geneticBooking";
        } else if (detail2B.getGolfBooking() != null) {
            // 是高尔夫
            serviceItems = "golfBooking";
            golfName = detail2B.getGolfBooking().getGolfName();
            bookingTime = detail2B.getGolfBooking().getBookingTime();
        } else if (detail2B.getLuxuryShipBooking() != null) {
            // 是豪华邮轮
            serviceItems = "luxuryShipBooking";
        } else if (detail2B.getHouseBooking() != null) {
            // 海外资产配置
            serviceItems = "houseBooking";
        }

        // 点取消按钮时调接口
        HtmlRequest.cancelBooking(this, id, serviceItems, name, departments, bookingTime, golfName, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params == null) {
                    cancelFailure();
                    return;

                }
                BookingInsurance2B bookingInsurance2B = (BookingInsurance2B) params.result;
                if (bookingInsurance2B == null || Boolean.parseBoolean(bookingInsurance2B.getMessage())) {
                    cancelFailure();
                    return;
                }
                Toast.makeText(ServiceOrderDetailActivity.this, "取消成功", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    // 取消失败时调的方法
    private void cancelFailure() {
        Toast.makeText(ServiceOrderDetailActivity.this, "取消预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
        //让按钮再次可点
        btn_cancel.setEnabled(true);
        btn_cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_button_red));
    }

}
