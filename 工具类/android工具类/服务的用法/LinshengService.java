package com.boss.demo.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boss.demo.activity.NewBaseActivity;
import com.boss.demo.activity.PersonlSound;
import com.boss.demo.utils.AsyncGetRequest;
import com.boss.demo.utils.Consts;
import com.boss.demo.utils.HttpUtils;
import com.boss.demo.utils.SysConstants;
import com.boss.demo.utils.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message; 
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

public class LinshengService  extends Service { 
	public static String UserName = null;
	private static boolean IsRead = true;
	private static int MaxId = 0;
	public PersonlSound personlSound;
	private static int MaxIdT =0;
	
	@Override
	public IBinder onBind(Intent arg0) { 
		return null;
	}

	@Override
	public void onCreate() {   
		super.onCreate(); 
		//加一个语音提示
		personlSound=new PersonlSound(this);
		personlSound.ini();
		UserName = Utils.ReadSharePreference(getApplicationContext(),
				SysConstants.UserKey,
				SysConstants.UserNameKey);
		
	}
	
	
	private Handler MaxHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			if(msg == null || msg.equals(""))
				return;
			JSONObject _JsonObject = (JSONObject)msg.obj;
			if(_JsonObject == null)
				return;
			try {
				MaxId = _JsonObject.getInt("count");
				LinshengService.IsRead = true;
				LoopGet();
				
			} catch (JSONException e) { 
				MaxId = 0;
			}
		}
		
	};
	private void LoopGet()
	{
		new Thread(){
			@Override
			public void run() { 
				while(LinshengService.IsRead)
				{
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					 String result = "";
					 Message msg = handler.obtainMessage();
					 try 
					 {
						 AsyncGetRequest _AsyncGetRequest = new AsyncGetRequest(LinshengService.this, handler, "tools/android.ashx?maxid="+MaxId+"&committype=getmxcount&sjh="+LinshengService.UserName);
						 _AsyncGetRequest.execute();
					} catch (Exception e) {
						 
					}
					handler.sendMessage(msg);
				}
			} 
		}.start(); 
	}
   
	@Override
	public void onDestroy() { 
		super.onDestroy();
		LinshengService.IsRead = false;
	}

	@Override
	public void onStart(Intent intent, int startId) { 
		super.onStart(intent, startId);  
		AsyncGetRequest _AsyncGetRequest = new AsyncGetRequest(LinshengService.this, MaxHandler, "tools/android.ashx?committype=getmxcounttojson&sjh="+LinshengService.UserName);
		_AsyncGetRequest.execute();
		 
	}
	public Handler handler = new Handler() {  
		@Override  
		public void handleMessage(Message msg) {    
			if(msg == null || msg.equals(""))
				return;
			JSONObject _JsonObject = (JSONObject)msg.obj;
			if(_JsonObject == null)
				return;
			try { 
				Log.e("123456", "MaxId="+MaxId);
				if(_JsonObject.getInt("error") != 0)
					return; 
				
				double je = _JsonObject.getDouble("je");
				MaxId = _JsonObject.getInt("id");
				DecimalFormat  df = new DecimalFormat("######0.00");   
				if(je>=0)
				{
					personlSound.speak("收到金额"+df.format(je) +"元");
				}
				else{
					je=Math.abs(je);
					personlSound.speak("支出金额"+df.format(je)+"元");
				}
				
			} catch (JSONException e1) {
				 
			} 
		}
	}; 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) { 
		return super.onStartCommand(intent, flags, startId);
	}

}
