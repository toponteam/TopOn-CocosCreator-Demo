package com.anythink.cocosjs.utils;

import android.content.Context;
import android.text.TextUtils;

import com.anythink.core.api.AdError;

public class CommonUtil {

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static String getErrorMsg(AdError adError) {
        try {
            return adError.getFullErrorInfo().replace("'s", "")
                    .replace("'", "")
                    .replace("\n", "");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "code:[ " + adError.getCode() + " ]desc:[ ]platformCode:[ " + adError.getPlatformCode() + " ]platformMSG:[ " + adError.getPlatformMSG() + " ]";
    }

    public static int getResId(Context context, String resName, String resType) {
        if (context != null) {
            resName = "anythink_" + resName;
            return context.getResources().getIdentifier(resName, resType,
                    context.getPackageName());
        }
        return -1;
    }

}
