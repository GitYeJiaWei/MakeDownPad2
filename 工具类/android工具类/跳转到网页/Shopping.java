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
		// ע����
		ButterKnife.bind(this);
		initview();

		webview = (WebView) findViewById(R.id.webView1);
		// ����WebView���ԣ��ܹ�ִ��Javascript�ű�
		webview.getSettings().setJavaScriptEnabled(true);
		// ������Ҫ��ʾ����ҳ
		webview.loadUrl(url);
		// ����Web��ͼ
		webview.setWebViewClient(new HelloWebViewClient());
	}

	private void initview() {
		
		btnBack = (LinearLayout) this.findViewById(R.id.btnBack);
		btnMore = (LinearLayout) this.findViewById(R.id.btnMore);
		pageTitle = (TextView) this.findViewById(R.id.pageTitle);

		btnMore.setVisibility(View.GONE);
		
		Intent intent=getIntent();
		String key=intent.getStringExtra("key");
		if (key.equals("�̳�")) {
			pageTitle.setText("�г�����-�̳�");
			url="http://www.sxx001.com/Wap/Home/Index";
		}else{
			pageTitle.setText("ͶӰ��");
			url="http://www.sxx001.com/Wap/Goods/GoodsDetails?ID=4272";
		}
	}

	@OnClick(R.id.btnBack)
	void onBackClick(View v) {
		if (webview.canGoBack()) {
			webview.goBack(); // goBack()��ʾ����WebView����һҳ��
			return;
		} else {
			Shopping.this.finish();
		}
	}

	// ���û���
	// ����Activity���onKeyDown(int keyCoder,KeyEvent event)����
	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack(); // goBack()��ʾ����WebView����һҳ��
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
				webview.goBack();// ������һҳ��
				return true;
			} else {
				Shopping.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// Web��ͼ
	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
