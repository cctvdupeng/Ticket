package com.dp.base.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.dp.base.exception.ExceptionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseAccessibilityService extends AccessibilityService {
    private static BaseAccessibilityService service;
    private final TopActivityProvider topActivityProvider = new TopActivityProvider(this);
    private final List<AccessibilityEventListener> accessibilityEventListeners = Collections.synchronizedList(new ArrayList<>());
    public static BaseAccessibilityService getService() {
        return service;
    }

    public TopActivityProvider getTopActivityProvider() {
        return topActivityProvider;
    }

    public void registerAccessibilityEventListener(AccessibilityEventListener listener) {
        if (!accessibilityEventListeners.contains(listener)) {
            accessibilityEventListeners.add(listener);
        }
    }

    public void unRegisterAccessibilityEventListener(AccessibilityEventListener listener) {
        accessibilityEventListeners.remove(listener);
    }

    public String getActiveWindowPackageName() {
        return ExceptionUtil.ignoreExceptionWithResult(() -> getRootInActiveWindow().getPackageName().toString(), "");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        service = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        topActivityProvider.onAccessibilityEvent(event);
        notifyAccessibilityEvent(event);
    }

    private void notifyAccessibilityEvent(AccessibilityEvent event) {
        if (accessibilityEventListeners.size() > 0) {
            for (AccessibilityEventListener listener : accessibilityEventListeners) {
                ExceptionUtil.ignoreException(() -> listener.onAccessibilityEvent(BaseAccessibilityService.this, event));
            }
        }
    }

    @Override
    public void onInterrupt() {

    }


}
