package com.boss.taxi.adapter;

import java.util.List;
import org.json.JSONObject;
import com.boss.taxi.R;
import com.boss.taxi.adapter.NewAdapter.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewHistoryAdapter extends BaseAdapter{
	private List<JSONObject> _objects=null;
	private Context context;
	private LayoutInflater inflater;
	
	public NewHistoryAdapter(Context context,List<JSONObject> objects) {
		// TODO Auto-generated constructor stub
		
		inflater = LayoutInflater.from(context);
		this.context=context;
		_objects=objects;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (_objects == null) {
			return 0;
		}else {
			return _objects.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		JSONObject model=_objects.get(position);
		ViewHolder view1=null;
		if (convertView== null) {
			view1=new ViewHolder();
			convertView=inflater.inflate(R.layout.history, null);
			view1.shangjia =(TextView) convertView.findViewById(R.id.shangjia);
			view1.shangjianame = (TextView) convertView.findViewById(R.id.shangjianame);
			view1.shangjiadizhi = (TextView) convertView.findViewById(R.id.shangjiadizhi);
			view1.songkeshijian = (TextView) convertView.findViewById(R.id.songkeshijian);
			view1.yidu = (TextView) convertView.findViewById(R.id.yidu);
			view1.yifa = (TextView) convertView.findViewById(R.id.yifa);
			convertView.setTag(view1);
		}else{
			view1 = (ViewHolder)convertView.getTag();
		}
		if(model != null)
		{ 
			view1.shangjia.setText(model.optString("shop_sjh"));
			view1.shangjianame.setText(model.optString("shopname")); 
			view1.shangjiadizhi.setText(model.optString("dwmc"));  
			view1.songkeshijian.setText(model.optString("addtime"));
			if (model.optString("isread").equals("1")) {
				view1.yidu.setText("已读");
			}else{
				view1.yidu.setText("未读");
			}
			if (model.optString("issend").equals("1")) {
				view1.yifa.setText("已发");
			}else{
				view1.yifa.setText("未发");
			}
	}
		return convertView;
	}
	
	public class ViewHolder{
		TextView shangjia,shangjianame,shangjiadizhi,songkeshijian,yidu,yifa;
	}
}
