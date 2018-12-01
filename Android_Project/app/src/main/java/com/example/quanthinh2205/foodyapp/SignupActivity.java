package com.example.quanthinh2205.foodyapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {

    EditText edtUsername,edtPassword,edtConfirm,edtBirth,edtEmail,edtPhone;
    Button btnRegister,btnCancel;
    String username,password,confirm,email,phone,birth;
    User user;
    String urlPostUser = "https://thinhnvqfm220697.000webhostapp.com/postuser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setID();
        edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                confirm = edtConfirm.getText().toString();
                birth="";
                if(edtBirth.getText().toString().length()==10){
                    birth = edtBirth.getText().toString();
                }
                email = edtEmail.getText().toString();
                phone = edtPhone.getText().toString();
                if(username.length()>0 && password.length()>0 && confirm.length()>0 && birth.length()>0 && email.length()>0 && phone.length()>0){
                    if(password.compareTo(confirm)==0){
                        addUser(urlPostUser);
                        Confirm();
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Wrong in confirm password !", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignupActivity.this, "You must fill all infos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Confirm(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Congratulation!");
        alertDialog.setIcon(R.drawable.congratulation_icon);
        alertDialog.setMessage("Successful in registering. Click confirm to return Log in");
        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user = new User(username,password,email,birth,phone);
                Intent intent = new Intent();
                intent.putExtra("User",user);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        alertDialog.show();
    }

    private void setID(){
        edtUsername = (EditText)findViewById(R.id.edtRegisterUsername);
        edtPassword = (EditText)findViewById(R.id.edtRegisterPassword);
        edtConfirm = (EditText)findViewById(R.id.edtConfirm);
        edtBirth = (EditText)findViewById(R.id.edtBirth);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnCancel = (Button)findViewById(R.id.btnCancel);
    }

    private void setDate(){
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR)-20;
        DatePickerDialog dateSet = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirth.setText(dateFormat.format(calendar.getTime()));
            }
        },year,month,date);
        dateSet.show();
    }

    private void addUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("Success")){
                    Toast.makeText(SignupActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                params.put("Username",username);
                params.put("Password",password);
                params.put("Email",email);
                params.put("Birth",birth);
                params.put("Phone",phone);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
