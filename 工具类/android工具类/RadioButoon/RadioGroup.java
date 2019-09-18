 <RadioGroup
            android:id="@+id/radioGroup"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical" >

            <RadioButton
                android:id="@+id/rb_ye"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@android:color/transparent"
                android:button="@null"
                android:checked="true"
                android:drawablePadding="10dp"
                android:drawableLeft="@drawable/ic_ye"
                android:drawableRight="@android:drawable/btn_radio"
                android:text="Óà¶îÖ§¸¶"
                android:textColor="#000"
                android:textSize="16sp" />
            <View android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#ccc"/>
            <RadioButton
                android:id="@+id/rb_zfb"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@android:color/transparent"
                android:button="@null"
                android:drawablePadding="10dp"
                android:drawableLeft="@drawable/ic_zfb"
                android:drawableRight="@android:drawable/btn_radio"
                android:text="Ö§¸¶±¦Ö§¸¶"
                android:textColor="#000"
                android:textSize="16sp" />
            <View android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#ccc"/>
            <RadioButton
                android:id="@+id/rb_wx"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@android:color/transparent"
                android:button="@null"
                android:drawablePadding="10dp"
                android:drawableLeft="@drawable/ic_weixin"
                android:drawableRight="@android:drawable/btn_radio"
                android:text="Î¢ÐÅÖ§¸¶"
                android:textColor="#000"
                android:textSize="16sp" />
            <View android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#ccc"/>
        </RadioGroup>