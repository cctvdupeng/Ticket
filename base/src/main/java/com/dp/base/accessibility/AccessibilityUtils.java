package com.dp.base.accessibility;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.dp.base.exception.ExceptionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AccessibilityUtils {
    public static String getEventPackageName(AccessibilityEvent event) {
        return ExceptionUtil.ignoreExceptionWithResult(() -> Objects.requireNonNull(event.getPackageName()).toString(), "");
    }

    public static String getEventClassName(AccessibilityEvent event) {
        return ExceptionUtil.ignoreExceptionWithResult(() -> Objects.requireNonNull(event.getClassName()).toString(), "");
    }

    public static Rect getNodeBounds(@NotNull AccessibilityNodeInfo nodeInfo) {
        Rect bounds = new Rect();
        nodeInfo.getBoundsInScreen(bounds);
        return bounds;
    }
}
