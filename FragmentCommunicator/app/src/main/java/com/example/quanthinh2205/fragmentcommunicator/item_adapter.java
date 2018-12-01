package com.example.quanthinh2205.fragmentcommunicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class item_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Info>list;

    public item_adapter(Context context, int layout, ArrayList<Info> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{
        private ImageView img;
        private TextView txtInfo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.img = (ImageView)view.findViewById(R.id.img);
            holder.txtInfo = (TextView)view.findViewById(R.id.txtInfo);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        Info tmp = list.get(i);
        holder.img.setImageResource(tmp.getImage());
        holder.txtInfo.setText(tmp.getInfo());
        return view;
    }
}
