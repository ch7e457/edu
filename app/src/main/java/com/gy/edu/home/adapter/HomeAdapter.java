package com.gy.edu.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gy.edu.R;
import com.gy.edu.home.bean.HomeBean;

import java.util.List;

/**
 * 作者：gy on 16/12/22
 * 说明：
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<HomeBean> datas;

    public HomeAdapter(Context context, List<HomeBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, R.layout.adapter_home, null);
        TextView title = (TextView) convertView.findViewById(R.id.adapter_home_title);
        TextView name = (TextView) convertView.findViewById(R.id.adapter_home_name);
        TextView number = (TextView) convertView.findViewById(R.id.adapter_home_number);
        TextView classs = (TextView) convertView.findViewById(R.id.adapter_home_class);
        title.setText(datas.get(position).title);
        name.setText(datas.get(position).name);
        number.setText(datas.get(position).number);
        classs.setText(datas.get(position).classs);
        return convertView;
    }
}
