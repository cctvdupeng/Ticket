package com.dp.base.utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

public class AppUtil {
    private static String appName;

    @NonNull
    public static String getSelfName(@NonNull Context context) {
        if (!TextUtils.isEmpty(appName)) {
            return appName;
        }
        try {
            appName = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appName;
    }
}
