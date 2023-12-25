package com.dp.base.permission;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

public class PermissionUtil {
    public static boolean hasAccessibilityPermission(@NonNull Context context, @NonNull Class<? extends AccessibilityService> serviceClass) {
        return AccessibilityPermissionUtil.hasPermission(context, serviceClass);
    }

    public static void goAccessibilityPermissionSetting(Activity activity, int requestCode) {
        AccessibilityPermissionUtil.goToSetting(activity, requestCode);
    }
}
