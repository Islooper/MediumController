package com.example.mediumcontroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mediumcontroller.adapter.SceneAdapter;
import com.example.mediumcontroller.bean.Scene;
import com.example.mediumcontroller.data.SharedHelper;
import com.example.mediumcontroller.service.MusicService;
import com.example.mediumcontroller.service.SceneService;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Context context;
    RecyclerView sceneRv;

    Button newScene;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        context = this;
        findId();

        initWidget();

        Intent intent = new Intent(this , SceneService.class);
        startService(intent);

    }

    int layoutPosition;

    private void initWidget() {
        final List<Scene> list;
        // 获取所有已经保存的list
        final SharedHelper helper = new SharedHelper(context);
        list = helper.readAll();
        final SceneAdapter adapter = new SceneAdapter(getApplicationContext(), list);
        final GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        sceneRv.setLayoutManager(layoutManager);
        sceneRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new SceneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Scene scene, final int i) {
                CreateDialog createDialog = new CreateDialog(context, new CreateDialog.OnCloseListener() {
                    @Override
                    public void onClick(Scene scene, Boolean isEdit) {
                        if (isEdit) {
                            layoutPosition = i;
                            // 编辑模式
                            list.set(layoutPosition, scene);
                            adapter.notifyItemChanged(layoutPosition, scene);

                            // 更新已经存数据
                            helper.save(scene , scene.getUuid());
                        }

                    }
                }, scene);

                createDialog.show();
            }

            // 删除item
            @Override
            public void onItemDel(View view, Scene scene, int i) {
                // 从list中删除
                list.remove(i);
                // 刷新界面
                adapter.notifyDataSetChanged();

                // 从已经数据中删除
                helper.deleteScene(scene.getUuid());
            }

            // 播放音乐
            @Override
            public void onItemPlay(View view, Scene scene, int i) {
                // 判断要播放哪首音乐
                int songId ;
                switch (scene.getMusic()){
                    case "烟火里的尘埃-华晨宇":
                        songId = R.raw.yhldca;
                        break;
                    case "一程山路-毛不易":
                        songId = R.raw.ycsl;
                        break;
                    case "他只是经过-h3R3/Felix Bennett":
                        songId = R.raw.tzsjg;
                        break;
                    case "青春大概-王上":
                        songId = R.raw.qcdg;
                        break;
                    case "棉花糖-至上励合":
                        songId = R.raw.mht;
                        break;
                    case "因你而在-林俊杰":
                        songId = R.raw.ynez;
                        break;
                    default:
                        songId = 0;
                        break;
                }

                // 启动播放服务
                // 启动服务播放背景音乐
                intent = new Intent(MainActivity.this, MusicService.class);
                String action = MusicService.ACTION_MUSIC;
                intent.putExtra("songId" , songId);
                // 设置action
                intent.setAction(action);
                startService(intent);
            }
        });

        // 新建 场景的点击事件
        newScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog createDialog = new CreateDialog(context, new CreateDialog.OnCloseListener() {
                    @Override
                    public void onClick(Scene scene, Boolean isEdit) {
                        if (!isEdit) {
                            // 新增模式
                            if (scene.getName().equals("") || scene.getName() == null) {
                                return;
                            }

                            // 添加至adapter的list
                            list.add(scene);
                            adapter.notifyDataSetChanged();

                            // 存储该信息
                            SharedHelper  helper = new SharedHelper(context);
                            helper.save(scene , scene.getUuid());
                        }

                    }
                }, null);

                createDialog.show();
            }
        });

    }

    private void findId() {
        sceneRv = findViewById(R.id.scene_rv);
        newScene = findViewById(R.id.btn_newScene);

    }
}