intent:
//��ֵ
Intent intent=new Intent(CarBookCw.this,ParkingRecordAcitivity.class);
	intent.putExtra("a","wu");
	intent.putExtra("type", 2);
	startActivity(intent);
	
//ȡֵ
	Intent intent=getIntent();  
    int a=intent.getStringExtra("a", "wu");  
    int b=intent.getIntExtra("b", 0);  
	
	
bundle:
//��ֵ
��1���½�һ��bundle��
	Bundle mBundle = new Bundle();
��2��bundle���м������ݣ�key -value����ʽ����һ��activity����ȡ���ݵ�ʱ�򣬾�Ҫ�õ�key���ҳ���Ӧ��value��
	mBundle.putString("Data", "data from TestBundle");
 (3)�½�һ��intent���󣬲�����bundle�������intent����
	Intent intent = new Intent();    
	intent.setClass(TestBundle.this, Target.class);    
	intent.putExtras(mBundle);
 ��4������һ��activity�л�ȡ����
	Bundle bundle = getIntent().getExtras();//�õ���������bundle
	String data = bundle.getString("Data");//��������
	
					Bundle bundle = new Bundle();
					bundle.putString("Spid", model.optString("id") + "");
					bundle.putString("Sjh", GlobalClass.getLoginUserInfo().getSjh());
					Intent intent = new Intent();
					intent.putExtras(bundle);
					intent.setClass(context, SetMeony.class);
					((Activity) context).startActivityForResult(intent,
							MyCarParkList.CODE);
	
//�ж�bundle�Ƿ���ֵ
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