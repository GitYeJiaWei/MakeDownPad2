@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            moveTaskToBack(false);  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 

//里面的moveTaskToBack()是关键。
// Android按返回键退出程序但不销毁，程序后台运行，同QQ退出处理方式