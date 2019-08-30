#Android回调机制详情2
- Fragment向Activity传递数据

##方法一
###Fragment中
```
 //先定义一个回调接口
    public interface CallBackValue {
        public void SendMessageValue(String strValue);
    }
```

```
	//实例化接口
	public CallBackValue callBackValue;

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue = (CallBackValue) getActivity();
    }

	//调用接口方法
	callBackValue.SendMessageValue(strValue);
```

###Activity
```
//继承接口，接收数据
public class MainActivity implements HomeFragment.CallBackValue	{

	//接收数据
    @Override
    public void SendMessageValue(String strValue) {
        if (strValue.equals("MedicalCollectActivity")) {
            startActivity(new Intent(this, MedicalCollectActivity.class));
        }
    }

}
```

##方法二
###Fragment中
```
 //先定义一个回调接口
    public interface CallBackValue {
        public void SendMessageValue(String strValue);
    }
```

```
	//实例化接口
	public CallBackValue callBackValue;

    public void setCallBackValue(CallBackValue callBackValue1){
		callBackValue = callBackValue1;
	)

	//调用接口方法
	 setCallBackValue(new CallBackValue() {
                        @Override
                        public void SendMessageValue(String strValue) {

                        }
                    });
```

###Activity
```
FragmentManager fragmentManager1 = getSupportFragmentManager();
                MyDialogFragment2 myDialogFragment2 = MyDialogFragment2.
                        newInstance();
                myDialogFragment2.show(fragmentManager1, "dialogFragment1");
                //获取登录信息
                myDialogFragment2.setCallBackValue(new MyDialogFragment2.CallBackValue() {
                    @Override
                    public void SendMessageValue(String strValue) {
                        tvAccount.setText(strValue);
                    }
                });
```