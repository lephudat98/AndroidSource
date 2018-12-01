package com.example.quanthinh2205.foodyapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Meal_Adapter extends BaseAdapter{
    private Context context;
    private int layout;
    private ArrayList<meal>meals;

    public Meal_Adapter(Context context, int layout, ArrayList<meal> meals) {
        this.context = context;
        this.layout = layout;
        this.meals = meals;
    }

    @Override
    public int getCount() {
        return meals.size();
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
        private ImageView imgMeals;
        private TextView txtMeal;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.meal_rows,null);
            holder.imgMeals = (ImageView)view.findViewById(R.id.imgMeal);
            holder.txtMeal = (TextView)view.findViewById(R.id.txtMeal);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }
        meal tmp = meals.get(i);
        holder.txtMeal.setText(tmp.getMeal());
        holder.imgMeals.setImageResource(tmp.getImage());
        return view;
    }
}
