package com.example.quanthinh2205.fragmentcommunicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragment_a fragment_A = (fragment_a) getFragmentManager().findFragmentById(R.id.fragment_a);
//                fragment_A.ChangeFragmentA("Change from MainActivity");
//            }
//        });
    }
}
