package com.example.mediumcontroller;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.mediumcontroller.adapter.ColorAdapter;
import com.example.mediumcontroller.bean.Scene;
import com.example.mediumcontroller.data.Uuid;

import java.util.ArrayList;
import java.util.List;

class CreateDialog extends Dialog {

    private Context context;
    private OnCloseListener listener;

    /**
     * 控件
     */

    EditText nameEt;
    Spinner musicsSp;
    Spinner backgroundSp;
    Button yes;
    Button cancle;
    TimePicker tp;
    CheckBox lamp1;
    CheckBox lamp2;
    CheckBox fan;
    Scene mScene;

    public CreateDialog(@NonNull Context context) {
        super(context);
    }

    public CreateDialog(Context context, OnCloseListener listener, Scene scene) {
        super(context);
        this.context = context;
        this.listener = listener;
        if (scene != null) {
            this.mScene = scene;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_scene);
        setCanceledOnTouchOutside(false);
        findId();

        initWidget();

    }


    private void findId() {
        nameEt = findViewById(R.id.et_name);
        musicsSp = findViewById(R.id.sp_music);
        backgroundSp = findViewById(R.id.sp_background);
        yes = findViewById(R.id.btn_yes);
        cancle = findViewById(R.id.btn_cancle);
        tp = findViewById(R.id.tp);
        lamp1 = findViewById(R.id.cb_lamp1);
        lamp2 = findViewById(R.id.cb_lamp2);
        fan = findViewById(R.id.cb_fan);
    }

    /**
     * 控件初始化
     */

    String music = "";
    String colorBg = "";
    String time = "";
    Boolean isLamp1 = false;
    Boolean isLamp2 = false;
    Boolean isFan = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initWidget() {

        tp.setIs24HourView(true);
        // 添加背景色
        final List<String> list = new ArrayList<>();
        list.add("#050505");
        list.add("#2196F3");
        list.add("#009688");
        list.add("#FF5722");
        list.add("#673AB7");
        list.add("#FFEB3B");
        ColorAdapter adapter = new ColorAdapter(getContext(), list);
        backgroundSp.setAdapter(adapter);


        if (mScene != null) {
            nameEt.setText(mScene.getName());

            // 切换音乐位置
            SpinnerAdapter musicAdapter = musicsSp.getAdapter(); //得到Spinner Adapter对象
            int count = musicAdapter.getCount();
            for (int i = 0; i < count; i++) {
                if (mScene.getMusic().equals(musicAdapter.getItem(i).toString())) {
                    musicsSp.setSelection(i, true);// 默认选中项
                    break;
                }
            }

            // 获取时间
            String[] time = mScene.getTime().split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);

            tp.setHour(hour);
            tp.setMinute(minute);

            // 设置联动状态
            if (mScene.getLamp1()) {
                lamp1.setChecked(true);
                isLamp1 = true;
            }

            if (mScene.getLamp2()) {
                lamp2.setChecked(true);
                isLamp2 = true;
            }

            if (mScene.getFan()) {
                fan.setChecked(true);
                isFan = true;
            }

            // 默认的颜色选择
            switch (mScene.getColor()){
                case "#050505":
                    backgroundSp.setSelection(0);
                    break;
                case "#2196F3":
                    backgroundSp.setSelection(1);
                    break;
                case "#009688":
                    backgroundSp.setSelection(2);
                    break;
                case "#FF5722":
                    backgroundSp.setSelection(3);
                    break;
                case "#673AB7":
                    backgroundSp.setSelection(4);
                    break;
                case "#FFEB3B":
                    backgroundSp.setSelection(5);
                    break;
            }
        }


        // 音乐选择
        musicsSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] musics = context.getResources().getStringArray(R.array.musics);
                music = musics[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 场景颜色选择
        backgroundSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorBg = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 时间选择器
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {  //获取当前选择的时间
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
            }
        });

        lamp1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLamp1 = isChecked;
            }
        });

        lamp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLamp2 = isChecked;
            }
        });

        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFan = isChecked;
            }
        });

        // 取消按钮 关闭弹窗
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取场景名称
                String sceneName = nameEt.getText().toString();

                Scene scene = new Scene();

                // 生成唯一uuid
                scene.setUuid(Uuid.getUuid());
                scene.setName(sceneName);
                scene.setColor(colorBg);
                scene.setFan(isFan);
                scene.setLamp1(isLamp1);
                scene.setLamp2(isLamp2);
                scene.setMusic(music);

                if (time.equals(""))
                    time = tp.getCurrentHour()+":"+tp.getCurrentMinute();
                scene.setTime(time);

                if (listener != null) {
                    if (mScene == null){
                        listener.onClick(scene, false);
                    }else {
                        listener.onClick(scene, true);
                    }
                }

                dismiss();

            }
        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnCloseListener {
        void onClick(Scene scene, Boolean isEdit);
    }

}
