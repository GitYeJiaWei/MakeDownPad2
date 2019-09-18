 //框框设置
 //.xml中
 <EditText
                 android:id="@+id/et_rePassword"
                 android:layout_width="100dp"
                 android:layout_height="wrap_content"
                 android:layout_toRightOf="@+id/yanzheng"
                 android:background="@drawable/backgrand_shape_regist"
                 android:enabled="false"
                 android:hint="请输入验证码"
                 android:paddingBottom="10dp"
                 android:paddingTop="10dp"
                 android:singleLine="true"
                 android:textSize="15sp" />
				 
// drawable/backgrand_shape_regist
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle" >  
   <solid android:color="#ffffff" />  
   <stroke android:width="1dip" android:color="#4fa5d5"/>  
</shape>  


editText 文本框编辑

代码
et_rePassword.setEnabled(true);//可编辑

布局
android:background="@null"//背景色
android:enabled="false"   //不可编辑文本框

Editext: android:editable设置是否可编辑。
如果它的值为：“true” 可以编辑，若“false”不可编辑。


字母小写改为大写
et_rePassword.toUpperCase

android:hint="请输入车牌号"  //隐式提醒





