 <AutoCompleteTextView
        android:id="@+id/auto_tv_search"
        style="@style/AutoCompleteStyle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/ll_title"
        android:layout_margin="7dp"
        android:background="@drawable/textview_border"
		//true焦点指向这里
        android:focusableInTouchMode="true"
        android:gravity="center_vertical"
        android:hint="搜索"
        android:minHeight="40dp"
        android:padding="5dp"
		//单行设置 设置为true时多的将出现t...,没有则会出现换行
        android:singleLine="true"
        android:textSize="@dimen/sp2" />
		//@dimen/sp2为 28sp