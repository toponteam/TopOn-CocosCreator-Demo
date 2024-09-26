package com.anythink.cocosjs.nativead;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.anythink.cocosjs.utils.Const;
import com.anythink.nativead.api.ATNativeAdView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/7.
 * @Email: zhoushubin@salmonads.com
 */
public class ViewInfo {
    protected class INFO {
        protected int mX = 0;
        protected int mY = 0;
        protected int mWidth = 0;
        protected int mHeight = 0;
        protected String bgcolor = "";
        protected int textSize = 0;
        protected String textcolor = "";
        protected boolean isCustomClick = false;

        protected String name;

        public INFO() {

        }
    }


    public static void add2ParentView(final FrameLayout view, final View childView, final INFO pViewInfo, final int gravity) {

        if (view == null || pViewInfo == null) {
            return;
        }

        if (childView == null || pViewInfo.mWidth < 0 || pViewInfo.mHeight < 0) {
            Log.e("add2activity--[" + pViewInfo.name + "]", " config error ,show error !");
            return;
        }


        Log.i("add2activity", "[" + pViewInfo.name + "]   add 2 activity");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pViewInfo.mWidth, pViewInfo.mHeight);
        layoutParams.leftMargin = pViewInfo.mX;
        layoutParams.topMargin = pViewInfo.mY;
        if (gravity > 0) {
            layoutParams.gravity = gravity;
        } else {
            layoutParams.gravity = 51;
        }


        childView.setLayoutParams(layoutParams);

//                pViewInfo.mView.setBackgroundColor(pViewInfo.bgcolor);
        try {
            if (!TextUtils.isEmpty(pViewInfo.bgcolor)) {
                childView.setBackgroundColor(Color.parseColor(pViewInfo.bgcolor));
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        view.addView(childView, layoutParams);

    }


//    public static void add2RootView(final Activity pActivity,final  ViewInfo pViewInfo){
//        RelativeLayout relative = new RelativeLayout(pActivity);
//        relative.setBackgroundColor(Color.parseColor(pViewInfo.rootView.bgcolor));
//
//        pActivity.setContentView(relative);
//
//    }


    public static void addNativeAdView2Activity(final Activity pActivity, final ViewInfo pViewInfo, final ATNativeAdView mATNativeAdView) {

        if (pActivity == null || mATNativeAdView == null) {
            return;
        }


        pActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ViewGroup _viewGroup = (ViewGroup) mATNativeAdView.getParent();
                    if (_viewGroup != null) {
                        _viewGroup.removeView(mATNativeAdView);
                    }
                } catch (Exception e) {
                    if (Const.DEBUG) {
                        e.printStackTrace();
                    }

                }

                if (pViewInfo.rootView != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pViewInfo.rootView.mWidth, pViewInfo.rootView.mHeight);
                    layoutParams.leftMargin = pViewInfo.rootView.mX;
                    layoutParams.topMargin = pViewInfo.rootView.mY;

                    if (!TextUtils.isEmpty(pViewInfo.rootView.bgcolor)) {
                        mATNativeAdView.setBackgroundColor(Color.parseColor(pViewInfo.rootView.bgcolor));
                    }

                    pActivity.addContentView(mATNativeAdView, layoutParams);
                }


            }
        });
    }


    public INFO rootView, imgMainView, IconView, titleView, descView, adLogoView, ctaView, dislikeView, elementsView;


    public static ViewInfo createDefualtView(Activity pActivity) {


        DisplayMetrics dm = pActivity.getResources().getDisplayMetrics();
        int heigth = dm.heightPixels;
        int width = dm.widthPixels;

        ViewInfo _viewInfo = new ViewInfo();
        _viewInfo.rootView.textSize = 12;
        _viewInfo.rootView.textcolor = "#0X000000";
        _viewInfo.rootView.bgcolor = "#0XFFFFFF";
        _viewInfo.rootView.mWidth = width;
        _viewInfo.rootView.mHeight = heigth / 5;
        _viewInfo.rootView.mX = 0;
        _viewInfo.rootView.mY = 0;
        _viewInfo.rootView.name = "rootView_def";

        //imgMainView
        _viewInfo.imgMainView.textSize = 12;
        _viewInfo.imgMainView.textcolor = "#0X000000";
        _viewInfo.imgMainView.bgcolor = "#0XFFFFFF";
        _viewInfo.imgMainView.mWidth = 25;
        _viewInfo.imgMainView.mHeight = 25;
        _viewInfo.imgMainView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.imgMainView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.imgMainView.name = "imgMainView_def";


        _viewInfo.adLogoView.textSize = 12;
        _viewInfo.adLogoView.textcolor = "#0X000000";
        _viewInfo.adLogoView.bgcolor = "#0XFFFFFF";
        _viewInfo.adLogoView.mWidth = _viewInfo.rootView.mWidth * 3 / 5;
        _viewInfo.adLogoView.mHeight = _viewInfo.rootView.mHeight / 2;
        _viewInfo.adLogoView.mX = _viewInfo.rootView.mX + 100;
        _viewInfo.adLogoView.mY = _viewInfo.rootView.mX + 10;
        _viewInfo.adLogoView.name = "adlogo_def";


        _viewInfo.IconView.textSize = 12;
        _viewInfo.IconView.textcolor = "#0X000000";
        _viewInfo.IconView.bgcolor = "#0XFFFFFF";
        _viewInfo.IconView.mWidth = 25;
        _viewInfo.IconView.mHeight = 25;
        _viewInfo.IconView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.IconView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.IconView.name = "appicon_def";


        _viewInfo.titleView.textSize = 12;
        _viewInfo.titleView.textcolor = "#0X000000";
        _viewInfo.titleView.bgcolor = "#0XFFFFFF";
        _viewInfo.titleView.mWidth = 25;
        _viewInfo.titleView.mHeight = 25;
        _viewInfo.titleView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.titleView.mY = _viewInfo.rootView.mX + 0;


        _viewInfo.descView.textSize = 12;
        _viewInfo.descView.textcolor = "#0X000000";
        _viewInfo.descView.bgcolor = "#0XFFFFFF";
        _viewInfo.descView.mWidth = 25;
        _viewInfo.descView.mHeight = 25;
        _viewInfo.descView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.descView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.descView.name = "desc_def";

        _viewInfo.ctaView.textSize = 12;
        _viewInfo.ctaView.textcolor = "#0X000000";
        _viewInfo.ctaView.bgcolor = "#0XFFFFFF";
        _viewInfo.ctaView.mWidth = 25;
        _viewInfo.ctaView.mHeight = 25;
        _viewInfo.ctaView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.ctaView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.ctaView.name = "cta_def";

        _viewInfo.dislikeView.textSize = 12;
        _viewInfo.dislikeView.textcolor = "#0X000000";
        _viewInfo.dislikeView.bgcolor = "#0X000000";
        _viewInfo.dislikeView.mWidth = 25;
        _viewInfo.dislikeView.mHeight = 25;
        _viewInfo.dislikeView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.dislikeView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.dislikeView.name = "dislike_def";

        _viewInfo.elementsView = _viewInfo.getDefaultViewInfoForElements(_viewInfo.rootView);

        return _viewInfo;
    }

    ViewInfo.INFO getDefaultViewInfoForElements(ViewInfo.INFO rootView) {
        ViewInfo.INFO _viewInfo = new ViewInfo.INFO();

        _viewInfo.textSize = 12;
        _viewInfo.textcolor = "#FFFFFF";
        _viewInfo.bgcolor = "#7F000000";
        _viewInfo.mWidth = rootView.mWidth;
        _viewInfo.mHeight = 60;
        _viewInfo.mX = rootView.mX + 0;
        _viewInfo.mY = rootView.mX + 0;
        _viewInfo.name = "elements_def";

        return _viewInfo;
    }

    public INFO parseINFO(String json, String name, int px, int py) throws JSONException {
        INFO _info = new INFO();
        JSONObject _jsonObject = new JSONObject(json);
        if (_jsonObject.has(Const.X)) {
            _info.mX = _jsonObject.getInt(Const.X) + px;
        }

        if (_jsonObject.has(Const.Y)) {
            _info.mY = _jsonObject.getInt(Const.Y) + py;
        }
        if (_jsonObject.has(Const.WIDTH)) {
            _info.mWidth = _jsonObject.getInt(Const.WIDTH);
        }
        if (_jsonObject.has(Const.HEIGHT)) {
            _info.mHeight = _jsonObject.getInt(Const.HEIGHT);
        }
        if (_jsonObject.has(Const.BACKGROUND_COLOR)) {
            _info.bgcolor = _jsonObject.getString(Const.BACKGROUND_COLOR);
        }
        if (_jsonObject.has(Const.TEXT_COLOR)) {
            _info.textcolor = _jsonObject.getString(Const.TEXT_COLOR);
        }
        if (_jsonObject.has(Const.TEXT_SIZE)) {
            _info.textSize = _jsonObject.getInt(Const.TEXT_SIZE);
        }
        if (_jsonObject.has(Const.CUSTOM_CLICK)) {
            _info.isCustomClick = _jsonObject.getBoolean(Const.CUSTOM_CLICK);
        }

        _info.name = name;
        return _info;
    }


}
