<?xml version="1.0" encoding="utf-8"?>  
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    >  
  
    <android.support.v4.view.ViewPager  
        android:id="@+id/viewpage"  
        android:layout_width="match_parent"  
        android:layout_height="match_parent" />  
  
    <LinearLayout  
        android:id="@+id/llPoint"  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        android:layout_alignParentBottom="true"  
        android:layout_centerHorizontal="true"  
        android:layout_marginBottom="24.0dip"  
        android:gravity="center_horizontal"  
        android:orientation="horizontal" >  
        
    </LinearLayout>  
    <TextView   
        android:id="@+id/guideTv"  
        android:layout_width="130dp"  
        android:layout_height="40dp"  
        android:text="��������"  
        android:textColor="#ffffff"  
        android:background="#88000000"  
        android:textSize="14sp"  
        android:gravity="center"  
        android:layout_above="@id/llPoint"  
        android:layout_centerHorizontal="true"  
        android:layout_marginBottom="20dp"  
        android:visibility="gone"  
        />  
</RelativeLayout>  