package com.dp.base.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.dp.base.exception.ExceptionUtil;

import java.util.ArrayList;
import java.util.List;

public class TopActivityProvider {
    private final AccessibilityService service;
    private final List<OnActivityChangeListener> onActivityChangeListeners = new ArrayList<>();
    private ActivityInfo topActivity;

    public TopActivityProvider(AccessibilityService service) {
        this.service = service;
    }

    public ActivityInfo getTopActivity() {
        return topActivity;
    }

    public String getTopActivityName() {
        if (topActivity != null) {
            return topActivity.name;
        }
        return "";
    }

    public void registerOnActivityChangeListener(OnActivityChangeListener listener) {
        if (!onActivityChangeListeners.contains(listener)) {
            onActivityChangeListeners.add(listener);
        }
    }

    public void unRegisterOnActivityChangeListener(OnActivityChangeListener listener) {
        onActivityChangeListeners.remove(listener);
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        ExceptionUtil.ignoreException(() -> {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                AccessibilityNodeInfo source = event.getSource();
                String className = AccessibilityUtils.getEventClassName(event);
                String packageName = AccessibilityUtils.getEventPackageName(event);
                if (source != null && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(packageName)) {
                    // 获取当前窗口activity名
                    ComponentName componentName = new ComponentName(packageName, className);
                    ActivityInfo activityInfo = service.getPackageManager().getActivityInfo(componentName, 0);
                    ActivityInfo oldActivity = topActivity;
                    topActivity = activityInfo;
                    if (!onActivityChangeListeners.isEmpty()) {
                        for (OnActivityChangeListener listener : onActivityChangeListeners) {
                            listener.onActivityChange(service, oldActivity, topActivity);
                        }
                    }
                }
            }
        });
    }

    public interface OnActivityChangeListener {
        void onActivityChange(AccessibilityService service, ActivityInfo oldActivity, ActivityInfo newActivity);
    }
}
