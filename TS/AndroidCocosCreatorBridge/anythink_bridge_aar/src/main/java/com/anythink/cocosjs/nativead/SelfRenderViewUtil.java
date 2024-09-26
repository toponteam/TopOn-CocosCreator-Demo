package com.anythink.cocosjs.nativead;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.cocosjs.view.SimpleWebViewActivity;
import com.anythink.core.api.ATAdAppInfo;
import com.anythink.nativead.api.ATNativeImageView;
import com.anythink.nativead.api.ATNativeMaterial;
import com.anythink.nativead.api.ATNativePrepareExInfo;
import com.anythink.nativead.api.ATNativePrepareInfo;
import com.huawei.hms.ads.AppDownloadButton;
import com.huawei.hms.ads.AppDownloadButtonStyle;

import java.util.ArrayList;
import java.util.List;

public class SelfRenderViewUtil {

    Activity mActivity;
    ViewInfo mViewInfo;
    ImageView mDislikeView;
    int mNetworkType;

    public SelfRenderViewUtil(Activity pActivity, ViewInfo pViewInfo, int networkType) {
        mActivity = pActivity;
        mViewInfo = pViewInfo;
        mNetworkType = networkType;
    }

    public FrameLayout bindSelfRenderView(ATNativeMaterial adMaterial, ATNativePrepareInfo nativePrepareInfo, ViewInfo pViewInfo) {
        FrameLayout frameLayout = new FrameLayout(mActivity);
        TextView titleView = new TextView(mActivity);
        TextView descView = new TextView(mActivity);

        //click views
        List<View> clickViewList = new ArrayList<>();
        //click
        List<View> customClickViews = new ArrayList<>();

        final View mediaView = adMaterial.getAdMediaView(frameLayout);
        if (mediaView != null) {
//            mediaView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            MsgTools.printMsg("media view ---> " + adMaterial.getVideoUrl());
            if (pViewInfo.imgMainView != null) {
                ViewInfo.add2ParentView(frameLayout, mediaView, pViewInfo.imgMainView, -1);
            }
        } else {
            //加载大图
            MsgTools.printMsg("main image ---> " + adMaterial.getMainImageUrl());
            if (pViewInfo.imgMainView != null) {
                final ATNativeImageView mainImageView = new ATNativeImageView(mActivity);
                ViewInfo.add2ParentView(frameLayout, mainImageView, pViewInfo.imgMainView, -1);
                mainImageView.setImage(adMaterial.getMainImageUrl());
                nativePrepareInfo.setMainImageView(mainImageView);//bind main image
            }
        }

        if (pViewInfo.titleView != null) {

            if (!TextUtils.isEmpty(mViewInfo.titleView.textcolor)) {
                titleView.setTextColor(Color.parseColor(mViewInfo.titleView.textcolor));
            }

            if (mViewInfo.titleView.textSize > 0) {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mViewInfo.titleView.textSize);
            }
            MsgTools.printMsg("title----> " + adMaterial.getTitle());
            titleView.setText(adMaterial.getTitle());

            titleView.setSingleLine();
            titleView.setMaxEms(15);
            titleView.setEllipsize(TextUtils.TruncateAt.END);

            ViewInfo.add2ParentView(frameLayout, titleView, pViewInfo.titleView, -1);
            nativePrepareInfo.setTitleView(titleView);//bind title
        }


        if (pViewInfo.descView != null && descView != null) {

            if (!TextUtils.isEmpty(mViewInfo.descView.textcolor)) {
                descView.setTextColor(Color.parseColor(mViewInfo.descView.textcolor));

            }
            if (mViewInfo.descView.textSize > 0) {
                descView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mViewInfo.descView.textSize);
            }
            MsgTools.printMsg("desc----> " + adMaterial.getDescriptionText());
            descView.setText(adMaterial.getDescriptionText());


            descView.setMaxLines(3);
            descView.setMaxEms(15);
            descView.setEllipsize(TextUtils.TruncateAt.END);

            ViewInfo.add2ParentView(frameLayout, descView, mViewInfo.descView, -1);
            nativePrepareInfo.setDescView(descView);//bind desc
        }

        View iconView = null;
        if (pViewInfo.IconView != null) {

            FrameLayout iconArea = new FrameLayout(mActivity);

            View adIconView = adMaterial.getAdIconView();
            if (adIconView == null) {
                ATNativeImageView imageView = new ATNativeImageView(mActivity);
                iconArea.addView(imageView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                imageView.setImage(adMaterial.getIconImageUrl());

                iconView = imageView;

                MsgTools.printMsg("ad icon url----> " + adMaterial.getIconImageUrl());
            } else {
                iconView = adIconView;
                MsgTools.printMsg("ad icon view----> " + adIconView);
                iconArea.addView(adIconView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }

            // 加载图片
            ViewInfo.add2ParentView(frameLayout, iconArea, pViewInfo.IconView, -1);
            nativePrepareInfo.setIconView(iconView);//bind icon
        }


        View ctaView = null;
        if (pViewInfo.ctaView != null) {
            try {
                if (mNetworkType == 39) {// AppDownloadButton(Only Huawei Ads support)
                    ctaView = adMaterial.getCallToActionButton();
                    if (ctaView != null) {
                        if (ctaView instanceof AppDownloadButton) {

                            AppDownloadButtonStyle appDownloadButtonStyle = new CustomAppDownloadButtonStyle(mActivity, pViewInfo);

                            if (pViewInfo.ctaView.textSize > 0) {
                                ((AppDownloadButton) ctaView).setTextSize(pViewInfo.ctaView.textSize);

                                //This line of code test found that the font size cannot be controlled
//                                appDownloadButtonStyle.Code().setTextSize(CommonUtil.dip2px(mActivity, pViewInfo.ctaView.textSize));
                            }

                            ((AppDownloadButton) ctaView).setAppDownloadButtonStyle(appDownloadButtonStyle);

                            MsgTools.printMsg("cta----> download button");

                            FrameLayout.LayoutParams ctaLayoutParams = new FrameLayout.LayoutParams(pViewInfo.ctaView.mWidth, pViewInfo.ctaView.mHeight);
                            ctaLayoutParams.leftMargin = pViewInfo.ctaView.mX;
                            ctaLayoutParams.topMargin = pViewInfo.ctaView.mY;
                            ctaLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                            frameLayout.addView(ctaView, ctaLayoutParams);
                        }
                    }
                }
            } catch (Throwable e) {
                ctaView = null;
                if (MsgTools.isDebug) {
                    e.printStackTrace();
                }
            }
            if (ctaView == null) {
                TextView ctaTextView = new TextView(mActivity);
                if (!TextUtils.isEmpty(pViewInfo.ctaView.textcolor)) {
                    ctaTextView.setTextColor(Color.parseColor(pViewInfo.ctaView.textcolor));
                }

                if (pViewInfo.ctaView.textSize > 0) {
                    ctaTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pViewInfo.ctaView.textSize);
                }

                ctaTextView.setGravity(Gravity.CENTER);
                ctaTextView.setSingleLine();
                ctaTextView.setMaxEms(15);
                ctaTextView.setEllipsize(TextUtils.TruncateAt.END);

                MsgTools.printMsg("cta----> " + adMaterial.getCallToActionText());
                ctaTextView.setText(adMaterial.getCallToActionText());

                ViewInfo.add2ParentView(frameLayout, ctaTextView, pViewInfo.ctaView, -1);
                nativePrepareInfo.setCtaView(ctaTextView);//bind cta button

                ctaView = ctaTextView;
            }
        }


        if (!TextUtils.isEmpty(adMaterial.getAdFrom())) {
            FrameLayout.LayoutParams adFromParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            adFromParam.leftMargin = CommonUtil.dip2px(mActivity, 3);
            adFromParam.bottomMargin = CommonUtil.dip2px(mActivity, 3);
            adFromParam.gravity = Gravity.BOTTOM;
            TextView adFromTextView = new TextView(mActivity);
            adFromTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
            adFromTextView.setPadding(CommonUtil.dip2px(mActivity, 5), CommonUtil.dip2px(mActivity, 2), CommonUtil.dip2px(mActivity, 5), CommonUtil.dip2px(mActivity, 2));
            adFromTextView.setBackgroundColor(0xff888888);
            adFromTextView.setTextColor(0xffffffff);
            adFromTextView.setText(adMaterial.getAdFrom());

            MsgTools.printMsg("ad from----> " + adMaterial.getAdFrom());
            frameLayout.addView(adFromTextView, adFromParam);
            nativePrepareInfo.setAdFromView(adFromTextView);//bind ad from
        }


        View logoView = null;
        if (pViewInfo.adLogoView != null) {
            View adLogoView = adMaterial.getAdLogoView();
            if (adLogoView != null) {//ad logo view
                logoView = adLogoView;

                MsgTools.printMsg("ad logo view----> " + adLogoView);
                ViewInfo.add2ParentView(frameLayout, logoView, pViewInfo.adLogoView, -1);
                nativePrepareInfo.setAdLogoView(logoView);
            } else {

                String adChoiceIconUrl = adMaterial.getAdChoiceIconUrl();
                Bitmap adLogoBitmap = adMaterial.getAdLogo();

                ATNativeImageView atNativeImageView = new ATNativeImageView(mActivity);

                if (!TextUtils.isEmpty(adChoiceIconUrl)) {//ad choice
                    MsgTools.printMsg("ad choice url----> " + adChoiceIconUrl);
                    atNativeImageView.setImage(adChoiceIconUrl);

                    logoView = atNativeImageView;

                    ViewInfo.add2ParentView(frameLayout, logoView, pViewInfo.adLogoView, -1);
                    nativePrepareInfo.setAdLogoView(logoView);
                } else if (adLogoBitmap != null) {//ad logo bitmap
                    MsgTools.printMsg("ad logo bitmap----> " + adLogoBitmap);
                    atNativeImageView.setImageBitmap(adLogoBitmap);

                    logoView = atNativeImageView;

                    ViewInfo.add2ParentView(frameLayout, logoView, pViewInfo.adLogoView, -1);
                    nativePrepareInfo.setAdLogoView(logoView);
                } else {//ad
                    MsgTools.printMsg("start to add adMaterial label textview");
                    TextView adLableTextView = new TextView(mActivity);
                    adLableTextView.setTextColor(Color.WHITE);
                    adLableTextView.setText("AD");
                    adLableTextView.setTextSize(11);
                    adLableTextView.setPadding(CommonUtil.dip2px(mActivity, 3), 0, CommonUtil.dip2px(mActivity, 3), 0);
                    adLableTextView.setBackgroundColor(Color.parseColor("#66000000"));
                    if (frameLayout != null) {
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.leftMargin = CommonUtil.dip2px(mActivity, 3);
                        layoutParams.topMargin = CommonUtil.dip2px(mActivity, 3);
                        frameLayout.addView(adLableTextView, layoutParams);

                        MsgTools.printMsg("add adMaterial label textview 2 activity");

                        nativePrepareInfo.setAdLogoView(adLableTextView);//bind ad choice
                    }
                }
            }
            FrameLayout.LayoutParams adLogoLayoutParams = new FrameLayout.LayoutParams(pViewInfo.adLogoView.mWidth, pViewInfo.adLogoView.mHeight);
            adLogoLayoutParams.leftMargin = pViewInfo.adLogoView.mX;
            adLogoLayoutParams.topMargin = pViewInfo.adLogoView.mY;
            nativePrepareInfo.setChoiceViewLayoutParams(adLogoLayoutParams);//bind layout params for adMaterial choice
        }


        if (pViewInfo.dislikeView != null) {
            initDislikeView(pViewInfo.dislikeView);
            MsgTools.printMsg("ad dislike----> " + mDislikeView);
            ViewInfo.add2ParentView(frameLayout, mDislikeView, pViewInfo.dislikeView, -1);
            nativePrepareInfo.setCloseView(mDislikeView);//bind close button
        }

        //v6.2.37
        if (pViewInfo.elementsView != null) {
            ATAdAppInfo adAppInfo = adMaterial.getAdAppInfo();
            if (adAppInfo != null) {
                MsgTools.printMsg("adAppInfo ----> " + adAppInfo);
                String appName = adAppInfo.getAppName();
                if (!TextUtils.isEmpty(appName)) {
                    MsgTools.printMsg("update title ----> " + appName);
                    titleView.setText(appName);
                }
                View elementsView = createElementsView(mViewInfo.elementsView, adAppInfo);
                if (elementsView != null) {
                    ViewInfo.add2ParentView(frameLayout, elementsView, pViewInfo.elementsView, -1);
                }
            }
        } else {
            MsgTools.printMsg("adAppInfo ----> null");
        }

        if (pViewInfo.rootView != null) {
            dealWithClick(frameLayout, pViewInfo.rootView.isCustomClick, clickViewList, customClickViews, "root");
        }
        if (pViewInfo.titleView != null) {
            dealWithClick(titleView, pViewInfo.titleView.isCustomClick, clickViewList, customClickViews, "title");
        }
        if (pViewInfo.descView != null) {
            dealWithClick(descView, pViewInfo.descView.isCustomClick, clickViewList, customClickViews, "desc");
        }
        if (pViewInfo.IconView != null) {
            dealWithClick(iconView, pViewInfo.IconView.isCustomClick, clickViewList, customClickViews, "icon");
        }
        if (pViewInfo.adLogoView != null) {
            dealWithClick(logoView, pViewInfo.adLogoView.isCustomClick, clickViewList, customClickViews, "adLogo");
        }
        if (pViewInfo.ctaView != null) {
            dealWithClick(ctaView, pViewInfo.ctaView.isCustomClick, clickViewList, customClickViews, "cta");
        }


        nativePrepareInfo.setClickViewList(clickViewList);//bind click view list

        if (nativePrepareInfo instanceof ATNativePrepareExInfo) {
            ((ATNativePrepareExInfo) nativePrepareInfo).setCreativeClickViewList(customClickViews);//bind custom view list
        }

        return frameLayout;
    }

    private void initDislikeView(ViewInfo.INFO dislikeInfoView) {
        if (mDislikeView == null) {
            mDislikeView = new ImageView(mActivity);
            mDislikeView.setImageResource(CommonUtil.getResId(mActivity, "btn_close", "drawable"));
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dislikeInfoView.mWidth, dislikeInfoView.mHeight);
        layoutParams.leftMargin = dislikeInfoView.mX;
        layoutParams.topMargin = dislikeInfoView.mY;

        if (!TextUtils.isEmpty(dislikeInfoView.bgcolor)) {
            mDislikeView.setBackgroundColor(Color.parseColor(dislikeInfoView.bgcolor));
        }

        mDislikeView.setLayoutParams(layoutParams);
    }

    private void dealWithClick(View view, boolean isCustomClick, List<View> clickViews, List<View> customClickViews, String name) {
        if (view == null) {
            return;
        }

        if (mNetworkType == 8 || mNetworkType == 22) {
            if (isCustomClick) {
                MsgTools.printMsg("add customClick ----> " + name);
                customClickViews.add(view);
                return;
            }
        }
        MsgTools.printMsg("add click ----> " + name);
        clickViews.add(view);
    }

    private View createElementsView(ViewInfo.INFO elementsView, ATAdAppInfo atAdAppInfo) {
        if (elementsView == null || atAdAppInfo == null) {
            MsgTools.printMsg("createElementsView ----> " + elementsView + ", " + atAdAppInfo);
            return null;
        }


//        String appName = atAdAppInfo.getAppName();
        String appVersion = atAdAppInfo.getAppVersion();
        String publisher = atAdAppInfo.getPublisher();
        String appPermissonUrl = atAdAppInfo.getAppPermissonUrl();
        String appPrivacyUrl = atAdAppInfo.getAppPrivacyUrl();
        String functionUrl = atAdAppInfo.getFunctionUrl();

        LinearLayout linearLayout = new LinearLayout(mActivity);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        if (!TextUtils.isEmpty(functionUrl)) {
            TextView functionView = new TextView(mActivity);
            setupElementView(elementsView, "function", functionView, "功能");
            setOpenUrlClickListener(functionView, functionUrl);

            linearLayout.addView(functionView);
        }
        if (!TextUtils.isEmpty(appPrivacyUrl)) {
            TextView privacyView = new TextView(mActivity);
            setupElementView(elementsView, "privacy", privacyView, "隐私");
            setOpenUrlClickListener(privacyView, appPrivacyUrl);

            linearLayout.addView(privacyView);
        }
        if (!TextUtils.isEmpty(appPermissonUrl)) {
            TextView permissionView = new TextView(mActivity);
            setupElementView(elementsView, "permission", permissionView, "权限");
            setOpenUrlClickListener(permissionView, appPermissonUrl);

            linearLayout.addView(permissionView);
        }

//        if (!TextUtils.isEmpty(appName)) {
//            TextView appNameView = new TextView(mActivity);
//            setupElementView(elementsView, "appName", appNameView, appName);
//
//            linearLayout.addView(appNameView);
//        }
        if (!TextUtils.isEmpty(publisher)) {
            TextView publisherView = new TextView(mActivity);
            setupElementView(elementsView, "publisher", publisherView, publisher);

            linearLayout.addView(publisherView);
        }
        if (!TextUtils.isEmpty(appVersion)) {
            TextView appVersionView = new TextView(mActivity);
            setupElementView(elementsView, "appVersion", appVersionView, "v" + appVersion);

            linearLayout.addView(appVersionView);
        }

        if (linearLayout.getChildCount() > 0) {
            return linearLayout;
        } else {
            return null;
        }
    }


    private void setupElementView(ViewInfo.INFO elementsView, String tag, TextView view, String text) {
        try {
            view.setTextColor(Color.parseColor(elementsView.textcolor));
            view.setTextSize(elementsView.textSize);
            MsgTools.printMsg(tag + "----> " + text);
            view.setText(text);

            view.setSingleLine();
            view.setMaxEms(15);
            view.setEllipsize(TextUtils.TruncateAt.END);

            int padding = CommonUtil.dip2px(mActivity, 2);
            view.setPadding(padding, padding, padding, padding);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            view.setLayoutParams(params);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private void setOpenUrlClickListener(View view, final String url) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MsgTools.printMsg("open url: " + url);

//                    Intent intent = new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(url));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                            | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Context context = mActivity;
//                    if (context != null) {
//                        context.startActivity(intent);
//                    }

                    Intent intent = new Intent(mActivity, SimpleWebViewActivity.class);
                    intent.putExtra(SimpleWebViewActivity.EXTRA_URL, url);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mActivity.startActivity(intent);

                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
