package com.anythink.cocosjs.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseHelper {

    private static final String TAG = BaseHelper.class.getSimpleName();

    private JSONObject mCallbackJsonObject;
    private String mCallbackNameJson;

    protected boolean hasCallbackName(String key) {
        return mCallbackJsonObject != null && mCallbackJsonObject.has(key);
    }

    protected String getCallbackName(String key) {
        try {
            return mCallbackJsonObject.optString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setAdListener(final String callbackNameJson) {
        if (!TextUtils.equals(mCallbackNameJson, callbackNameJson) && !TextUtils.isEmpty(callbackNameJson)) {
            try {
                mCallbackJsonObject = new JSONObject(callbackNameJson);
                mCallbackNameJson = callbackNameJson;
                MsgTools.pirntMsg(TAG + " setAdListener success... " + callbackNameJson);
            } catch (JSONException e) {
                e.printStackTrace();
                MsgTools.pirntMsg(TAG + " setAdListener error>>> " + e.getMessage());
            }
        }
    }

    protected Map<String, Object> getJsonMap(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            String key;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                map.put(key, jsonObject.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    protected static void fillMapFromJsonObject(Map<String, Object> localExtra, JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            String key;
            while (keys.hasNext()) {
                key = keys.next();
                Object value = jsonObject.opt(key);
                localExtra.put(key, value);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
