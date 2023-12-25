package com.dp.ticket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dp.base.permission.PermissionUtil;
import com.dp.base.utils.AppUtil;
import com.dp.ticket.accessibility.AccessService;
import com.dp.ticket.accessibility.MMEventListener;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10002;
    private final MMEventListener mmEventHandler = new MMEventListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAccessibilityPermission();
    }

    private void checkAccessibilityPermission() {
        boolean hasPermission = PermissionUtil.hasAccessibilityPermission(this, AccessService.class);
        if (!hasPermission) {
            String appName = AppUtil.getSelfName(this);
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    // 需要启用无障碍服务
                    .setTitle("启用无障碍服务")
                    // 软件需要打开“无障碍服务”才能运行，请打开“设置”-》
                    .setMessage(appName + "核心服务需要打开“无障碍服务”才能运行，" + "请打开『设置』=>『无障碍服务』，或\n『设置』=>『无障碍服务』=>『更多已下载的服务』，找到" + appName + "开启无障碍服务")
                    .setPositiveButton("去打开", (dialog, which) -> {
                        dialog.dismiss();
                        PermissionUtil.goAccessibilityPermissionSetting(MainActivity.this, REQUEST_CODE);
                    })
                    .show();
        } else {
            AccessService.getService().registerAccessibilityEventListener(mmEventHandler);
            Toast.makeText(this, "无障碍服务已经开启", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PermissionUtil.hasAccessibilityPermission(this, AccessService.class)) {
            AccessService.getService().registerAccessibilityEventListener(mmEventHandler);
        } else {
            PermissionUtil.goAccessibilityPermissionSetting(this, REQUEST_CODE);
        }
    }
}