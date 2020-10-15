package com.example.mediumcontroller;

import android.content.Context;
import android.content.SharedPreferences;

public class GwShare {

    private Context context;

    public GwShare() {

    }

    public GwShare(Context context) {
        this.context = context;
    }

    // 保存停车场Id
    public void save(String parkid) {
        SharedPreferences sp = context.getSharedPreferences("parkofaddress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("parkid", parkid);
        editor.commit();
    }

    //读取已存储的数据
    public String read() {
        SharedPreferences sp = context.getSharedPreferences("parkofaddress", Context.MODE_PRIVATE);
        String parkId = sp.getString("parkid", "");
        return parkId;
    }

}