package com.dp.base.permission;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.dp.base.exception.ExceptionUtil;

public class AccessibilityPermissionUtil {
    public static boolean hasPermission(@NonNull Context context, @NonNull Class<? extends AccessibilityService> serviceClass) {
        return ExceptionUtil.ignoreExceptionWithResult(() -> {
            String serviceString = context.getPackageName() + "/" + serviceClass.getName();
            int accessibilityEnabled = Settings.Secure.getInt(
                    context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(
                        context.getApplicationContext().getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (settingValue != null) {
                    return settingValue.toLowerCase().contains(serviceString.toLowerCase());
                }
            }
            return false;
        }, false);
    }

    public static void goToSetting(Activity activity, int requestCode) {
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivityForResult(intent, requestCode);
    }
}
