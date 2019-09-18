/**
	 * 如果希望浏览的网页后退而不是退出浏览器，需要WebView覆盖URL加载，让它自动生成历史访问记录， 那样就可以通过前进或后退访问已访问过的站点。
	 * */
	 //安卓自带的返回键在webview中
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();// 返回上一页面
				return true;
			} else {
				Show_Dwsp.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	//点击自设的返回键在webview中
	@OnClick(R.id.btnBack)
	void onBackClick(View v) {
		if (webview.canGoBack()) {
			webview.goBack(); // goBack()表示返回WebView的上一页面
			return;
		} else {
			Shoping.this.finish();
		}
	}