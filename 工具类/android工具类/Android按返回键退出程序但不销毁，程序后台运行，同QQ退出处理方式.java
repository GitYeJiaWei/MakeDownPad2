@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            moveTaskToBack(false);  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 

//�����moveTaskToBack()�ǹؼ���
// Android�����ؼ��˳����򵫲����٣������̨���У�ͬQQ�˳�����ʽ