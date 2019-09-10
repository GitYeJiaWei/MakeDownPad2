#Android下拉列表Spinner更改边框和箭头

- **直接在drawable文件下创建xml文件，写spinner控件的时候background属性引用样式即可**
```java
	<LinearLayout
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:layout_margin="5dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="80dp"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="废物类型：" />

        <Spinner
            android:id="@+id/sp_kuqu"
            android:singleLine="true"
            android:gravity="center"
            android:background="@drawable/spinner_back"
            android:layout_width="200dp"
            android:layout_height="match_parent"
            />
    </LinearLayout>
```

```java
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android" >
    //第一组item 圆角边框。
    <item>
        <shape>
            <stroke
                android:width="1dp"
            android:color="@color/md_grey_500" />		//边框线宽度//边框线颜色
            <corners android:radius="10dp" />		//圆角度数
            <solid android:color="#ffffff" >		//背景颜色
            </solid>
            <padding android:right="5dp" />		//边距
        </shape>
    </item>
    //第二组item 插入图片(spinner箭头)
    <item>
        <bitmap
            android:gravity="end"
        android:src="@mipmap/xiala" />	//箭头图片//end把箭头放再spinner最右侧
    </item>
</layer-list>
```