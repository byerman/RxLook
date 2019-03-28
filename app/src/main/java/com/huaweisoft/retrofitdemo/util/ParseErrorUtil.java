package com.huaweisoft.retrofitdemo.util;

import android.content.Context;
import android.widget.Toast;

public class ParseErrorUtil {

    public static boolean parseError(int errorCode, String errorMsg, Context context){
        if (errorCode != 0) {
            Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

}
