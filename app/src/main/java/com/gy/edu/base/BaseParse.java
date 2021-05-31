package com.gy.edu.base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 高岳 on 2016/8/3.
 * Describe:基础解析
 */
public class BaseParse {

    public static BaseBean parseBaseBean(String result){
        BaseBean baseBean = new BaseBean();
        try {
            JSONObject object = new JSONObject(result);
            baseBean.success = object.optBoolean("success");
            baseBean.errCode = object.optString("errCode");
            baseBean.errMsg = object.optString("errMsg");
            Object obj = object.opt("data");
            baseBean.data = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return baseBean;
    }
}
