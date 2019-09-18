// Set up the window layout
//设置屏幕属性，设置titlebar是系统的，就是app的主题

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
				
//屏幕上分为两部分 titleView 和ContentView。
//setContentView()就是设置的ContentView这一块的布局。

 requestWindowFeature(Window.FEATURE_NO_TITLE);
//这一句代码是把上面的TitleView移除掉，然后调用setContentView()的时候就会铺满全屏