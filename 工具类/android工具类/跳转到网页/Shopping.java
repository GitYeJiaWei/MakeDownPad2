package com.boss.taxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Shopping extends BaseActivity{

	private WebView webview;
	@Bind(R.id.btnBack)
	LinearLayout btnBack;
	@Bind(R.id.btnMore)
	LinearLayout btnMore;
	@Bind(R.id.pageTitle)
	TextView pageTitle;
	
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_shopping);
		// 注解框架
		ButterKnife.bind(this);
		initview();

		webview = (WebView) findViewById(R.id.webView1);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		// 加载需要显示的网页
		webview.loadUrl(url);
		// 设置Web视图
		webview.setWebViewClient(new HelloWebViewClient());
	}

	private void initview() {
		
		btnBack = (LinearLayout) this.findViewById(R.id.btnBack);
		btnMore = (LinearLayout) this.findViewById(R.id.btnMore);
		pageTitle = (TextView) this.findViewById(R.id.pageTitle);

		btnMore.setVisibility(View.GONE);
		
		Intent intent=getIntent();
		String key=intent.getStringExtra("key");
		if (key.equals("商城")) {
			pageTitle.setText("行车助手-商城");
			url="http://www.sxx001.com/Wap/Home/Index";
		}else{
			pageTitle.setText("投影仪");
			url="http://www.sxx001.com/Wap/Goods/GoodsDetails?ID=4272";
		}
	}

	@OnClick(R.id.btnBack)
	void onBackClick(View v) {
		if (webview.canGoBack()) {
			webview.goBack(); // goBack()表示返回WebView的上一页面
			return;
		} else {
			Shopping.this.finish();
		}
	}

	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		return false;
	}
	*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webview.canGoBack()) {
				webview.goBack();// 返回上一页面
				return true;
			} else {
				Shopping.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// Web视图
	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
