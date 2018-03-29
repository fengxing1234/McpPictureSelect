package cn.fengxing.com.mcppictureselect.internal.utils;

import android.os.Build;

/**
 * Created by fengxing on 2018/3/29.
 */

public class Platform {

    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
