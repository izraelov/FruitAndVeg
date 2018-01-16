####################################################################################################
####################################################################################################
####################################################################################################
######################################### PROGUARD #################################################
####################################################################################################
####################################################################################################
####################################################################################################
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# to keep the Show Forgot Password Dialog from SignIn class
-keep public class SignIn{
    private void showForgotPwdDialog();
}
# to keep the 'currentUser' Var
# Because currentUser is usefull for SignIn and SignUp Classes
-keep class Common {
 public static final android.Common.*;
}

# Keep Classes MainActivity & foodList
-keep public class * extends android.app.Home
-keep public class * extends android.app.FoodList


 # keep inteface , keep class ,


# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
