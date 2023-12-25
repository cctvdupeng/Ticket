package com.dp.base.accessibility;

import android.view.accessibility.AccessibilityEvent;

public interface AccessibilityEventListener {
    public void onAccessibilityEvent(BaseAccessibilityService service, AccessibilityEvent event);
}
