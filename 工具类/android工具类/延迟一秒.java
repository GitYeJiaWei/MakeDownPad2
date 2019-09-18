//“timerHandler.sendEmptyMessageDelayed(0, 1000);”Value为“0”的空消息延迟1秒。
//多个消息可以使用同一个handler， 
//通过what不同区分不同的消息来源， 从而获取消息内容

handlerText.sendEmptyMessageDelayed(1, 1000);//1 是what接受的值

Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                	et_yanzheng.setText(time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                    et_yanzheng.setClickable(false);//button 按钮不能点击
                } else {
                	et_yanzheng.setText("获取验证码");
                    time = 60;
                    et_yanzheng.setClickable(true);
                }
            } else {
                et_yanzheng.setText("获取验证码");
                time = 60;
                et_yanzheng.setClickable(true);
            }
        };
    };