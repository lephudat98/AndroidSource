package com.example.quanthinh2205.foodyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class comment_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Comment>list;

    public comment_adapter(Context context, int layout, ArrayList<Comment> list) {
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
        private TextView txtUsername;
        private TextView txtComment;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtUsername = (TextView)view.findViewById(R.id.txtUser);
            holder.txtComment = (TextView)view.findViewById(R.id.txtComment);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        Comment tmp = list.get(i);
        holder.txtUsername.setText(tmp.getUsername());
        holder.txtComment.setText(tmp.getComment());
        return view;
    }
}
