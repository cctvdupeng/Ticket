package com.dp.ticket.accessibility;

import android.view.accessibility.AccessibilityEvent;

import com.dp.base.accessibility.AccessibilityEventListener;
import com.dp.base.accessibility.AccessibilityLogUtil;
import com.dp.base.accessibility.BaseAccessibilityService;
import com.dp.base.utils.ExecutorUtil;

public class MMEventListener implements AccessibilityEventListener {
    @Override
    public void onAccessibilityEvent(BaseAccessibilityService service, AccessibilityEvent event) {
        if ("com.tencent.mm".equals(service.getActiveWindowPackageName())) {
            ExecutorUtil.execute(new ExecutorUtil.ISchedule() {
                @Override
                public void runWorker() {
                    AccessibilityLogUtil.printNodeHierarchy(service.getRootInActiveWindow(), 0);
                }

                @Override
                public void runUI() {

                }
            });
        }
    }
}
