// Set up the window layout
//������Ļ���ԣ�����titlebar��ϵͳ�ģ�����app������

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
				
//��Ļ�Ϸ�Ϊ������ titleView ��ContentView��
//setContentView()�������õ�ContentView��һ��Ĳ��֡�

 requestWindowFeature(Window.FEATURE_NO_TITLE);
//��һ������ǰ������TitleView�Ƴ�����Ȼ�����setContentView()��ʱ��ͻ�����ȫ��