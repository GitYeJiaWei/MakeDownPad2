 //������APP����������Ȼ����ת������ҳ���ڽ����¼����
 //SplashScreen.java ������������GuideViewActivity.java ������ҳ
 //��������
 boolean isFirstIn = false;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final long SPLASH_DELAY_MILLIS = 3000;
    public static final String SHAREDPREFERENCES_NAME = "first_pref";
    public static final String IS_FIRST_IN="isFirstIn";
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
	    
	//��SharedPreferences �ж��Ƿ��״δ�APP
	private void init() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        isFirstIn = preferences.getBoolean(IS_FIRST_IN, true);
        if (isFirstIn) {
            mHandler.sendEmptyMessage(GO_GUIDE);

        } else {
            mHandler.sendEmptyMessage(GO_HOME);
        }
    }
	
//����ҳ����

 //�����Ѿ���������ҳ
    private void setGuided() {
        SharedPreferences preferences = getSharedPreferences(SplashScreen.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // ��������
        editor.putBoolean(SplashScreen.IS_FIRST_IN, false);
        // �ύ�޸�
        editor.commit();
    }