package com.yx.android.copyuc.utils;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yx on 2016/1/13 0013.
 */
public class SmartBarUtils {
    public SmartBarUtils() {
    }

    public static void setActionBarTabsShowAtBottom(ActionBar actionbar, boolean showAtBottom) {
        try {
            Method e = Class.forName("android.app.ActionBar").getMethod("setTabsShowAtBottom", new Class[]{Boolean.TYPE});

            try {
                e.invoke(actionbar, new Object[]{Boolean.valueOf(showAtBottom)});
            } catch (IllegalArgumentException var4) {
                var4.printStackTrace();
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
            } catch (InvocationTargetException var6) {
                var6.printStackTrace();
            }
        } catch (SecurityException var7) {
            var7.printStackTrace();
        } catch (NoSuchMethodException var8) {
            var8.printStackTrace();
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }

    }

    public static void setActionBarViewCollapsable(ActionBar actionbar, boolean collapsable) {
        try {
            Method e = Class.forName("android.app.ActionBar").getMethod("setActionBarViewCollapsable", new Class[]{Boolean.TYPE});

            try {
                e.invoke(actionbar, new Object[]{Boolean.valueOf(collapsable)});
            } catch (IllegalArgumentException var4) {
                var4.printStackTrace();
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
            } catch (InvocationTargetException var6) {
                var6.printStackTrace();
            }
        } catch (SecurityException var7) {
            var7.printStackTrace();
        } catch (NoSuchMethodException var8) {
            var8.printStackTrace();
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }

    }

    public static void setActionModeHeaderHidden(ActionBar actionbar, boolean hidden) {
        try {
            Method e = Class.forName("android.app.ActionBar").getMethod("setActionModeHeaderHidden", new Class[]{Boolean.TYPE});

            try {
                e.invoke(actionbar, new Object[]{Boolean.valueOf(hidden)});
            } catch (IllegalArgumentException var4) {
                var4.printStackTrace();
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
            } catch (InvocationTargetException var6) {
                var6.printStackTrace();
            }
        } catch (SecurityException var7) {
            var7.printStackTrace();
        } catch (NoSuchMethodException var8) {
            var8.printStackTrace();
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }

    }

    public static void setBackIcon(ActionBar actionbar, Drawable backIcon) {
        try {
            Method e = Class.forName("android.app.ActionBar").getMethod("setBackButtonDrawable", new Class[]{Drawable.class});

            try {
                e.invoke(actionbar, new Object[]{backIcon});
            } catch (IllegalArgumentException var4) {
                var4.printStackTrace();
            } catch (Resources.NotFoundException var5) {
                var5.printStackTrace();
            } catch (IllegalAccessException var6) {
                var6.printStackTrace();
            } catch (InvocationTargetException var7) {
                var7.printStackTrace();
            }
        } catch (NoSuchMethodException var8) {
            var8.printStackTrace();
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        }

    }

    public static void hide(View decorView) {
        if (hasSmartBar()) {
            try {
                Class[] e = new Class[]{Integer.TYPE};
                Method localMethod = View.class.getMethod("setSystemUiVisibility", e);
                Field localField = View.class.getField("SYSTEM_UI_FLAG_HIDE_NAVIGATION");
                Object[] arrayOfObject = new Object[1];

                try {
                    arrayOfObject[0] = localField.get((Object) null);
                } catch (Exception var6) {
                    ;
                }

                localMethod.invoke(decorView, arrayOfObject);
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }
    }

    public static void hide(Context context, Window window) {
        hide(context, window, 0);
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static void hide(Context context, Window window, int smartBarHeight) {
        if (hasSmartBar()) {
            if (context.getResources().getConfiguration().orientation != 2) {
                window.setFlags(1024, 1024);
                window.addFlags(2048);
                int statusBarHeight = getStatusBarHeight(context);
                window.getDecorView().setPadding(0, statusBarHeight, 0, -smartBarHeight);
            }
        }
    }

    public static boolean hasSmartBar() {
        try {
            Method e = Class.forName("android.os.Build").getMethod("hasSmartBar", new Class[0]);
            return ((Boolean) e.invoke((Object) null, new Object[0])).booleanValue();
        } catch (Exception var1) {
            return Build.DEVICE.equals("mx2") ? true : (!Build.DEVICE.equals("mx") && !Build.DEVICE.equals("m9") ? false : false);
        }
    }
}
