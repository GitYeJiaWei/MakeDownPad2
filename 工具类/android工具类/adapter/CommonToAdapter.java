package com.boss.demo.fragment;

import java.util.List;
import java.util.zip.Inflater;

import javax.sql.DataSource;

import org.json.JSONObject;

import com.boss.demo.adapter.ViewHolder;
import com.sxx001.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public  class CommonToAdapter<T> extends BaseAdapter {
	private List<JSONObject> _objects=null;
	private Context context;
	private BookingRecordFragment _bBookingRecordFragment=null;
	private LayoutInflater inflater;
	
	public  CommonToAdapter(Context context,List<JSONObject> objects,BookingRecordFragment _bookingRecordFragment) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.context=context;
		_objects=objects;
		_bBookingRecordFragment=_bookingRecordFragment;
	}

	//获取到数据的行数
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

	//position表示将显示的是第几行，covertView是从布局文件中inflate来的布局
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		JSONObject model=_objects.get(position);
		View1 view1=null;
		View2 view2=null;
		if (model.optInt("FromBook")==1) {
			view1=new View1();
			convertView=inflater.inflate(R.layout.item_park_record, null);
			view1.tv_cph=(TextView) convertView.findViewById(R.id.tv_cph);
			view1.tv_parkingName=(TextView) convertView.findViewById(R.id.tv_parkingName);
			view1.tv_parkingPosition=(TextView) convertView.findViewById(R.id.tv_parkingPosition);
			view1.tv_state=(TextView) convertView.findViewById(R.id.tv_state);
			
			view1.tv_cph.setText(model.optString("Cph"));
			view1.tv_parkingName.setText(model.optString("P_Name"));
//			view1.tv_parkingPosition.setText(model.optString("P_Spxh"));
			view1.tv_state.setText(model.optString("ZtStr"));
			
		}else{
			view2=new View2();
			convertView=inflater.inflate(R.layout.item_book_record, null);
			view2.tv_cph=(TextView) convertView.findViewById(R.id.tv_cph);
			view2.tv_parkingName=(TextView) convertView.findViewById(R.id.tv_parkingName);
			view2.tv_state=(TextView) convertView.findViewById(R.id.tv_state);
			
			view2.tv_cph.setText(model.optString("Cph"));
			view2.tv_parkingName.setText(model.optString("P_Name"));
			view2.tv_state.setText(model.optString("ZtStr"));
		}
		return convertView;
	}

	public class ViewHolder{
	}

	class View1 extends ViewHolder {
		TextView tv_cph,tv_parkingName,tv_parkingPosition,tv_state;
	}

	class View2 extends ViewHolder {
		TextView tv_cph, tv_parkingName,tv_state;

}

}
