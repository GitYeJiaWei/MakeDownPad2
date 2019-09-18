package com.boss.demo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.boss.demo.activity.NewBaseActivity;
import com.boss.demo.activity.ParkingBookDetailActivity;
import com.boss.demo.activity.ParkingRecordDetailActivity;
import com.boss.demo.adapter.CommonAdapter;
import com.boss.demo.adapter.NewParkingAdapter;
import com.boss.demo.adapter.ViewHolder;
import com.boss.demo.callback.OkHttpCallBack;
import com.boss.demo.utils.OKHttpUtils;
import com.boss.demo.utils.SysConstants;
import com.boss.demo.utils.ToastUtils;
import com.boss.demo.utils.Utils;
import com.boss.demo.view.xListView.XListView;
import com.google.gson.JsonArray;
import com.sxx001.R;

public class BookingRecordFragment extends BaseFragment implements
		XListView.IXListViewListener {
	private static final int CODE_START = 99;
	private static BookingRecordFragment parkingRecordFragment;
	private XListView listView;

	private CommonToAdapter<JSONObject> adapter;
	private List<JSONObject> objects = new ArrayList<JSONObject>();

	private int PageNum = 1;

	public static BookingRecordFragment getBookingRecordFragment() {
		if (parkingRecordFragment == null) {
			parkingRecordFragment = new BookingRecordFragment();
		}
		return parkingRecordFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.xlistview, container, false);
		initView(rootView);
		
		return rootView;
	}

	private void initView(View rootView) {
		// TODO Auto-generated method stub
		listView = (XListView) rootView.findViewById(R.id.listview);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
		adapter = new CommonToAdapter<JSONObject>(getContext(), objects, this);
		listView.setAdapter(adapter);
		listView.startPullRefresh();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						ParkingBookDetailActivity.class);
				intent.putExtra("data", objects.get(position - 1).toString());
				startActivityForResult(intent, CODE_START);
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE_START && resultCode == Activity.RESULT_OK) {
			listView.startPullRefresh();
		}
	}


	private void loadData(int page) {
		final HashMap<String, String> map = new HashMap<String, String>();
		map.put("c_sjh", Utils.ReadSharePreference(getActivity(),
				SysConstants.UserKey, SysConstants.UserNameKey));
		map.put("PageNum", page + "");

		OKHttpUtils.get(SysConstants.HTTP_GET_MY_BOOKING_LIST, map,
				new OkHttpCallBack(getActivity()) {

					@Override
					public void onSuccess(Call arg0, JSONObject object) {
						int resultCode = object.optInt("ResultCode", 0);
						if (resultCode == 1) {
							if (listView.getLoadingState() == 1) {
								PageNum = 1;
								objects.clear();
							} else if (listView.getLoadingState() == 2) {
								PageNum++;
							}

							JSONArray array = object.optJSONArray("DataList");
							//“FromBook”:1（1表示个人停车位预约，0表示停车场车位预约）
							//“base_parktemp_id”:1  在库车辆停车ID
							for (int i = 0; i < array.length(); i++) {
								objects.add(array.optJSONObject(i));
								if (array.length() < 20) {
									listView.setPullLoadEnable(false);
								}else {
									listView.setPullLoadEnable(true);
								}
								
							} 
							adapter = new CommonToAdapter<JSONObject>(getActivity(), objects,BookingRecordFragment.this);
							listView.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}else {
							if (object.optString("Message") != null) {
								ToastUtils.toast(getActivity(),
										object.optString("Message"));
							} else {
								ToastUtils.toast(getActivity(), "服务器错误,请联系管理员");
							}

						}
						listView.stopRefresh();
						listView.stopLoadMore();
					}

					@Override
					public void onFailure(int errorType) {
						listView.stopRefresh();
						listView.stopLoadMore();
					}
				});
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		loadData(1);
		// listView.stopRefresh();
		// listView.stopLoadMore();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		loadData(PageNum + 1);
		// listView.stopRefresh();
		// listView.stopLoadMore();
	}

}
