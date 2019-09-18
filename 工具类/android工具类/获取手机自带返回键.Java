@Override      
	 public boolean dispatchKeyEvent(KeyEvent event) {          
		 if (event.getKeyCode() == KeyEvent.KEYCODE_BACK 
				 && event.getAction() == KeyEvent.ACTION_DOWN 
				 && event.getRepeatCount() == 0) {   
			 //具体的操作代码              
			 new AlertDialog.Builder(this)  
			 .setTitle("确定退出程序么")    
			 .setCancelable(false)//其他地方都不能点击
			 .setNegativeButton("取消", new DialogInterface.OnClickListener() {            
				 @Override               
				 public void onClick(DialogInterface dialog, int which){             
					 
				 }                       
				 })              
			 .setPositiveButton("确定", new DialogInterface.OnClickListener(){  
				 public void onClick(DialogInterface dialog, int whichButton) { 
				 finish();
				 }                   
				 }).show();           
			 return true;         
			 }         
		 return super.dispatchKeyEvent(event); 
	 }
	 
	 
	 
 // 捕获手机自带返回键的方法1  
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
            // 按下BACK，同时没有重复  
            Log.d(TAG, "onKeyDown()");  
        }  
  
        return super.onKeyDown(keyCode, event);  
    }  
  
    // 捕获手机自带返回键的方法2  
    @Override  
    public void onBackPressed() {  
        Log.d(TAG, "onBackPressed()");  
        super.onBackPressed();  
    }  