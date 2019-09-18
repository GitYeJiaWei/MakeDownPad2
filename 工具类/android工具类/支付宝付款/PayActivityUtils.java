package com.boss.demo.alipay_utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
 

public class PayActivityUtils {
	// 商户PID 签约的支付宝账号
	private static final String PARTNER = "2088311437680014";
	// 商户收款账号
	private static final String SELLER = "2942465303@qq.com";
	// 商户私钥，pkcs8格式
	private static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJnhDoD0wCi7nHt7qEPFe4WyVXZcqx/stZ0GKVnUl3V07dm4lXPSQyFOZfJWOu8vFAz/B4PmHHdUk3lhFMMEw0FTqPCKgyykI/PDoVdbWKIu3SdcB5tGFyNbZ2NWj/gYKcvSCwHBG0lrBVRuNpp6Ohc3Dhl6yIUeRAQmKGcZDp0pAgMBAAECgYAnJ6EtJxoOQ07aDOkP9ZwstOgSKhpREUZVIlA7T2EKvioXkGRXCFWUkAJY26A7ZFtRw0MCSPdNVfQAmrdRuFCGVeWBmHfaqo2N8HUrpqDaRQMR7NwPd5pwAOLzZJhUkyYzvu3gZ073ad+pmVTQHdIh6GGT4RGU7+OC6venyzjfQQJBAMaesopMVr1v7RnoB44bcRIoITyrP9PkvK4B2XPngbwNmjkhZooOmDhIKXXC9zqTpBXmS28Ec+Kju56oumWeY3UCQQDGVXjMDv8g+Ui0XFeKuqE1zSwx76oM6OeT/aLSsmRgPjq6uNxJa2Y1hDEIz1r+xXa2v1mUjh9VaNo07hnV/+BlAkB5NsjHVidpnTEaKlzSATVUW2FNYUWmz2XG3CIVdifa2IJSc2vcvWNwlbmXwNCY0xnEs7M9oX4HxtztyxNWiP8xAkB7fmbr8H6NoorRuxnLSO/uVyWopkllJnRjoq+KsAduIfFhqI+Jq7UFp5z8llhbgMqtebImnOQHw3TzNjqiFGQ9AkEAtTC38jCqoEinFZDVOUsaimXs+zOJTjgSCBt/PHcfskB6apIq0A5Ze6zcSwXcoX5qNEQeoju14sBr7bbkvKDHkA==";
	private static final String notify_url = "http://www.zftong.cn/alipaydirect/notify_url2.aspx";
	private static final String return_url = "m.alipay.com"; 
	private static Handler mPayHandler = null;
	private static Context mContext= null;
	//=================对外参数================
	//支付结果代号
	public static final int SDK_PAY_FLAG = 1;
	public PayActivityUtils(Context context, Handler _handler)
	{ 
		mContext = context;
		mPayHandler = _handler;
	}
	/** 
	 * *支付对外方法
	 * @param subject   商品名称
	 * @param body		商品描述
	 * @param price		价格
	 * @param ddh		订单号
	 */
	public void Pay(String subject, String body, String price, String ddh)
	{
		if(CheckIsEmpty())
		{
			new AlertDialog.Builder(mContext)
			.setTitle("警告") 
			.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
			.setPositiveButton("确  定",
					new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface dialoginterface, int i) {
							((Activity)mContext).finish();
						}
					}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo(subject, body, price, ddh); 
		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
  
		Runnable payRunnable = new Runnable() {
 
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity)mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mPayHandler.sendMessage(msg);
			}
		}; 
		if(mPayHandler != null)
		{
			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	}
	
	private boolean CheckIsEmpty()
	{
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			return true;
		}
		return false;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	/**
	 * 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	/**
	 * 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price, String ddh) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + ddh + "\"";
		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";
		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\""; 
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";
		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\""+return_url+"\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	public String getResultStatusStr(String codeStatus)
	{
		if(codeStatus.equals("9000"))
		{ 
			return "订单支付成功";
		}
		else if(codeStatus.equals("8000"))
		{
			return "正在处理中...";
		} 
		else if(codeStatus.equals("4000"))
		{
			return "订单支付失败";
		}
		else if(codeStatus.equals("6001"))
		{ 
			return "用户中途取消";
		}
		else if(codeStatus.equals("6002"))
		{
			return "网络连接出错";
		}
		return "未知错误！";
	}
}
