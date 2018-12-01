package com.example.quanthinh2205.foodyapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReserveActivity extends AppCompatActivity {

    TextView txtStoreName,txtUsername,txtMaxseat;
    EditText edtName, edtPhone, edtEmail, edtSeat,edtTime,edtDate;
    Button btnSend,btnCancel;
    Shop shop;
    int id;
    String store;
    String username;
    String name,phone,email,seat,time,date;
    String urlSendReserve = "https://thinhnvqfm220697.000webhostapp.com/reserve.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        setID();
        Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra("Shop");
        username = intent.getStringExtra("Username");
        id = shop.getId();
        txtStoreName.setText(shop.getName());
        txtUsername.setText(""+username);
        txtMaxseat.setText("/"+shop.getSeats());
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime();
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetDate();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                store = shop.getName();
                name = edtName.getText().toString();
                phone = edtPhone.getText().toString();
                email = edtEmail.getText().toString();
                seat = edtSeat.getText().toString()+"";
                time = edtTime.getText().toString()+"";
                date = edtDate.getText().toString()+"";
                Send(urlSendReserve);
            }
        });
    }
    private void setID(){
        txtStoreName = (TextView)findViewById(R.id.txtstorename);
        txtUsername = (TextView)findViewById(R.id.txtusername);
        txtMaxseat = (TextView)findViewById(R.id.txtmaxseat);
        edtName = (EditText)findViewById(R.id.edtname);
        edtPhone = (EditText)findViewById(R.id.edtphonenumber);
        edtEmail = (EditText)findViewById(R.id.edtmail);
        edtSeat = (EditText)findViewById(R.id.edtseat);
        edtTime = (EditText)findViewById(R.id.edttime);
        edtDate = (EditText)findViewById(R.id.edtDate);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnCancel = (Button)findViewById(R.id.btnCancelSend);
    }

    private void Confirm(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Congratulation!");
        alertDialog.setIcon(R.drawable.congratulation_icon);
        alertDialog.setMessage("Successful in Reserving, Click Confirm to return");
        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.show();
    }

    private void Send(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(ReserveActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("Success")){
                    Confirm();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("ID",id+"");
                params.put("Store",store);
                params.put("Username",username);
                params.put("Name",name);
                params.put("Phone",phone);
                params.put("Email",email);
                params.put("Seat",seat);
                params.put("Time",time);
                params.put("Date",date);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SetDate()
    {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dateSet = new DatePickerDialog(ReserveActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(dateFormat.format(calendar.getTime()));
            }
        },year,month,date);
        dateSet.show();
    }

    private void SetTime(){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,i,i1);
                edtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
}
