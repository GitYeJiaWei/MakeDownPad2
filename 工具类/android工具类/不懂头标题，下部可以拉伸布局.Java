<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/ll_content"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
    android:orientation="vertical" >

    <include
        android:id="@+id/header"
        layout="@layout/header" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
         <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical" >

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/num"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="车 牌 号："
                android:textColor="#666666"
                android:textSize="20dp" />

            <TextView
                android:id="@+id/numget"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="粤AY6U78"
                android:textColor="#666666"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/seat"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="车位所属："
                android:textColor="#666666"
                android:textSize="20dp" />

            <TextView
                android:id="@+id/getseat"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="qwe"
                android:textColor="#666666"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/time"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="进场时间："
                android:textColor="#666666"
                android:textSize="20dp" />

            <TextView
                android:id="@+id/timeget"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="2017-02-12 11:12"
                android:textColor="#666666"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/stop"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="停车时长："
                android:textColor="#666666"
                android:textSize="20dp" />

            <TextView
                android:id="@+id/stopget"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="286.28小时"
                android:textColor="#666666"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/money"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="金 额："
                android:textColor="#666666"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="30dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/moneyget"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="￥  0.05"
                android:textColor="#FF0000"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="10dp" >

            <TextView
                android:id="@+id/pay"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="选择支付方式"
                android:textColor="#666666"
                android:textSize="20dp" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp" >

            <RadioButton
                android:id="@+id/radioButton1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:drawableLeft="@drawable/ey"
                android:text="账户余额"
                android:textColor="#666666" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp" >

            <RadioButton
                android:id="@+id/radioButton2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:drawableLeft="@drawable/wechat"
                android:text="微信支付"
                android:textColor="#666666" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp" >

            <RadioButton
                android:id="@+id/radioButton3"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:drawableLeft="@drawable/blipay"
                android:text="支付宝支付"
                android:textColor="#666666" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#cccccc" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="10dp"
            android:layout_marginLeft="10dp"
            android:layout_marginRight="10dp"
            android:layout_marginTop="10dp"
            android:gravity="center" >

            <Button
                android:id="@+id/enpay"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:background="#336699"
                android:text="确 认 支 付"
                android:textColor="#FFFFFF"
                android:textSize="20sp" />
        </LinearLayout>
        </LinearLayout>
    </ScrollView>

</LinearLayout>