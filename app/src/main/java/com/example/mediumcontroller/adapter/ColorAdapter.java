package com.example.mediumcontroller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mediumcontroller.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by looper on 2020/10/8.
 */
public class ColorAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;

    public ColorAdapter(Context pContext, List<String> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.color_item, null);
        if(convertView!=null) {
            TextView bg = convertView.findViewById(R.id.ll_bg);
            bg.setBackgroundColor(Color.parseColor(mList.get(position)));
        }
        return convertView;
    }
}
