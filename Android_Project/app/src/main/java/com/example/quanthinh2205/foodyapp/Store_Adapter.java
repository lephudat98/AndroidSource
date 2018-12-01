package com.example.quanthinh2205.foodyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Store_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Shop>listShops;

    public Store_Adapter(Context context, int layout, ArrayList<Shop> listShops) {
        this.context = context;
        this.layout = layout;
        this.listShops = listShops;
    }

    @Override
    public int getCount() {
        return listShops.size();
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
        private ImageView imgStore;
        private TextView nameStore;
        private TextView menu;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgStore = (ImageView)view.findViewById(R.id.imgStore);
            holder.nameStore = (TextView)view.findViewById(R.id.txtName);
            holder.menu = (TextView)view.findViewById(R.id.txtMenu);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        Shop shop = listShops.get(i);
        holder.nameStore.setText(shop.getName());
        holder.menu.setText(shop.getMenu());
        Picasso.get().load(shop.getPicIcon()).into(holder.imgStore);
        return view;
    }
}
