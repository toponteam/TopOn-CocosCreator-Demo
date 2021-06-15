package com.anythink.cocosjs.utils;

public class Const {

    public static final boolean DEBUG = true;
    public static final String SCENARIO = "Scenario";
    public static final String USER_ID = "userID";
    public static final String USER_DATA = "media_ext";

    public static final String BANNER_POSITION_TOP = "top";
    public static final String BANNER_POSITION_BOTTOM = "bottom";
    public static final String BANNER_AD_SIZE = "banner_ad_size";

    public static final String X = "x";
    public static final String Y = "y";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String TEXT_COLOR = "textColor";
    public static final String TEXT_SIZE = "textSize";
    public static final String CUSTOM_CLICK = "isCustomClick";

    public static final String parent = "parent";
    public static final String icon = "icon";
    public static final String mainImage = "mainImage";
    public static final String title = "title";
    public static final String desc = "desc";
    public static final String adLogo = "adLogo";
    public static final String cta = "cta";
    public static final String dislike = "dislike";

    public static class Interstital {
        public static final String UseRewardedVideoAsInterstitial = "UseRewardedVideoAsInterstitial";
    }


    public static class RewardVideoCallback {
        public static final String LoadedCallbackKey = "RewardedVideoLoaded";
        public static final String LoadFailCallbackKey = "RewardedVideoLoadFail";
        public static final String PlayStartCallbackKey = "RewardedVideoPlayStart";
        public static final String PlayEndCallbackKey = "RewardedVideoPlayEnd";
        public static final String PlayFailCallbackKey = "RewardedVideoPlayFail";
        public static final String CloseCallbackKey = "RewardedVideoClose";
        public static final String ClickCallbackKey = "RewardedVideoClick";
        public static final String RewardCallbackKey = "RewardedVideoReward";

    }

    public static class InterstitialCallback {
        public static final String LoadedCallbackKey = "InterstitialLoaded";
        public static final String LoadFailCallbackKey = "InterstitialLoadFail";
        public static final String PlayStartCallbackKey = "InterstitialPlayStart";
        public static final String PlayEndCallbackKey = "InterstitialPlayEnd";
        public static final String PlayFailCallbackKey = "InterstitialPlayFail";
        public static final String CloseCallbackKey = "InterstitialClose";
        public static final String ClickCallbackKey = "InterstitialClick";
        public static final String ShowCallbackKey = "InterstitialAdShow";
        public static final String ShowFailCallbackKey = "InterstitialAdShowFail";
    }

    public static class BannerCallback {
        public static final String LoadedCallbackKey = "BannerLoaded";
        public static final String LoadFailCallbackKey = "BannerLoadFail";
        public static final String CloseCallbackKey = "BannerCloseButtonTapped";
        public static final String ClickCallbackKey = "BannerClick";
        public static final String ShowCallbackKey = "BannerShow";
        public static final String RefreshCallbackKey = "BannerRefresh";
        public static final String RefreshFailCallbackKey = "BannerRefreshFail";
    }

    public static class NativeCallback {
        public static final String LoadedCallbackKey = "NativeLoaded";
        public static final String LoadFailCallbackKey = "NativeLoadFail";
        public static final String CloseCallbackKey = "NativeCloseButtonTapped";
        public static final String ClickCallbackKey = "NativeClick";
        public static final String ShowCallbackKey = "NativeShow";
        public static final String VideoStartKey = "NativeVideoStart";
        public static final String VideoEndKey = "NativeVideoEnd";
        public static final String VideoProgressKey = "NativeVideoProgress";
    }

}
