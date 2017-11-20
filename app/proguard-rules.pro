# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# 保持哪些类不被混淆
-keep class android.support.v4.** { *; }
-keep class android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep public class com.jdhui.mould.BaseRequester
-keep public class * extends com.jdhui.mould.BaseRequester
-keep public class * extends android.preference.Preference
-keep class com.jdhui.bean.** { *; }
-keep public class * extends android.view.ViewGroup{
    public *;
}
-keep public class * implements java.io.Serializable {*;}

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepclassmembers class com.jdhui.act.WebActivity$MyJavaScriptinterface {
    *;
}

-keep public class com.jdhui.R$*{
    public static final int *;
}

#webView js 交互
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 public *;
}
-keepattributes *JavascriptInterface*
-keepclassmembers class com.jdhui.ui.activity.**$**{
  public *;
}
-keepclassmembers class com.jdhui.ui.fragment.**$**{
  public *;
}
-keepclassmembers class * {
    public void *(android.view.View);
}

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#枚举类不能去混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#Gson
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.mrocker.m6go.entity.**{ *; }


# 阿里推送
-keepclasseswithmembernames class ** {
    native <methods>;
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class anet.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**


# afinal混淆
 -dontwarn com.jdhui.db.afinal.**
 -dontwarn com.jdhui.db.model.**
 -keep class com.jdhui.db.afinal.** { *; }
 -keep public class * extends com.jdhui.db.afinal.**
 -keep class com.jdhui.db.model.** {*; }
 -keep public interface com.jdhui.db.afinal.** {*;}
 -keepattributes Signature
 -keepattributes *Annotation*




