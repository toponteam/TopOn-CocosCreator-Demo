package com.anythink.cocosjs.interstitial;

import com.anythink.cocosjs.utils.BaseHelper;

import org.json.JSONObject;

import java.util.Map;

public class InterstitialAutoADHelper extends BaseHelper {

    public void fillMapFromJsonObjectExposed(Map<String, Object> map, JSONObject jsonObject) {
        fillMapFromJsonObject(map, jsonObject);
    }
}
