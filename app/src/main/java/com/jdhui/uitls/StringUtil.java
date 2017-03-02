package com.jdhui.uitls;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.widget.Button;

import com.jdhui.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String MD5Encode(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] digest = md.digest();
            String text;
            for (int i = 0; i < digest.length; i++) {
                text = Integer.toHexString(0xFF & digest[i]);
                if (text.length() < 2) {
                    text = "0" + text;
                }
                hexString.append(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    public static String MD5Encode(String text) {
        return MD5Encode(text.getBytes());
    }

    public static String eregi_replace(String strFrom, String strTo,
                                       String strTarget) {
        String strPattern = "(?i)" + strFrom;
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strTarget);
        if (m.find()) {
            return strTarget.replaceAll(strFrom, strTo);
        } else {
            return strTarget;
        }
    }

    /**
     * 把字符串的后n位用“*”号代替(11位手机号)
     *
     * @param str 要代替的字符串
     * @return6
     */

    public static String replaceSubString(String str) {
        String sub = "";
        try {
            sub = str.substring(0, 3);
            String sub3 = str.substring(7, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb = sb.append("*");
            }
            sub = sub + sb.toString() + sub3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /**
     * 把字符串的后n位用“*”号代替(18位身份证号)
     *
     * @param str 要代替的字符串
     * @return6
     */

    public static String replaceSubStringID(String str) {
        String sub = "";
        try {
            sub = str.substring(0, 10);
            String sub3 = str.substring(14, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb = sb.append("*");
            }
            sub = sub + sb.toString() + sub3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /**
     * 把字符串的后n位用“*”号代替(只保留头尾两位)
     *
     * @param str 要代替的字符串
     * @return
     */

    public static String replaceSubStringName(String str) {
        String sub = "";
        try {
            sub = str.substring(0, 1);
            String sub3 = str.substring(str.length() - 1, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb = sb.append("*");
            }
            sub = sub + sb.toString() + sub3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /***
     * 截取后 Length位
     *
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        String sub = "";
        try {
            if (str.length() > length) {
                sub = str.substring(length, str.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /**
     * 去除字符串中的特定字符
     *
     * @param str  原字符串
     * @param temp 需要去除的字符
     * @return
     */
    public static String subStringSpecial(String str, String temp) {
        String sub = str.replace(temp, "");
        return sub;
    }

    public static boolean isUserNameRight(String username) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]+$");
        Matcher m = p.matcher(username);
        if (m.matches()) {
            return true;
        }
        return false;

    }

    public static void sendSms(Context context, String phone, String body) {
        body = trimCRLF(body);
        Uri uri = Uri.parse("smsto:" + phone);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", body);
        context.startActivity(it);
    }

    /**
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @param time
     * @return
     */
    public static String formatDate(String format, long time) {
        if (time != -1) {
            try {
                Date date = new Date(time);
                SimpleDateFormat sfd = new SimpleDateFormat(format/* "yyyy-MM-dd" */);
                return sfd.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 多个连续换行符只留一个
     *
     * @param str
     * @return
     */
    public static String trimCRLF(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        str = str.replace("\r", "");
        char charc = '\0';
        char[] c = str.toCharArray();
        int l = c.length;
        int lrnSize = 0;
        for (int i = 0; i < l; i++) {
            if (c[i] == '\n') {
                lrnSize++;
            } else {
                lrnSize = 0;
            }
            if (lrnSize > 1) {
                c[i] = charc;
            }
        }
        // str = str.replace("\n", "");
        return new String(c);
    }

    /**
     * 半角转全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> splitKeyValue(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = strUrlParam.split("&");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("=");
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static String base64UrlEncode(String host, String url) {
        try {
            url = host
                    + Base64.encodeToString(url.getBytes("utf-8"),
                    Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // url = host + StringUtil.eregi_replace("(\r\n|\r|\n|\n\r)", "", url);
        return url;
    }

    // public static SpannableStringBuilder setStringStyle(){
    //
    // }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        // String telRegex = "[1][34578]\\d{9}";//
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((145|147)|(15[^4])|(17[6-8])|((13|18)[0-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}xX";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /***
     * 判断字符串中是否有空格
     *
     * @param str
     * @return
     */
    public static boolean hasBlank(String str) {
        if (str.startsWith(" ") || str.endsWith(" ")) {
            return true;
        } else {
            String s[] = str.split(" +");
            if (s.length == 1) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断字符串中是否包含除_外特殊字符
     *
     * @param str
     * @return
     */
    public static boolean hasSpecialWord(String str) {
        boolean hasSymble = !str.matches("^[\\da-zA-Z_]*$");
        return hasSymble;
    }

    /**
     * 判断字符串中是否是字母和数字组合
     *
     * @param str
     * @return
     */
    public static boolean hasSpecialWordOne(String str) {
        boolean hasSymble = !str.matches("[0-9]+[a-zA-Z]+[0-9a-zA-Z]*|[a-zA-Z]+[0-9]+[0-9a-zA-Z]*");
        return hasSymble;
    }

    /***
     * 修改字符串样式
     *
     * @param context
     * @param str1
     * @param str2
     * @param str3
     * @param color1
     * @param color2
     * @param color3
     * @param size1
     * @param size2
     * @param size3
     * @param index1
     * @param index2
     * @param index3
     * @return
     */
    public static SpannableStringBuilder setTextStyle(Context context,
                                                      String str1, String str2, String str3, int color1, int color2,
                                                      int color3, int size1, int size2, int size3, int index1,
                                                      int index2, int index3) {
        SpannableStringBuilder style = new SpannableStringBuilder(str3);
        // SpannableStringBuilder实现CharSequence接口
        style.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(context, size1)), 0,
                str1.length() - index1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(context, size2)),
                str1.length() - index1, str2.length() - index2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(context, size3)),
                str2.length() - index2, str3.length() - index3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(
                new ForegroundColorSpan(context.getResources().getColor(color1)),
                0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(
                new ForegroundColorSpan(context.getResources().getColor(color2)),
                str1.length(), str2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(
                new ForegroundColorSpan(context.getResources().getColor(color3)),
                str2.length(), str3.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * 修改投资记录按钮方法
     *
     * @param btn
     * @param btn_hk
     * @param btn_tb
     * @param btn_jq
     * @param mResource
     */

    public static void changeButtonStyle(Button btn_hk, Button btn_tb, Button btn_jq, int btn, Resources mResource) {

        if (btn == btn_hk.getId()) {
            btn_hk.setBackgroundResource(R.drawable.shape_left_blue);
            btn_hk.setTextColor(mResource.getColor(R.color.txt_white));
            btn_tb.setBackgroundResource(R.color.white);
            btn_tb.setTextColor(mResource.getColor(R.color.txt_black));
            btn_jq.setBackgroundResource(R.drawable.shape_right_white);
            btn_jq.setTextColor(mResource.getColor(R.color.txt_black));
        } else if (btn == btn_tb.getId()) {
            btn_hk.setBackgroundResource(R.drawable.shape_left_white);
            btn_hk.setTextColor(mResource.getColor(R.color.txt_black));
            btn_tb.setBackgroundResource(R.color.blue_overlay);
            btn_tb.setTextColor(mResource.getColor(R.color.txt_white));
            btn_jq.setBackgroundResource(R.drawable.shape_right_white);
            btn_jq.setTextColor(mResource.getColor(R.color.txt_black));
        } else if (btn == btn_jq.getId()) {
            btn_hk.setBackgroundResource(R.drawable.shape_left_white);
            btn_hk.setTextColor(mResource.getColor(R.color.txt_black));
            btn_tb.setBackgroundResource(R.color.white);
            btn_tb.setTextColor(mResource.getColor(R.color.txt_black));
            btn_jq.setBackgroundResource(R.drawable.shape_right_blue);
            btn_jq.setTextColor(mResource.getColor(R.color.txt_white));
        }

    }

    /**
     * 修改投资记录按钮方法
     *
     * @param btn
     * @param btn_zr_kzr
     * @param btn_zr_csz
     * @param btn_zr_yzc
     * @param btn_zr_ygm
     * @param mResource
     */
    public static void changeButtonStyle(Button btn_zr_kzr, Button btn_zr_csz, Button btn_zr_yzc, Button btn_zr_ygm, int btn, Resources mResource) {


        if (btn == btn_zr_kzr.getId()) {
            btn_zr_kzr.setBackgroundResource(R.drawable.shape_left_blue);
            btn_zr_kzr.setTextColor(mResource.getColor(R.color.txt_white));
            btn_zr_csz.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_csz.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_yzc.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_yzc.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_ygm.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_ygm.setTextColor(mResource.getColor(R.color.txt_black));
        } else if (btn == btn_zr_csz.getId()) {
            btn_zr_kzr.setBackgroundResource(R.drawable.shape_left_white);
            btn_zr_kzr.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_csz.setBackgroundResource(R.color.blue_overlay);
            btn_zr_csz.setTextColor(mResource.getColor(R.color.txt_white));
            btn_zr_yzc.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_yzc.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_ygm.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_ygm.setTextColor(mResource.getColor(R.color.txt_black));
        } else if (btn == btn_zr_yzc.getId()) {
            btn_zr_kzr.setBackgroundResource(R.drawable.shape_left_white);
            btn_zr_kzr.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_csz.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_csz.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_yzc.setBackgroundResource(R.color.blue_overlay);
            btn_zr_yzc.setTextColor(mResource.getColor(R.color.txt_white));
            btn_zr_ygm.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_ygm.setTextColor(mResource.getColor(R.color.txt_black));
        } else if (btn == btn_zr_ygm.getId()) {
            btn_zr_kzr.setBackgroundResource(R.drawable.shape_left_white);
            btn_zr_kzr.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_csz.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_csz.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_yzc.setBackgroundResource(R.drawable.shape_right_white);
            btn_zr_yzc.setTextColor(mResource.getColor(R.color.txt_black));
            btn_zr_ygm.setBackgroundResource(R.drawable.shape_right_blue);
            btn_zr_ygm.setTextColor(mResource.getColor(R.color.txt_white));
        }

    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     *
     * @param str
     * @return
     */
    public static String addComma(String str) {
        // 将传进数字反转
        String reverseStr = new StringBuilder(str).reverse().toString();
        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        // 将[789,456,] 中最后一个[,]去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr;
    }

    /**
     * 格式化数据结果，千分位及保留小数点后面两位数据
     *
     * @param str 不含小数的String类型数据
     */

    public static String formatNum(String str) {

        DecimalFormat df2 = new DecimalFormat("0.00");
        double bond = Double.parseDouble(str);
        String str0 = StringUtil.addComma(df2.format(bond));
        String result = str0.substring(0, str0.lastIndexOf(",")) + str0.substring(str0.lastIndexOf(",") + 1, str0.length());
        return result;

    }

    /**
     * 修改邀请排行按钮方法
     *
     * @param btn          1
     * @param btn_contacts 1
     * @param btn_invest   1
     * @param btn          1
     * @param mResource    1
     */
    public static void changeButtonStyle(Button btn_contacts, Button btn_invest, int btn, Resources mResource) {

        if (btn == btn_contacts.getId()) {
            btn_contacts
                    .setBackgroundResource(R.color.white);
            btn_contacts.setTextColor(mResource.getColor(R.color.orange));
            btn_invest
                    .setBackgroundResource(R.color.gray);
            btn_invest.setTextColor(mResource
                    .getColor(R.color.white));
        } else if (btn == btn_invest.getId()) {
            btn_contacts.setBackgroundResource(R.color.gray);
            btn_contacts.setTextColor(mResource.getColor(R.color.white));
            btn_invest
                    .setBackgroundResource(R.color.white);
            btn_invest.setTextColor(mResource
                    .getColor(R.color.orange));
        }

    }

    /**
     * 修改主页按钮方法
     *
     * @param btn       1
     * @param btn_wyk   1
     * @param btn_djk   1
     * @param btn       1
     * @param mResource 1
     */

    public static void changeButtonStyleZR(Button btn_wyk, Button btn_djk, int btn, Resources mResource) {

        if (btn == btn_wyk.getId()) {
            btn_wyk.setBackgroundResource(R.drawable.shape_left_blue_background);
            btn_wyk.setTextColor(mResource.getColor(R.color.txt_white));
            btn_djk.setBackgroundResource(R.drawable.shape_right_blue_two);
            btn_djk.setTextColor(mResource.getColor(R.color.blue_two));
        } else if (btn == btn_djk.getId()) {
            btn_wyk.setBackgroundResource(R.drawable.shape_left_blue_two);
            btn_wyk.setTextColor(mResource.getColor(R.color.blue_two));
            btn_djk.setBackgroundResource(R.drawable.shape_right_blue_background);
            btn_djk.setTextColor(mResource .getColor(R.color.txt_white));
        }

    }

    /**
     *  字符串长度过长时以“...”显示
     * @param str
     * @return
     */
    public static String getResult(String str){
        String result ;
        if (str.length() > 7) {
            result = str.substring(0, 7) + "...";
        } else {
            result = str;
        }
        return result;
    }
}
