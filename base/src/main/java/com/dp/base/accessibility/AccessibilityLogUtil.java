package com.dp.base.accessibility;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

public class AccessibilityLogUtil {
    public static void printNodeHierarchy(AccessibilityNodeInfo nodeInfo, int depth) {
        Rect bounds = AccessibilityUtils.getNodeBounds(nodeInfo);
        StringBuilder sb = new StringBuilder();
        if (depth > 0) {
            for (int i = 0; i < depth; i++) {
                sb.append("  ");
            }
            sb.append("\u2514 ");
        }
        sb.append(nodeInfo.getClassName());
        sb.append(" (" + nodeInfo.getChildCount() + ")");
        sb.append(" " + bounds.toString());
        sb.append(" (depth:" + depth + ")");
        if (nodeInfo.getViewIdResourceName() != null) {
            sb.append(" (id:" + nodeInfo.getViewIdResourceName() + ")");
        }
        if (nodeInfo.getText() != null) {
            sb.append(" - \"" + nodeInfo.getText() + "\"");
            sb.append(" - \"" + nodeInfo.getContentDescription() + "\"");
        }

        System.out.println(sb.toString());

        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = nodeInfo.getChild(i);
            if (childNode != null) {
                printNodeHierarchy(childNode, depth + 1);
            }
        }
    }
}
