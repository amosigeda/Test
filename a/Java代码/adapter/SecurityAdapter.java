package com.lewic.bracelet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lewic.bracelet.R;
import com.lewic.bracelet.model.SelectWhiteModel;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class SecurityAdapter extends BaseAdapter {
    private Context mContext;
    private List<SelectWhiteModel.Data> codes;

    public SecurityAdapter(Context mContext, List<SelectWhiteModel.Data> codes) {
        this.mContext = mContext;
        this.codes = codes;
    }

    @Override
    public int getCount() {
        return codes.size();
    }

    @Override
    public Object getItem(int i) {
        return codes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("ATG","ddddddd");
        Log.e("ATG","codes:"+codes);
        ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view=View.inflate(mContext, R.layout.list_white,null);
            holder.tv_name= (TextView) view.findViewById(R.id.tv_name);
            holder.tv_phone= (TextView) view.findViewById(R.id.tv_phone);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        SelectWhiteModel.Data code= codes.get(i);
        holder.tv_name.setText(code.name);
        String phone = code.phone;
        String phoneNumber = phone.substring(0,3);
        holder.tv_phone.setText("电话号"+phoneNumber+"********");
        return view;
    }
    class  ViewHolder{
        TextView tv_name;
        TextView tv_phone;
    }
}
