package com.boss.taxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boss.taxi.adapter.NewAdapter;
import com.boss.taxi.adapter.NewHistoryAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import common.AsyncPostRequest;
import common.Consts;
import common.GlobalClass;
import common.Utils;

public class History extends BaseActivity implements OnClickListener{
	private LinearLayout btnBack;
	private TextView txtMore;
	private TextView pageTitle;
	
	private ListView listview1;
	private List<JSONObject> list=new ArrayList<JSONObject>();
	private NewHistoryAdapter adapter=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		initview();
		initData();
	}
	
	private void initview(){
		txtMore = (TextView) this.findViewById(R.id.txtMore);
		btnBack = (LinearLayout) this.findViewById(R.id.btnBack);
		pageTitle = (TextView) this.findViewById(R.id.pageTitle);
		listview1 = (ListView) this.findViewById(R.id.listview1);
		
		pageTitle.setText("历史纪录");
		txtMore.setVisibility(View.GONE);;
		btnBack.setOnClickListener(this);
	}
	
	private void initData(){
		final HashMap<String , String> map=new HashMap<String, String>();
		map.put("action", "DS_GET_CLIENTER_LIST");
		map.put("ds_sjh", GlobalClass.getLoginUserInfo().getSjh());
		AsyncPostRequest request = new AsyncPostRequest(getThis(), "系统提示",
				"", dtHandler, "tools/dididache.ashx", map);
		request.execute();
		
	}
	
	private Handler dtHandler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == Consts.SuccessCode) {
				JSONArray array = Utils.GetJsonArrayByHeader(msg.obj, "rows");  
				 list = new ArrayList<JSONObject>();
				 for(int i = 0; i < array.length(); i++)
				 {
					 JSONObject obj;
					 try {
						obj = array.getJSONObject(i); 
						list.add(obj);
					 } catch (JSONException e) { 
					} 
				} 
			}
				adapter = new NewHistoryAdapter(getThis(), list);
				listview1.setAdapter(adapter); 
				listview1.setDivider(null);
				adapter.notifyDataSetChanged();
			
				}
			
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnBack:
			finish();
			break;

		default:
			break;
		}
	}

}
