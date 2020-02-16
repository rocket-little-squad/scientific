package com.ts.scientific.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class RepResult implements Serializable {

    public static JSONObject repResult(int code,String msg,Object date){
        JSONObject res = new JSONObject();
        res.put("code",code);
        res.put("msg",msg);
        res.put("date",date);
        return res;
    }

    public static JSONObject repResult(int code,String msg,Object date,int count){
        JSONObject res = new JSONObject();
        res.put("code",code);
        res.put("msg",msg);
        res.put("date",date);
        res.put("count",count);
        return res;
    }
}
