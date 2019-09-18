package com.boss.demo.activity;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.json.JSONObject;

import com.alipay.android.phone.mrpc.core.v;
import com.boss.demo.alipay_utils.PayActivityUtils;
import com.boss.demo.alipay_utils.PayResult;
import com.boss.demo.callback.OkHttpCallBack;
import com.boss.demo.utils.DialogUtils;
import com.boss.demo.utils.GlobalClass;
import com.boss.demo.utils.OKHttpUtils;
import com.boss.demo.utils.SysConstants;
import com.boss.demo.utils.ToastUtils;
import com.boss.demo.utils.Utils;
import com.sxx001.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.BinderThread;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class StopCar extends NewBaseActivity {
	@Bind(R.id.numget)//车牌号
	TextView numget;
	@Bind(R.id.getseat)//车位所属
	TextView getseat;
	@Bind(R.id.timeget)//进场时间
	TextView timeget;
	@Bind(R.id.stopget)//停车时长
	TextView stopget;
	@Bind(R.id.moneyget)//金额
	TextView moneyget;
	
	@Bind(R.id.enpay)//确认支付
	Button enpay;
	@Bind(R.id.btnBack)
	LinearLayout btnBack;
	@Bind(R.id.btnMore)
	LinearLayout btnMore;
	@Bind(R.id.pageTitle)
	TextView pageTitle;
	@Bind(R.id.radioButton1)//账户余额
	RadioButton ye;
	@Bind(R.id.radioButton2)//微信支付
	RadioButton wx;
	@Bind(R.id.radioButton3)//支付宝
	RadioButton zf;
	
	private Intent intent;
	//支付类
	private PayActivityUtils payActivityUtils = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stop_car);
		ButterKnife.bind(this);
		initView();
		initData();
		initTitle();
	}
	
	private void initView(){
		intent=this.getIntent();
	}
	
	private void initData(){
		DecimalFormat df=new DecimalFormat("######0.00");
		numget.setText(intent.getStringExtra("Cph"));//车牌号
		getseat.setText(intent.getStringExtra("Sjh"));//车位所属
		timeget.setText(intent.getStringExtra("Addtime"));//进场时间
		stopget.setText(intent.getStringExtra("LongTime")+"小时");//停车时长
		moneyget.setText("￥  "+df.format(intent.getDoubleExtra("Je", 0.00)));//金额
		
	}
	
	private void initTitle() {
		pageTitle.setText("结账停车缴费");
		btnMore.setVisibility(View.GONE);
		//实例支付类,获取到支付的对象
		payActivityUtils = new PayActivityUtils(StopCar.this, mPayHandler);
	}
	
	private void appAlipay(){
		final HashMap<String, String> map=new HashMap<String, String>();
		map.put("clientSjh", intent.getStringExtra("c_sjh"));
		map.put("serverSjh", intent.getStringExtra("Sjh"));
		map.put("je", intent.getDoubleExtra("Je", 0.00)+"");
		map.put("spId", intent.getStringExtra("ID"));
		map.put("spBz", "停车场扫码支付");
		if (zf.isChecked()) {
			map.put("action", "ALIPAY_PAY");
		OKHttpUtils.post(SysConstants.HTTP_APPALIPAY, map, 
				new OkHttpCallBack(StopCar.this) {
					
					@Override
					public void onSuccess(Call arg0, JSONObject object) {
						int resultCode = object.optInt("ResultCode", 0);
						if (resultCode==1) {
							if (object.optString("Message").equals("SUCCESS")) {
								String Je =object.optString("Zje");
								String ddh=object.optString("out_trade_no");
								payActivityUtils.Pay("支付", "停车缴费", Je, ddh);//支付 
								
								/*ToastUtils.toast(StopCar.this, 
									"成功支付"+object.optString("Zje"));*/
							}else {
								ToastUtils.toast(StopCar.this,
									"服务器错误,请联系管理员");
							}
						}
						
					}
					
					@Override
					public void onFailure(int errorType) {
						// TODO Auto-generated method stub
						
					}
				});
		}else if (ye.isChecked()) {
			map.put("action", "ZFTONG_PAY");
			OKHttpUtils.post(SysConstants.HTTP_AppZftongPay, map, 
					new OkHttpCallBack(StopCar.this) {
						
						@Override
						public void onSuccess(Call arg0, JSONObject object) {
							int resultCode = object.optInt("ResultCode", 0);
							if (resultCode==1) {
								if (object.optString("Message").equals("SUCCESS")) {
									ToastUtils.toast(StopCar.this, 
										"支付成功");
									startAct(Mx.class);
									StopCar.this.finish();
								}else {
									ToastUtils.toast(StopCar.this,
										"服务器错误,请联系管理员");
								}
							}
							
						}
						
						@Override
						public void onFailure(int errorType) {
							// TODO Auto-generated method stub
							
						}
					});
		}else {
			
		}
	}
	
	@OnClick({R.id.enpay,R.id.btnBack,R.id.radioButton1,R.id.radioButton2,R.id.radioButton3})
	void OnClick(View v){
		switch (v.getId()) {
		case R.id.enpay:
			appAlipay();
			break;
		case R.id.btnBack:
			this.finish();
			break;
		case R.id.radioButton1:
			if(ye.isChecked())
			 {
				 wx.setChecked(false);
				 zf.setChecked(false);
			 }
			break;
		case R.id.radioButton2:
			if(wx.isChecked())
			 {
				 ye.setChecked(false);
				 zf.setChecked(false);
			 }
			break;
		case R.id.radioButton3:
			if(zf.isChecked())
			 {
				 wx.setChecked(false);
				 ye.setChecked(false);
			 }
			break;

		default:
			break;
		}
	}
	
	/**
	 * 支付返回Handler
	 * */
	private Handler mPayHandler = new Handler()
	{ 
		@Override
		public void handleMessage(Message msg) { 
			if(msg.what == PayActivityUtils.SDK_PAY_FLAG)//支付返回结果
			{
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult(); 
				String resultStatus = payResult.getResultStatus();
				 
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					DialogUtils.ShowToast(StopCar.this, "支付成功!");
					Utils.closeActivity(StopCar.this, Activity.RESULT_OK);
					startAct(Mx.class);
					StopCar.this.finish();
//					if (isPay) {
//						DialogUtils.ShowToast(StopCar.this, "支付成功!");
//						Utils.closeActivity(StopCar.this, Activity.RESULT_OK);
//						
//					}else {
//						//支付成功 
//						DialogUtils.ShowToast(StopCar.this, payActivityUtils.getResultStatusStr(resultStatus));
//						Utils.StartActivity(StopCar.this, OrderInfo.class,true);
//					}
				} else { 
//					editPwd.setText("");
//					trError.setVisibility(View.VISIBLE);
//					txtError.setText(payActivityUtils.getResultStatusStr(resultStatus)+",请到对账“更多”菜单中完成支付");
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						//支付结果确认中

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						//支付失败 
					}
				}
			}
		} 
	};
	
}
