package com.wehotel.mynote.utils;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import com.wehotel.mynote.data.Const;
import com.wehotel.mynote.ui.receiver.ShortcutReceiver;

import java.util.Arrays;
import java.util.List;

/**
 * @date: 2019-11-13 18:50
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
public class ShortCutUtils {

    public static void create(Context mContext, String name, Class<?> cls, int iconRes, String id) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            ToastUtils.show("您的手机系统版本不支持该功能。");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //1
            ShortcutManager shortcutManager = (ShortcutManager) mContext.getSystemService(Context.SHORTCUT_SERVICE);
            if (shortcutManager.isRequestPinShortcutSupported()) {
                Intent launcherIntent = new Intent(mContext, cls);
                launcherIntent.putExtra(Const.checkInNow,true);

                launcherIntent.setAction(Intent.ACTION_VIEW);
                ShortcutInfo info = new ShortcutInfo.Builder(mContext, id)
                        .setIcon(Icon.createWithResource(mContext, iconRes))
                        .setShortLabel(name)
                        .setIntent(launcherIntent)
                        .build();
                //当添加快捷方式的确认弹框弹出来时，将被回调
                PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(mContext, ShortcutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
                shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
            } else {
                ToastUtils.show("添加快捷方式失败");
            }
//            //2
//            if (ShortcutManagerCompat.isRequestPinShortcutSupported(mContext)) {
//                Intent launcherIntent = new Intent(mContext, Main2Activity.class);
//                launcherIntent.setAction(Intent.ACTION_VIEW);
//                ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(mContext, name)
//                        .setIcon(IconCompat.createWithResource(mContext, R.mipmap.ic_launcher))
//                        .setShortLabel(name)
//                        .setIntent(launcherIntent)
//                        .build();
//
//                //当添加快捷方式的确认弹框弹出来时，将被回调
//                PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(mContext, ShortcutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
//                ShortcutManagerCompat.requestPinShortcut(mContext, info, shortcutCallbackIntent.getIntentSender());
//            }
        } else {
            Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
            addShortcutIntent.putExtra(Const.checkInNow,true);
            // 不允许重复创建，不是根据快捷方式的名字判断重复的
            addShortcutIntent.putExtra("duplicate", false);
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
            //图标
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(mContext, iconRes));
            // 设置关联程序
            Intent launcherIntent = new Intent();
            launcherIntent.setClass(mContext, cls);
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
            // 发送广播
            mContext.sendBroadcast(addShortcutIntent);
        }
    }

    /**
     * Android 7.1及以下 添加桌面
     */
    public static final String ACTION_ADD_SHORTCUT = Manifest.permission.INSTALL_SHORTCUT;


    public static void createDynamic(Context context, Class<?> cls, int iconRes, String name, String id) {
        System.out.println("createDynamic");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        id = StringUtils.trimToEmpty(id);
        name = StringUtils.trimToEmpty(name);
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager == null) return;

        List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
        for (ShortcutInfo info : dynamicShortcuts) {
            if (info.getId().equalsIgnoreCase(id)) {
                return;
            }
        }
        Intent launcherIntent = new Intent(context, cls);
        launcherIntent.putExtra(Const.checkInNow,true);
        launcherIntent.setAction(Intent.ACTION_VIEW);
        ShortcutInfo shortcut = new ShortcutInfo.Builder(context, id)
                .setShortLabel(name)
                .setLongLabel(name)
                .setIcon(Icon.createWithResource(context, iconRes))
                .setIntent(launcherIntent)
                .build();
        shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
    }

}
