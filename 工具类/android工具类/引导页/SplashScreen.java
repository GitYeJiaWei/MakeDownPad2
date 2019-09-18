package com.boss.demo.activity;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boss.demo.BossApplication;
import com.boss.demo.model.SjmpModel;
import com.boss.demo.utils.CryptoTools;
import com.boss.demo.utils.GlobalClass;
import com.boss.demo.utils.OKHttpUtils;
import com.boss.demo.utils.SysConstants;
import com.boss.demo.utils.ToastUtils;
import com.boss.demo.utils.Utils;
import com.boss.demo.wx.PayConfig;
import com.payh5.bbnpay.mobile.main.BbnPay;
import com.sxx001.R;

public class SplashScreen extends NewBaseActivity {
	private static final int animTime = 100;
	private static final int moveTime = 150;
	private ImageView iv_image, iv_loading;

	boolean isFirstIn = false;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final long SPLASH_DELAY_MILLIS = 3000;
    public static final String SHAREDPREFERENCES_NAME = "first_pref";
    public static final String IS_FIRST_IN="isFirstIn";

	private String account;
	private String password;
	public int stye=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);

		iv_image = (ImageView)this.findViewById(R.id.iv_image);
		iv_loading = (ImageView)this.findViewById(R.id.iv_loading);
//		setLoadingHeight();
//		scheduleShowTargetActivity();
		
		account = Utils.ReadSharePreference(getApplicationContext(),
				SysConstants.UserKey, SysConstants.UserNameKey);
		
		password = Utils.ReadSharePreference(getApplicationContext(),
				SysConstants.UserKey, SysConstants.PasswordKey);
		init();
		showAnim();
		BbnPay.init(this, PayConfig.appid);
		
		AnimationDrawable mAnimation = (AnimationDrawable) iv_loading.getDrawable();
		mAnimation.start();
	}
	
	private void setLoadingHeight(){
		Display currentScreen = this.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		currentScreen.getSize(size);
		int height = size.y;
		
		RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) iv_loading.getLayoutParams();
		//获取当前控件的布局对象
		params.topMargin=height* 2 / 3 ;//设置当前控件布局的高度
		iv_loading.setLayoutParams(params);//将设置好的布局参数应用到控件中
	}
	
	private boolean isAutoLogin(){
		if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
			return false;
		}
		return true;
	}

	private void scheduleShowTargetActivity() {
		AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha_img);
		iv_image.startAnimation(alphaAnimation);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO 运行
				if (stye==2) {
					Intent intent = new Intent(SplashScreen.this,
							GuideViewActivity.class);
					intent.putExtra("stype", "2");
					startActivity(intent);
					SplashScreen.this.finish();
					overridePendingTransition(R.anim.alpha_in,  
							R.anim.alpha_out);
				} else{
					Intent intent = new Intent(SplashScreen.this,
							LoginActivity.class);
					SplashScreen.this.startActivity(intent);
					SplashScreen.this.finish();
					overridePendingTransition(R.anim.alpha_in,  
							R.anim.alpha_out);  

				}
				
			}
		}, 500);// 0.5秒后执行Runnable中的run方法

	}

	/**
	 * 启动动画
	 */
	private void showAnim() {
		final AnimationDrawable anim = new AnimationDrawable();
		
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20001), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20002), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20003), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20004), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20005), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20006), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20007), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20008), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20010), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20011), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20012), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20013), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20014), animTime);
		anim.addFrame(getResources().getDrawable(R.drawable.ic_20015), animTime);

		anim.setOneShot(true);

		iv_image.setBackgroundDrawable(anim);
		anim.start();
		int duration = 0;

		for (int i = 0; i < anim.getNumberOfFrames(); i++) {
			duration += anim.getDuration(i);
		}
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			public void run() {
				//动画结束
				moveAnim();
			}

		}, duration);
	}
	
	/**
	 * 移动动画
	 */
	private void moveAnim(){
//		int height = iv_image.getTop();
//		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -height/2);
//		animation.setFillAfter(true);
//		animation.setDuration(moveTime);
//		iv_image.startAnimation(animation);
		
		if (isAutoLogin()) {
			//已有登陆账号
			//进入登录逻辑
			iv_loading.setVisibility(View.VISIBLE);
			AnimationDrawable mAnimation = (AnimationDrawable) iv_loading.getDrawable();
			mAnimation.start();
			autoLogin();
		}else {
			//没有登陆账号
			//动画后进入登录页面
			showLoadingImageView();
		}
		
	}
	
	/**
	 * 显示loading
	 */
	private void showLoadingImageView() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO 运行
				scheduleShowTargetActivity();
			}
		}, moveTime);

	}
	
	 private Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	                case GO_HOME:
	                    stye=1;
	                    break;
	                case GO_GUIDE:
	                	stye=2;
	                    break;
	            }
	            super.handleMessage(msg);
	        }
	    };
	    
	//用SharedPreferences 判断是否首次打开
	private void init() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        isFirstIn = preferences.getBoolean(IS_FIRST_IN, true);
        if (isFirstIn) {
            mHandler.sendEmptyMessage(GO_GUIDE);

        } else {
            mHandler.sendEmptyMessage(GO_HOME);
        }
    }
	
	/**
	 * 自动登录
	 */
	private void autoLogin() {
		CryptoTools ct = null;
		String passwordEncode = null;
		try {
			ct = new CryptoTools();
			passwordEncode = ct.encode(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		final HashMap<String, String> map = new HashMap<String, String>();
		map.put("sjh", account);
		map.put("pwd", passwordEncode);
		map.put("committype", "login");
		OKHttpUtils.post(SysConstants.HTTP_ANDROID, map, new Callback() {

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				closeLoadingDialog();
				final String str = response.body().string();

				runOnUiThread(new Runnable() {
					public void run() {
						JSONObject resultJson = null;
						try {
							resultJson = new JSONObject(str);
						} catch (JSONException e) {
							e.printStackTrace();
							showToastOnMainThread(R.string.error_data);
							return;
						}

						String error = "";
						try {
							error = resultJson.getString("error");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if ("".equals(error)) {
							SjmpModel sjmpModel = new SjmpModel();
							sjmpModel.setDwdz(resultJson.optString("dwdz"));
							sjmpModel.setGrdz(resultJson.optString("grdz"));
							sjmpModel.setXm(resultJson.optString("xm"));
							sjmpModel.setSjh2(resultJson.optString("sjh2"));
							sjmpModel.setCph(resultJson.optString("cph"));
							sjmpModel.setSjh(account);
							GlobalClass.setLoginUserInfo(sjmpModel);

							ToastUtils.toast(SplashScreen.this, "登录成功");
							Utils.WriteSharePreference(getApplicationContext(),
									SysConstants.UserKey,
									SysConstants.UserNameKey, account);
							Utils.WriteSharePreference(getApplicationContext(),
									SysConstants.UserKey,
									SysConstants.UserGrxz,
									resultJson.optString("grxz"));
							Utils.WriteSharePreference(getApplicationContext(),
									SysConstants.UserKey,
									SysConstants.USER_CPH, resultJson.optString("cph"));
							Utils.WriteSharePreference(getApplicationContext(),
									SysConstants.UserKey,
									SysConstants.PasswordKey, password);
							Utils.WriteSharePreference(getApplicationContext(),
									SysConstants.UserKey,
									SysConstants.USER_INFO, str);
							
							Utils.WriteSharePreference(getApplicationContext(),
									SysConstants.UserKey,
									SysConstants.USER_NAME, resultJson.optString("xm"));

							BossApplication.getApp().setLogin(true);
							if (stye==2) {
								Intent intent = new Intent(SplashScreen.this,
										GuideViewActivity.class);
								intent.putExtra("stype", "1");
								startActivity(intent);
								SplashScreen.this.finish();
							}else
							{
								Intent intent = new Intent(SplashScreen.this,
									MainActivity.class);
								startActivity(intent);
								SplashScreen.this.finish();
							}
						} else {
							if (stye==2) {
								Intent intent = new Intent(SplashScreen.this,
										GuideViewActivity.class);
								intent.putExtra("stype", "2");
								startActivity(intent);
								SplashScreen.this.finish();
							}else {
								toLogin();
							}
							
						}
					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				runOnUiThread(new Runnable() {
					public void run() {
						ToastUtils
								.toast(SplashScreen.this, R.string.error_net);
						toLogin();
					}
				});

			}

		});
	}
	
	private void toLogin(){
		Intent intent = new Intent(SplashScreen.this,
				LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
}
