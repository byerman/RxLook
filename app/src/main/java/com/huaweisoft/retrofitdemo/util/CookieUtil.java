package com.huaweisoft.retrofitdemo.util;

import android.util.Log;
import android.widget.ListView;

import java.util.List;

import okhttp3.Headers;

public class CookieUtil {

    private static final String COOKIE_STR = "Set-Cookie";
    private static final String USER_NAME_COOKIE = "UserName_wanandroid";
    private static final String PASSWORD_COOKIE = "pass_wanandroid";

    public static void saveCookie(Headers headers) {
        List<String> cookies = headers.values(COOKIE_STR);
        for (String cookie : cookies) {
            if (cookie.contains(USER_NAME_COOKIE)) {
                String[] userNames = cookie.split("=");
                String[] names = userNames[1].split(";");
                String userName = names[0];
                Log.d("cookie","userNameCookie:" + userName);
            } else if (cookie.contains(PASSWORD_COOKIE)) {
                String[] passwords = cookie.split("=");
                String[] passes = passwords[1].split(";");
                String password = passes[0];
                Log.d("cookie","passwordCookie:" + password);
            }
        }
    }

}
