一。属性大全解析

1、EditText输入的文字为密码形式的设置
(1)通过.xml里设置：
 把该EditText设为：android:password="true" // 以”.”形式显示文本
(2)在代码里设置：
通过设置EditText的setTransformationMethod()方法来实现隐藏密码或这显示密码。
editText.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
2、(1)EditText输入的文字为电话号码
Android:phoneNumber=”true”  //输入电话号码
3、EditText字数限制的设置
(1)在.xml中设置：android:maxLength=“50” 
(2)代码中设置：   
editText.setFilters(new InputFilter[]{newInputFilter.LengthFilter(100)});
4、EditText设置字体
android:typeface="monospace" //设置字型。字形有：normal, sans, serif,monospace
5、EditText是否可编辑
Android:editable // 是否可编辑
6、在EditText中软键盘的调起、关闭
(1)EditText有焦点（focusable为true）阻止输入法弹出
 editText=(EditText)findViewById(R.id.txtBody);
   editText.setOnTouchListener(new OnTouchListener(){  
         public boolean onTouch(View v, MotionEvent event){ 
            editText.setInputType(InputType.TYPE_NULL); //关闭软键盘     
            return false;
         }
    });
(2)当EidtText无焦点（focusable=false）时阻止输入法弹出
 InputMethodManager imm =
(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE); 
 imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
（3）调用数字键盘并设置输入类型和键盘为英文
etNumber.setInputType(InputType.TYPE_CLASS_NUMBER); //调用数字键盘
rlEditText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);//设置输入类型和键盘为英文 或者：android:inputType="textUri|textMultiLine"
（4）android:focusable="false"//键盘永远不会弹出
<activity android:name=".AddLinkman"android:windowSoftInputMode="adjustUnspecified|stateHidden"/>//不自动弹出键盘
 
//关闭键盘（比如输入结束后执行） InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(etEditText.getWindowToken(), 0);
 
//自动弹出键盘
((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
etEditText.requestFocus();//让EditText获得焦点，但是获得焦点并不会自动弹出键盘
7、android:layout_gravity和android:gravity的区别
(1)android:layout_gravity是本元素对父元素的重力方向。
(2)android:gravity是本元素所有子元素的重力方向。
8、android:padding和android:layout_margin区别
这两个都可以设置边距，但有细微的区别：
(1)android:padding是相对父view的边距
(2)android:layout_margin是相对同一级View的边距
例：LinearLayout是水平布局，下面有两个按钮，
(a)如果右边的按钮想距左边的按钮15px,因为这两个按钮是同一级的，应该用android:layout_margin；
(b)如果右边的按钮想距左边的距离为350px,应该用android:padding
9、android:numeric//只接受数字
android:numeric来控制输入的数字类型，一共有三种分别为integer（正整数）、signed（带符号整数，有正负）和decimal(浮点数)。
11、使用android:imeOptinos可对Android自带的软键盘进行一些界面上的设置：
android:imeOptions="flagNoExtractUi" //使软键盘不全屏显示，只占用一部分屏幕 同时,这个属性还能控件软键盘右下角按键的显示内容,默认情况下为回车键 android:imeOptions="actionNone" //输入框右侧不带任何提示 android:imeOptions="actionGo"   //右下角按键内容为'开始' android:imeOptions="actionSearch" //右下角按键为放大镜图片，搜索 android:imeOptions="actionSend"   //右下角按键内容为'发送' android:imeOptions="actionNext"  //右下角按键内容为'下一步' android:imeOptions="actionDone" //右下角按键内容为'完成'
12、限定edittext能输入数字和字母，并且默认输入为数字，如身份证号码
android:inputType="number" android:digits="0123456789xyzXYZ"
13、软键盘的调起导致原来的界面被挤上去，或者导致界面下面的tab导航被挤上去，解决方法如下
解决方法：
使用Manifest中的Activity的android:windowSoftInputMode的"adjustPan"属性。
另外注意：有关软键盘的问题可参考android:windowSoftInputMode中属性。
14、edittext光标详解 edittext.requestFocusFromTouch();//让光标放入到点击位置。 edittext.requestFocus();//默认方式获得焦点
EditText editor = (EditText)getCurrentView();//光标处插入 int cursor = editor.getSelectionStart(); editor.getText().insert(cursor,delta);
 
让光标移到末端（这样文字就会向前显示） EditText et = ... String text = "text"; et.setText(text); et.setSelection(text.length());
 
android:cursorVisible="false" 隐藏光标
android:background="#00000000"//不要文本框背景 

二。属性大全，非解析

android:layout_gravity="center_vertical" 设置控件显示的位置：默认top，这里居中显示，还有bottom android:hint="请输入数字！"

设置显示在空间上的提示信息 android:numeric="integer" 设置只能输入整数，如果是小数则是：

decimal android:singleLine="true" 设置单行输入，一旦设置为true，则文字不会自动换行。

android:password="true" 设置只能输入密码

android:textColor = "#ff8c00" 字体颜色

android:textStyle="bold" 字体，bold, italic, bolditalic android:textSize="20dip" 大小

android:capitalize = "characters" 以大写字母写

android:textAlign="center" EditText没有这个属性，但TextView有 android:textColorHighlight="#cccccc" 被选中文字的底色，默认为蓝色

android:textColorHint="#ffff00" 设置提示信息文字的颜色，默认为灰色 android:textScaleX="1.5" 控制字与字之间的间距

android:typeface="monospace" 字型，normal, sans, serif, monospace android:background="@null" 空间背景，这里没有，指透明

android:layout_weight="1" 权重，控制控件之间的地位,在控制控件显示的大小时蛮有用的。

android:textAppearance="?android:attr/textAppearanceLargeInverse" 文字外观，这里引用的是系统自带的一个外观，？表示系统是否有这种外观，否则使用默认的外观。不知道这样理解对不对？ 通过EditText的layout xml文件中的相关属性来实现:

1. 密码框属性 android:password="true" 这条可以让EditText显示的内容自动为星号，输入时内容会在1秒内变成*字样。

2. 纯数字 android:numeric="true" 这条可以让输入法自动变为数字输入键盘，同时仅允许0-9的数字输入

3. 仅允许 android:capitalize="cwj1987" 这样仅允许接受输入cwj1987，一般用于密码验证 下面是一些扩展的风格属性

android:editable="false" 设置EditText不可编辑

android:singleLine="true" 强制输入的内容在单行

android:ellipsize="end" 自动隐藏尾部溢出数据，一般用于文字内容过长一行无法全部显示时



