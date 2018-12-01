package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername,edtPassword;
    Button btnSignup,btnSignin;
    TextView txtGuest;
    int REQUEST_CODE_SIGNUP = 100;
    String username;
    boolean result;
    String urlGetUser = "https://thinhnvqfm220697.000webhostapp.com/getuser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username="";
        setID();
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                readJson(urlGetUser,username,password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SIGNUP);
            }
        });

        txtGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,ListStoreActivity.class);
                intent2.putExtra("IsLog",0);
                startActivity(intent2);
            }
        });
    }

    private void readJson(String url, final String name, final String password){
        result = false;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        User tmp = new User(object.getString("Username"),object.getString("Password"),object.getString("Email"),
                                object.getString("Birth"),object.getString("Phone"));
                        if(name.compareTo(tmp.getUsername())==0 && password.compareTo(tmp.getPassword())==0){
                            Intent intent = new Intent(MainActivity.this,ListStoreActivity.class);
                            intent.putExtra("User",username);
                            intent.putExtra("IsLog",1);
                            intent.putExtra("Customer",tmp);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_SIGNUP && resultCode == RESULT_OK && data!=null){
            User tmp = (User) data.getSerializableExtra("User");
            edtUsername.setText(tmp.getUsername());
            edtPassword.setText(tmp.getPassword());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setID(){
        result = false;
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnSignup = (Button)findViewById(R.id.btnSignup);
        txtGuest = (TextView)findViewById(R.id.txtGuest);
    }

}
