package com.huaweisoft.retrofitdemo.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class VerifyEtUtil {

    public static boolean verifyEtLogin(EditText etName, EditText etPwd,Context context) {
        String name = etName.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(context,"账号或密码为空",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
