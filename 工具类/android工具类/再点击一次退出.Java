// 返回键
		private long exitTime = 0;

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					DialogUtils.ShowToast(MainActivity.this, "再按一次退出程序");
					exitTime = System.currentTimeMillis();
				} else {
					//关闭所有的界面
					System.exit(0);
				}
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}