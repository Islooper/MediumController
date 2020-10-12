package com.example.mediumcontroller.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.util.Base64;
import android.util.Log;

import com.example.mediumcontroller.bean.Scene;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SharedHelper {

    private Context context;

    public SharedHelper() {

    }

    public SharedHelper(Context context) {
        this.context = context;
    }

    //保存用户的用户名,密码
    public void save(Scene scene, String key) {
        try {
            //保存对象
            SharedPreferences sharedPreferences = context.getSharedPreferences("scene", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(scene);
            String base64Product = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            editor.putString(key, base64Product);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //读取已存储的数据
    public List<Scene> readAll() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("scene", MODE_PRIVATE);
        Map<String, ?> allScenes = sharedPreferences.getAll();
        List<Scene> list = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allScenes.entrySet()) {
            list.add(getOneScene(entry.getKey()));
        }
        return list;
    }

    public Scene getOneScene(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("scene", MODE_PRIVATE);
        String productString = sharedPreferences.getString(key, "");
        byte[] base64Product = Base64.decode(productString, Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Product);
        Scene scene = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            scene = (Scene) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public void deleteScene(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("scene", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }



}