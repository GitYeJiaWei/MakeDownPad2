package com.boss.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class Utils {
	@SuppressLint("SimpleDateFormat")
	public static Date ToDateTime(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = sdf.parse(dateString);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}

	public static String GetDataTimeString(String dataFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);// ֻ��ı������ʽ
		String dateStr = sdf.format(new Date());
		return dateStr;
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	// �����֤
	public static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // �ж�С�����2λ�����ֵ�������ʽ
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	@SuppressLint("SimpleDateFormat")
	public static String GetCurrentDateString(String formatter) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(formatter);
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	public static String RemoveStringEndWidth(String str, String c) {
		if (str.endsWith(c)) {
			str = str.substring(0, (str.length() - c.length()));
		}
		return str;
	}

	public static void RemoveSharePreference(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(key,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit().remove(key);
		editor.commit();
	}

	public static String ReadSharePreference(Context context, String key,
			String keyString) {
		SharedPreferences preferences = context.getSharedPreferences(key,
				context.MODE_PRIVATE);
		return preferences.getString(keyString, null);
	}

	@SuppressLint("CommitPrefEdits")
	public static void WriteSharePreference(Context context, String key,
			String keyString, String value) {
		SharedPreferences preferences = context.getSharedPreferences(key,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(keyString, value);
		editor.commit();
	}

	// ������activity
	public static void StartActivity(Context context, Class<?> newClass,
			Boolean coloseSelf) {
		Intent intent = new Intent();
		intent.setClass(context, newClass);
		context.startActivity(intent);
		if (coloseSelf) {
			// ���б����Ƴ�
			CloseActivity.RemoveActivity(context);
			((Activity) context).finish();
		}
	}
	
	public static void startActivityForResult(Context context, Class<?> newClass,
			int code) {
		Intent intent = new Intent();
		intent.setClass(context, newClass);
		context.startActivity(intent);
	}

	public static void closeActivity(Activity context, int code) {
		CloseActivity.RemoveActivity(context);
		context.setResult(Activity.RESULT_OK);
		context.finish();
	}
	
	public static void closeActivity(Activity context) {
		CloseActivity.RemoveActivity(context);
		context.finish();
	}

	public static void StartActivity(final Context context,
			final Class<?> newClass, final Bundle bundle,
			final Boolean coloseSelf) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(context, newClass);
		context.startActivity(intent);
		if (coloseSelf) {
			// ���б����Ƴ�
			CloseActivity.RemoveActivity(context);
			((Activity) context).finish();
		}
	}
	
	public static void StartActivityForResult(Context context, Class<?> newClass,
			Bundle bundle,int code) {
		StartActivityForResult(context, newClass,bundle, code, false);
	}
	
	public static void StartActivityForResult(final Context context,
			final Class<?> newClass,Bundle bundle, int code,
			final Boolean coloseSelf) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(context, newClass);
		((Activity)context).startActivityForResult(intent, code);
		if (coloseSelf) {
			// ���б����Ƴ�
			CloseActivity.RemoveActivity(context);
			((Activity) context).finish();
		}
	}

	public static void StartActivity(Context context, Class<?> newClass,
			Bundle bundle) {
		StartActivity(context, newClass, bundle, false);
	}

	public static void StartActivity(Context context, Class<?> newClass) {
		StartActivity(context, newClass, false);
	}

	public static Class<?> GetClass(String contextString) {
		Class<?> newClass = null;
		try {
			if (contextString.indexOf("@") != -1) {
				contextString = contextString.substring(0,
						contextString.indexOf("@"));
			}
			newClass = Class.forName(contextString);
		} catch (ClassNotFoundException e) {

		}
		return newClass;
	}

	public static JSONArray GetJsonArrayByHeader(Object obj, String header) {
		// ת��JSON��JSONARRAY
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray array = null;
		try {
			array = jsonObject.getJSONArray(header);
		} catch (JSONException e) {
			return null;
		}
		return array;
	}

	// ��ȡSDK�汾��
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}

	public static String Md5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return result;
	}

	// �ֽ�תʮ������
	public static String getHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��

		while (baos.toByteArray().length / 1024 > 1000) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}

	public static Bitmap comp(Bitmap image, float imgWidth, float imgHeight) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// �ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// ����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// ���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = imgHeight;// �������ø߶�Ϊ800f
		float ww = imgWidth;// �������ÿ��Ϊ480f
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// ѹ���ñ�����С���ٽ�������ѹ��
	}

	public static Bitmap ByteArrayToBitMap(byte[] bytes) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return bitmap;
	}

	public static byte[] BitmapToByteArray(Bitmap image) {
		if (image == null) {
			return new byte[] {};
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static byte[] ImageViewToByteArray(ImageView imageView) {
		Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		return BitmapToByteArray(image);
	}

	public static Bitmap ImageViewToBitmap(ImageView imageView) {
		return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	}

	// �����������AQI
	public static int GetAQI(int pm25) {
		double Iheight = 0, Ilow = 0, Cheight = 0, Clow = 0;
		if (pm25 >= 0 && pm25 <= 15.4) // ��
		{
			Iheight = 50;
			Ilow = 0;
			Cheight = 15.4;
			Clow = 0;
		} else if (pm25 >= 15.5 && pm25 <= 40.4) // ��
		{
			Iheight = 100;
			Ilow = 51;
			Cheight = 40.4;
			Clow = 15.5;
		} else if (pm25 >= 40.5 && pm25 <= 500.4) // ��
		{
			Iheight = 500;
			Ilow = 101;
			Cheight = 500.4;
			Clow = 40.5;
		}
		double i = (Iheight - Ilow) / (Cheight - Clow) * (pm25 - Clow) + Ilow;
		return (int) i;
	}

	// ���2��С��
	public static float getTwoDecimalByType(float value) {
		return getDecimalByType(value, 2, BigDecimal.ROUND_DOWN);
	}

	// ���decimal��С��
	public static float getDecimalByType(float value, int decimal) {
		return getDecimalByType(value, decimal, BigDecimal.ROUND_DOWN);
	}

	public static float getDecimalByType(float value, int decimal, int type) {
		BigDecimal bigDecimal = new BigDecimal(value);
		float newValue = bigDecimal.setScale(decimal, type).floatValue();
		return newValue;
	}
	
	//����绰
	public static void callTel(Activity act,String number){
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
        act.startActivity(intent);  
	}
}
