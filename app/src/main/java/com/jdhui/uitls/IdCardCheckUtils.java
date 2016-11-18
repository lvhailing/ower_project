package com.jdhui.uitls;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

public class IdCardCheckUtils {

    /**
    * 检查校验位
    * @param IdCardCheckUtils
    * @return
    */
    private static boolean checkIDParityBit(String certiCode) {
        boolean flag = false;
        if (certiCode == null || "".equals(certiCode))
            return false;
        int ai[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
        if (certiCode.length() == 18) {
            int i = 0;
            for (int k = 0; k < 18; k++) {
                char c = certiCode.charAt(k);
                int j;
                if (c == 'X')
                    j = 10;
                else if (c <= '9' && c >= '0')
                    j = c - 48;
                else
                    return flag;
                i += j * ai[k];
            }

            if (i % 11 == 1)
                flag = true;
        }
        return flag;
    }

    /**
    * 检查日期格式
    *
    * @param year
    * @param month
    * @param day
    * @return
    */
    @SuppressLint("SimpleDateFormat")
    private static boolean checkDate(String year, String month, String day) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        try {
            String s3 = year + month + day;
            simpledateformat.setLenient(false);
            simpledateformat.parse(s3);
        } catch (java.text.ParseException parseexception) {
            return false;
        }
        return true;
    }

    /**
    * 校验身份证
    *
    * @param certiCode
    * 待校验身份证
    * @return 0--校验成功; 1--位数不对; 2--生日格式不对 ; 3--校验位不对 ; 4--其他异常;5--字符异常;
    * @param certiCode
    * @return
    */
    private static int checkCertiCode(String certiCode) {
        try {
            if (certiCode == null || certiCode.length() != 15 && certiCode.length() != 18)
                return 1;
            String s1;
            String s2;
            String s3;

            if (certiCode.length() == 15) {

                if (!checkFigure(certiCode)) {
                    return 5;
                }

                s1 = "19" + certiCode.substring(6, 8);
                s2 = certiCode.substring(8, 10);
                s3 = certiCode.substring(10, 12);

                if (!checkDate(s1, s2, s3))
                    return 2;
            }

            if (certiCode.length() == 18) {
                if (!checkFigure(certiCode.substring(0, 17))) {
                    return 5;
                }

                s1 = certiCode.substring(6, 10);
                s2 = certiCode.substring(10, 12);
                s3 = certiCode.substring(12, 14);

                if (!checkDate(s1, s2, s3))
                    return 2;
                if (!checkIDParityBit(certiCode))
                    return 3;
            }
        } catch (Exception exception) {

            return 4;
        }
        return 0;
    }

    /**
    * 判断身份证号码是否有效
    * @param idCard
    * @return
    */
    public static boolean isIdCard(String idCard) {
        if (checkCertiCode(idCard) == 0)
            return true;
        else
            return false;
    }

    /**
    * 检查字符串是否全为数字
    * @param certiCode
    * @return
    */
    private static boolean checkFigure(String certiCode) {
        try {
            Long.parseLong(certiCode);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}