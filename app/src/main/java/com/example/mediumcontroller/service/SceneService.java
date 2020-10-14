package com.example.mediumcontroller.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.mediumcontroller.MainActivity;
import com.example.mediumcontroller.MyApplication;
import com.example.mediumcontroller.R;
import com.example.mediumcontroller.bean.Scene;
import com.example.mediumcontroller.config.Config;
import com.example.mediumcontroller.data.SharedHelper;
import com.example.mediumcontroller.http.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SceneService extends Service {
 
	public static final String TAG = "MyService";
 
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate() executed");

		// 启动定时器监听场景联动
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			public void run() {
				openTiming();
			}
		}, 1000,1000);

	}

	public void openTiming(){
		// 获取所有的场景列表
		SharedHelper helper = new SharedHelper(MyApplication.getmContext());
		List<Scene> list = helper.readAll();

		for (Scene scene: list
			 ) {
			if (!scene.getTime().equals("") && scene.getTime() != null){
				// 获取当前时间
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
				String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

				// 判断到点了
				if (scene.getTime().equals(date)){
					// 判断需要控制的传感器
					if (scene.getFan()){
						// 打开风扇
						HttpUtils.controlSersor(Config.gwid , "0000000200000002", "1" , "1");
					}

					if (scene.getLamp1()){
						// 打开灯1
						HttpUtils.controlSersor(Config.gwid , "0000000200000001", "1" , "1");
					}

					if (scene.getLamp2()){
						// 打开灯2
						HttpUtils.controlSersor(Config.gwid , "0000000200000001", "1" , "1");
					}

					// 打开音乐
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

					// 启动服务播放背景音乐
				    Intent intent = new Intent(this, MusicService.class);
					String action = MusicService.ACTION_MUSIC;
					intent.putExtra("songId" , songId);
					// 设置action
					intent.setAction(action);
					startService(intent);
				}
			}
		}
	}
 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand() executed");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() executed");
	}
 
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
 
}