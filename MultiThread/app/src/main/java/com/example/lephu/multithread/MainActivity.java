package com.example.lephu.multithread;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private EditText editText;
    private Button btn;


    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textViewPercent);
        editText = (EditText)findViewById(R.id.editTextNumber);
        btn = (Button)findViewById(R.id.buttonAgain);

        progressBar.setMax(100);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.INVISIBLE);
                final int value =Integer.parseInt(editText.getText().toString());

                progressStatus=0;
                progressBar.setProgress(0);
                textView.setText("0%");

                new Thread(new Runnable() {
                  @Override
                  public void run() {
                      int counter = 0;
                      int range;
                      if(value>100) {
                          range = value/100;
                          while (counter < value) {

                              Log.d("DEBUGG", "" + progressStatus * range);
                              counter++;
                              if (counter > progressStatus * range) {

                                  Log.d("DEBUGG", "process bar status: " + progressStatus);
                                  progressStatus++;
                                  handler.post(new Runnable() {
                                      @Override
                                      public void run() {

                                          if (progressStatus >= 100) {
                                              progressStatus=100;
                                              btn.setVisibility(View.VISIBLE);
                                          }
                                          progressBar.setProgress(progressStatus);
                                          textView.setText("" + progressStatus + "%");
                                      }
                                  });
                              }
                          }
                      }
                      else
                      {

                          range = 100/value;
                          while (counter < value) {
                              counter++;
                              progressStatus = counter * range;
                              Log.d("DEBUGG", "process bar status: " + progressStatus);

                              handler.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (progressStatus >=100/value) {
                                          btn.setVisibility(View.VISIBLE);
                                          progressStatus=100;
                                      }
                                      progressBar.setProgress(progressStatus);
                                      textView.setText("" + progressStatus + "%");
                                  }
                              });
                          }
                      }
                  }
              }).start();

            }
        });


    }
}
