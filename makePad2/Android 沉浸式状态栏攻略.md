#Android 沉浸式状态栏攻略

##首先引入依赖
```
 compile 'com.android.support:appcompat-v7:22.2.1'
 compile 'com.android.support:support-v4:22.2.1'
 compile 'com.android.support:design:22.2.0'
```

##colors.xml 和 styles.xml

首先我们定义几个颜色：

res/values/color.xml
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="primary">#FF03A9F4</color>
    <color name="primary_dark">#FF0288D1</color>
    <color name="status_bar_color">@color/primary_dark</color>
</resources>
```
下面定义几个styles.xml

注意文件夹的路径：

values/styles.xml
```
<resources>
    <style name="BaseAppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/primary</item>
        <item name="colorPrimaryDark">@color/primary_dark</item>
        <item name="colorAccent">#FF4081</item>
    </style>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="@style/BaseAppTheme">
    </style>
</resources>
```

values-v19
```
<resources>

    <style name="AppTheme" parent="@style/BaseAppTheme">
        <item name="android:windowTranslucentStatus">true</item>
    </style>
</resources>

```

- 注意我们的主题是基于NoActionBar的，android:windowTranslucentStatus这个属性是v19开始引入的。
- 该主题在AndroidManifest.xml 中引用

```
 <activity
            android:name=".ui.activity.MainActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme" />
```

##布局文件

activity_main.xml

```
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >


    <LinearLayout
        android:id="@+id/id_main_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:fitsSystemWindows="true"
            android:background="@color/colorPrimary"
            app:navigationIcon="@mipmap/button_daohang"
            >

            <TextView
                android:id="@+id/title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="标题"
                android:textColor="@color/white"
                android:textSize="22sp" />

        </android.support.v7.widget.Toolbar>


        <TextView
            android:id="@+id/id_tv_content"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:gravity="center"
            android:text="HelloWorld"
            android:textSize="30sp"/>
    </LinearLayout>


    <android.support.design.widget.NavigationView
        android:id="@+id/id_nv_menu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/header_just_username"
        app:menu="@menu/menu_drawer"
        />
</android.support.v4.widget.DrawerLayout>

```

DrawerLayout内部一个LinearLayout作为内容区域，一个NavigationView作为菜单。 
注意下Toolbar的高度设置为wrap_content。

然后我们的NavigationView中又依赖一个布局文件和一个menu的文件。

##header_just_username.xml
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="192dp"
                android:background="?attr/colorPrimaryDark"
                android:orientation="vertical"
                android:padding="16dp"
                android:fitsSystemWindows="true"
                android:theme="@style/ThemeOverlay.AppCompat.Dark">

    <TextView
        android:id="@+id/id_link"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_marginBottom="16dp"
        android:text="http://blog.csdn.net/lmj623565791"/>

    <TextView
        android:id="@+id/id_username"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/id_link"
        android:text="Zhang Hongyang"/>

    <ImageView
        android:layout_width="72dp"
        android:layout_height="72dp"
        android:layout_above="@id/id_username"
        android:layout_marginBottom="16dp"
        android:src="@mipmap/ic_launcher"/>


</RelativeLayout>
```
##menu_drawer

```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_home"
            android:icon="@drawable/ic_dashboard"
            android:title="Home"/>
        <item
            android:id="@+id/nav_messages"
            android:icon="@drawable/ic_event"
            android:title="Messages"/>
        <item
            android:id="@+id/nav_friends"
            android:icon="@drawable/ic_headset"
            android:title="Friends"/>
        <item
            android:id="@+id/nav_discussion"
            android:icon="@drawable/ic_forum"
            android:title="Discussion"/>
    </group>

    <item android:title="Sub items">
        <menu>
            <item
                android:icon="@drawable/ic_dashboard"
                android:title="Sub item 1"/>
            <item
                android:icon="@drawable/ic_forum"
                android:title="Sub item 2"/>
        </menu>
    </item>

</menu>
```

大体看完布局文件以后，有几个点要特别注意：

ToolBar高度设置为wrap_content
ToolBar添加属性android:fitsSystemWindows="true"
header_just_username.xml的跟布局RelativeLayout，添加属性android:fitsSystemWindows="true"
android:fitsSystemWindows这个属性，主要是通过调整当前设置这个属性的view的padding去为我们的status_bar留下空间。

根据上面的解释，如果你不写，那么状态栏和Toolbar就会有挤一块的感觉了

##Activity的代码
```
package com.zhy.colorfulstatusbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
         //Toolbar导航栏和文字title
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        //设置toolbar为Action对象
        toolbar.setNavigationIcon(R.mipmap.button_daohang);
    }

}

```