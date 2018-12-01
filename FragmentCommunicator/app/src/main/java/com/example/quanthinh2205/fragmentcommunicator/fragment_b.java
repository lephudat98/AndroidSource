package com.example.quanthinh2205.fragmentcommunicator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class fragment_b extends Fragment {
//    TextView txtB;
//    EditText edtB;
//    Button btnB;
    TextView txtSemester,txtName,txtClass,txtScore;
    Button btnFirst,btnPrevious,btnNext,btnLast;
    int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_b,container,false);
        txtSemester = (TextView)view.findViewById(R.id.txtSemester);
        txtName = (TextView)view.findViewById(R.id.txtName);
        txtClass = (TextView)view.findViewById(R.id.txtClass);
        txtScore = (TextView)view.findViewById(R.id.txtScore);
        btnFirst = (Button)view.findViewById(R.id.btnFirst);
        btnPrevious = (Button)view.findViewById(R.id.btnPrevious);
        btnNext = (Button)view.findViewById(R.id.btnNext);
        btnLast = (Button)view.findViewById(R.id.btnLast);
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_a fragmentA = (fragment_a)getFragmentManager().findFragmentById(R.id.fragment);
                changeFragmentB(fragmentA.getElement(0));
                pos = fragmentA.getpostion(txtSemester.getText().toString());
                fragmentA.setBackground(pos,false);
                pos=0;
                fragmentA.setBackground(0,true);
            }
        });
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_a fragmentA = (fragment_a)getFragmentManager().findFragmentById(R.id.fragment);
                changeFragmentB(fragmentA.getLast());
                fragmentA.setBackground(pos,false);
                pos = fragmentA.getSize()-1;
                fragmentA.setBackground(fragmentA.getSize()-1,true);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_a fragmentA = (fragment_a)getFragmentManager().findFragmentById(R.id.fragment);
                int size = fragmentA.getSize()-1;
                fragmentA.setBackground(pos,false);
                pos = fragmentA.getpostion(txtSemester.getText().toString());
                if(pos==size){
                    pos=0;
                }
                else{
                    pos=pos+1;
                }
                changeFragmentB(fragmentA.getElement(pos));
                fragmentA.setBackground(pos,true);
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_a fragmentA = (fragment_a)getFragmentManager().findFragmentById(R.id.fragment);
                int size = fragmentA.getSize()-1;
                fragmentA.setBackground(pos,false);
                pos = fragmentA.getpostion(txtSemester.getText().toString());
                if(pos==0){
                    pos=size;
                }
                else{
                    pos=pos-1;
                }
                fragmentA.setBackground(pos,true);
                changeFragmentB(fragmentA.getElement(pos));

            }
        });
//        txtB = (TextView)view.findViewById(R.id.textViewB);
//        edtB = (EditText)view.findViewById(R.id.editTextFragmentB);
//        btnB = (Button)view.findViewById(R.id.buttonFragmentB);
//        btnB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView txtA = (TextView)getActivity().findViewById(R.id.textViewA);
//                txtA.setText(edtB.getText().toString());
//            }
//        });
        return view;
    }
    public void changeFragmentB(Info info){
        txtSemester.setText(info.getInfo());
        txtName.setText(info.getName());
        txtClass.setText(info.getLop());
        txtScore.setText(info.getDtb()+"");
    }
}
