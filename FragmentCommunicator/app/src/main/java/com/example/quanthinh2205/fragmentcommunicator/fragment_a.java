package com.example.quanthinh2205.fragmentcommunicator;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class fragment_a extends Fragment {

//    TextView txtA;
//    EditText edttA;
//    Button buttonA;
    TextView txtSelected;
    ListView listView;
    ArrayList<Info>list;
    item_adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_a,container,false);
        txtSelected = (TextView)view.findViewById(R.id.txtSelected);
        listView = (ListView)view.findViewById(R.id.listView);
        list = new ArrayList<>();
        list.add(new Info("A1_9829",R.drawable.bird7,"Le Thi A","A1",8));
        list.add(new Info("A1_1809",R.drawable.bird9,"Le Thi B", "A1",8));
        list.add(new Info("A2_3509",R.drawable.bird10,"Le Thi C","A2",9));
        list.add(new Info("A2_3100",R.drawable.bird11,"Le Thi D","A2",7));
        list.add(new Info("A1_1120",R.drawable.bird13,"Le Thi E","A1",6));
//        list.add(new Info("A3_4120",R.drawable.bird14));
//        list.add(new Info("A2_8100",R.drawable.bird15));
//        list.add(new Info("A4_1160",R.drawable.bird16));
        adapter = new item_adapter(getActivity(),R.layout.item_rows,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fragment_b fragmentB = (fragment_b)getFragmentManager().findFragmentById(R.id.fragment2);
                fragmentB.changeFragmentB(list.get(i));
            }
        });

//        txtA = (TextView)view.findViewById(R.id.textViewA);
//        edttA = (EditText)view.findViewById(R.id.editTextFragmentA);
//        buttonA = (Button)view.findViewById(R.id.buttonFragmentA);
//        buttonA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView txt = (TextView)getActivity().findViewById(R.id.textViewTheme);
//                txt.setText(edttA.getText().toString());
//            }
//        });
        return view;
    }
//    public void ChangeFragmentA(String str){
//        txtA.setText(str);
//    }
    public Info getElement(int i){
        Info tmp = list.get(i);
        return tmp;
    }
    public Info getLast(){
        Info tmp = list.get(list.size()-1);
        return tmp;
    }

    public int getSize(){
        return list.size();
    }

    public int getpostion(String info){
        int pos = 0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getInfo().compareTo(info)==0){
                pos = i;
                break;
            }
        }
        return pos;
    }
    public void setBackground(int i,boolean isSelected){

        if(isSelected==true){
            listView.getChildAt(i).setBackgroundColor(Color.GRAY);
        }
        else{
            listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
