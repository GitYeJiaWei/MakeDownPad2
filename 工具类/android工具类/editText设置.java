 //�������
 //.xml��
 <EditText
                 android:id="@+id/et_rePassword"
                 android:layout_width="100dp"
                 android:layout_height="wrap_content"
                 android:layout_toRightOf="@+id/yanzheng"
                 android:background="@drawable/backgrand_shape_regist"
                 android:enabled="false"
                 android:hint="��������֤��"
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


editText �ı���༭

����
et_rePassword.setEnabled(true);//�ɱ༭

����
android:background="@null"//����ɫ
android:enabled="false"   //���ɱ༭�ı���

Editext: android:editable�����Ƿ�ɱ༭��
�������ֵΪ����true�� ���Ա༭������false�����ɱ༭��


��ĸСд��Ϊ��д
et_rePassword.toUpperCase

android:hint="�����복�ƺ�"  //��ʽ����





