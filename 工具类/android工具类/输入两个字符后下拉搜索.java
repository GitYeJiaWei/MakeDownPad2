 <AutoCompleteTextView
        android:id="@+id/auto_tv_search"
        style="@style/AutoCompleteStyle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/ll_title"
        android:layout_margin="7dp"
        android:background="@drawable/textview_border"
		//true����ָ������
        android:focusableInTouchMode="true"
        android:gravity="center_vertical"
        android:hint="����"
        android:minHeight="40dp"
        android:padding="5dp"
		//�������� ����Ϊtrueʱ��Ľ�����t...,û�������ֻ���
        android:singleLine="true"
        android:textSize="@dimen/sp2" />
		//@dimen/sp2Ϊ 28sp