//��timerHandler.sendEmptyMessageDelayed(0, 1000);��ValueΪ��0���Ŀ���Ϣ�ӳ�1�롣
//�����Ϣ����ʹ��ͬһ��handler�� 
//ͨ��what��ͬ���ֲ�ͬ����Ϣ��Դ�� �Ӷ���ȡ��Ϣ����

handlerText.sendEmptyMessageDelayed(1, 1000);//1 ��what���ܵ�ֵ

Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                	et_yanzheng.setText(time + "��");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                    et_yanzheng.setClickable(false);//button ��ť���ܵ��
                } else {
                	et_yanzheng.setText("��ȡ��֤��");
                    time = 60;
                    et_yanzheng.setClickable(true);
                }
            } else {
                et_yanzheng.setText("��ȡ��֤��");
                time = 60;
                et_yanzheng.setClickable(true);
            }
        };
    };