package com.boss.demo.activity;

import java.util.ArrayList;
import java.util.List;

import com.boss.demo.adapter.GuideViewAdapter;
import com.sxx001.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuideViewActivity extends BaseActivity {  
    private ViewPager viewPage;  
    // 图片  
    private int[] imageView = { R.drawable.yindaoye1, R.drawable.yindaoye2,  
            R.drawable.yindaoye3, R.drawable.yindaoye4 };  
    private List<View> list;  
    // 底部小点的图片  
    private LinearLayout llPoint;  
    //立即进入按钮  
    private TextView textView;  
    private String stype;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.guide_view_acitivyt);  
        initview();  
        initoper();  
        addView();  
        addPoint();  
  
    }  
    
    //设置已经看过引导页
    private void setGuided() {
        SharedPreferences preferences = getSharedPreferences(SplashScreen.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // 存入数据
        editor.putBoolean(SplashScreen.IS_FIRST_IN, false);
        // 提交修改
        editor.commit();
    }
    
    private void initoper() {  
        // 进入按钮  
        textView.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View arg0) {  
                //PageManage.toPageKeepOldPageState(PageDataKey.login);  
            	setGuided();
            	if (stype.equals("1")) {
            		Intent intent = new Intent(GuideViewActivity.this,
							MainActivity.class);
						startActivity(intent);
						finish();
				} else {
					Intent intent = new Intent(GuideViewActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
            }  
        });  
  
        // 2.监听当前显示的页面，将对应的小圆点设置为选中状态，其它设置为未选中状态  
        viewPage.addOnPageChangeListener(new OnPageChangeListener() {  
            @Override  
            public void onPageSelected(int position) {  
                monitorPoint(position);  
            }  
  
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
            }  
  
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
               // UIUtils.log("arg0--" + arg0);  
            }  
        });  
  
    }  
  
    private void initview() {  
        viewPage = (ViewPager) findViewById(R.id.viewpage);  
        llPoint = (LinearLayout) findViewById(R.id.llPoint);  
        textView = (TextView) findViewById(R.id.guideTv);  
        stype=getIntent().getStringExtra("stype");
    }  
  
    /**  
     * 添加图片到view  
     */  
    private void addView() {  
        list = new ArrayList<View>();  
        // 将imageview添加到view  
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(  
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);  
        for (int i = 0; i < imageView.length; i++) {  
            ImageView iv = new ImageView(this);  
            iv.setLayoutParams(params);  
            iv.setScaleType(ScaleType.FIT_XY);  
            iv.setImageResource(imageView[i]);  
            list.add(iv);  
        }  
        // 加入适配器  
        viewPage.setAdapter(new GuideViewAdapter(list));  
  
    }  
  
    /**  
     * 添加小圆点  
     */  
    private void addPoint() {  
        // 1.根据图片多少，添加多少小圆点  
        for (int i = 0; i < imageView.length; i++) {  
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(  
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
            if (i < 1) {  
                pointParams.setMargins(0, 0, 0, 0);  
            } else {  
                pointParams.setMargins(10, 0, 0, 0);  
            }  
            ImageView iv = new ImageView(this);  
            iv.setLayoutParams(pointParams);  
            iv.setBackgroundResource(R.drawable.point_normal);  
            llPoint.addView(iv);  
        }  
        llPoint.getChildAt(0).setBackgroundResource(R.drawable.point_select);  
  
    }  
  
    /**  
     * 判断小圆点  
     *   
     * @param position  
     */  
    private void monitorPoint(int position) {  
        for (int i = 0; i < imageView.length; i++) {  
            if (i == position) {  
                llPoint.getChildAt(position).setBackgroundResource(  
                        R.drawable.point_select);  
            } else {  
                llPoint.getChildAt(i).setBackgroundResource(  
                        R.drawable.point_normal);  
            }  
        }  
        // 3.当滑动到最后一个添加按钮点击进入，  
        if (position == imageView.length - 1) {  
            textView.setVisibility(View.VISIBLE);  
        } else {  
            textView.setVisibility(View.GONE);  
        }  
    }  
}  
