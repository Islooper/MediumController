package com.example.mediumcontroller.http;

import com.example.mediumcontroller.config.Config;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by looper on 2020/10/14.
 */
public class HttpUtils {


    /**
     * 传感器控制接口
     */
    public static void controlSersor(String gwid , String nodeid , String cmd , String param){
        OkHttpUtils.post()
                .url(Config.url + "control.do")
                .addParams("gwid", gwid)
                .addParams("nodeid", nodeid)
                .addParams("cmd" , cmd)
                .addParams("param" , param)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });
    }
}
