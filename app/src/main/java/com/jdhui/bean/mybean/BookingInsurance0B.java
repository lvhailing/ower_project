package com.jdhui.bean.mybean;

/**
 * 新增--（产品列表）保险预约     准备入参
 */
public class BookingInsurance0B {
    private  String productId;//产品ID
    private  String userInfoId; //用户ID
    private  String bookingRemark; //预约备注
    private  String bookingAmount; //预约金额

    public BookingInsurance0B(String productId, String userInfoId, String bookingRemark, String bookingAmount) {
        this.productId = productId;
        this.userInfoId = userInfoId;
        this.bookingRemark = bookingRemark;
        this.bookingAmount = bookingAmount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getBookingRemark() {
        return bookingRemark;
    }

    public void setBookingRemark(String bookingRemark) {
        this.bookingRemark = bookingRemark;
    }

    public String getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(String bookingAmount) {
        this.bookingAmount = bookingAmount;
    }
}
