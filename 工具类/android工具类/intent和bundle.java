intent:
//传值
Intent intent=new Intent(CarBookCw.this,ParkingRecordAcitivity.class);
	intent.putExtra("a","wu");
	intent.putExtra("type", 2);
	startActivity(intent);
	
//取值
	Intent intent=getIntent();  
    int a=intent.getStringExtra("a", "wu");  
    int b=intent.getIntExtra("b", 0);  
	
	
bundle:
//传值
（1）新建一个bundle类
	Bundle mBundle = new Bundle();
（2）bundle类中加入数据（key -value的形式，另一个activity里面取数据的时候，就要用到key，找出对应的value）
	mBundle.putString("Data", "data from TestBundle");
 (3)新建一个intent对象，并将该bundle加入这个intent对象
	Intent intent = new Intent();    
	intent.setClass(TestBundle.this, Target.class);    
	intent.putExtras(mBundle);
 （4）在另一个activity中获取数据
	Bundle bundle = getIntent().getExtras();//得到传过来的bundle
	String data = bundle.getString("Data");//读出数据
	
					Bundle bundle = new Bundle();
					bundle.putString("Spid", model.optString("id") + "");
					bundle.putString("Sjh", GlobalClass.getLoginUserInfo().getSjh());
					Intent intent = new Intent();
					intent.putExtras(bundle);
					intent.setClass(context, SetMeony.class);
					((Activity) context).startActivityForResult(intent,
							MyCarParkList.CODE);
	
//判断bundle是否有值
		Bundle bundle = getIntent().getExtras();
		if(bundle.containsKey("Spid") && bundle !=null)
		{
			Spid=getIntent().getStringExtra("Spid");
		}
		else{
			Spid ="";
		}
		if(bundle.containsKey("Sjh") && bundle !=null)
		{
			Sjh=getIntent().getStringExtra("Sjh");
		}
		else{
			Sjh ="";
		}