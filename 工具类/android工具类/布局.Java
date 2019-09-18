//在RelativeLayout中 相对布局

Android:layout_alignParentRight="true" 
//使当前控件的右端和父控件的右端对齐。这里属性值只能为true或false，默认false。
android:layout_alignParentRight="true" //属性是子控件针对父容器的。 且父容器必须是RelativeLayout

android:layout_alignParentBottom="true" //整个布局的最下方

android:layout_marginLeft="10dip" 
//使当前控件左边空出相应的空间。 

 android:layout_toLeftOf="@id/ok" 
//使当前控件置于id为ok的控件的左边。 
android:layout_alignTop="@id/ok" 
//使当前控件与id控件的上端对齐。

*android:layout_centerHrizontal          //是否支持横屏或竖屏 
*android:layout_centerVertical             //这个根据单词的意思：中心垂直 

android:layout_centerInParent="true"  --将控件置于父控件的中心位置
android:layout_centerHorizontal="true"  --将本控件置于父控件水平方向的中心位置
android:layout_centerVertical="true"  --将本控件置于父控件垂直方向的中心位置


android:scaleType=“centerCrop”    //按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

//注意在RelativeLayout中