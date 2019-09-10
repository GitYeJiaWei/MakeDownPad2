#Android动态修改ToolBar的Menu菜单

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/%E5%85%B6%E5%AE%83%E5%9B%BE%E7%89%87/20160415113840655.gif)

##实现

> 实现很简单，就是一个具有3个Action的Menu，在我们滑动到不同状态的时候，把对应的Action隐藏了。 
开始上货

##Menu

>Menu下添加3个Item

```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="com.kongqw.myapplication.MainActivity">
    <item
        android:id="@+id/search_button"
        android:icon="@mipmap/navigation_icon_search"
        android:orderInCategory="100"
        android:title="搜索"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/scan_button"
        android:icon="@mipmap/navigation_icon_sweep"
        android:orderInCategory="100"
        android:title="扫描"
        app:showAsAction="ifRoom" />

    <item
        android:id="@+id/setting_button"
        android:icon="@mipmap/titlebar_icon_settings_normal"
        android:orderInCategory="100"
        android:title="设置"
        app:showAsAction="ifRoom" />
</menu>
```

##重写onPrepareOptionsMenu方法

>用来根据ViewPager显示页面控制Menu显示的Item 
Demo里我用的是ViewPager滑动来控制，可以根据自己需求，不过大多数都是判断ViewPager吧。

```
@Override
public boolean onPrepareOptionsMenu(Menu menu) {
    // 动态设置ToolBar状态
    switch (mViewPager.getCurrentItem()) {
        case 0:
            menu.findItem(R.id.search_button).setVisible(true);
            menu.findItem(R.id.scan_button).setVisible(true);
            menu.findItem(R.id.setting_button).setVisible(false);
            break;
        case 1:
            menu.findItem(R.id.search_button).setVisible(false);
            menu.findItem(R.id.scan_button).setVisible(false);
            menu.findItem(R.id.setting_button).setVisible(false);
            break;
        case 2:
            menu.findItem(R.id.search_button).setVisible(false);
            menu.findItem(R.id.scan_button).setVisible(false);
            menu.findItem(R.id.setting_button).setVisible(true);
            break;
    }
    return super.onPrepareOptionsMenu(menu);
}
```

##Item点击
```
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.search_button) {
        Toast.makeText(getApplicationContext(), "搜索", Toast.LENGTH_SHORT).show();
        return true;
    } else if (id == R.id.scan_button) {
        Toast.makeText(getApplicationContext(), "扫描", Toast.LENGTH_SHORT).show();
        return true;
    } else if (id == R.id.setting_button) {
        Toast.makeText(getApplicationContext(), "设置", Toast.LENGTH_SHORT).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
}
```

##监听ViewPager滑动，改变Menu状态（重点）
>调用invalidateOptionsMenu();方法从新加载Menu，即回调onPrepareOptionsMenu方法

```
mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
        invalidateOptionsMenu();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
```

##Code（参考）
>就是Android Studio默认创建的带有ViewPager的Demo

```
package com.kongqw.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        switch (mViewPager.getCurrentItem()) {
            case 0:
                menu.findItem(R.id.search_button).setVisible(true);
                menu.findItem(R.id.scan_button).setVisible(true);
                menu.findItem(R.id.setting_button).setVisible(false);
                break;
            case 1:
                menu.findItem(R.id.search_button).setVisible(false);
                menu.findItem(R.id.scan_button).setVisible(false);
                menu.findItem(R.id.setting_button).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.search_button).setVisible(false);
                menu.findItem(R.id.scan_button).setVisible(false);
                menu.findItem(R.id.setting_button).setVisible(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_button) {
            Toast.makeText(getApplicationContext(), "搜索", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.scan_button) {
            Toast.makeText(getApplicationContext(), "扫描", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.setting_button) {
            Toast.makeText(getApplicationContext(), "设置", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
```