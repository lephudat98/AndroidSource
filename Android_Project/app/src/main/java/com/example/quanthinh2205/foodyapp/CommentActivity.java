package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    EditText edtComment;
    Button btnSubmit;
    ListView listView;
    ArrayList<Comment> list;
    comment_adapter adapter;
    int id;
    String username;
    boolean isLog;
    String urlGetComment = "https://thinhnvqfm220697.000webhostapp.com/getcomment.php";
    String urlPostComment = "https://thinhnvqfm220697.000webhostapp.com/postcomment.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        SetID();
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",0);
        username = intent.getStringExtra("username");
        if(username.compareTo("Guest")!=0){
            isLog = true;
        }
        readJson(urlGetComment);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLog==true){
                    addComment(urlPostComment);
                }
                else{
                    Toast.makeText(CommentActivity.this, "You must Login to Comment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void SetID(){
        edtComment = (EditText)findViewById(R.id.edtComment);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        listView = (ListView)findViewById(R.id.listComment);
        list = new ArrayList<>();
        adapter = new comment_adapter(CommentActivity.this,R.layout.comment_rows,list);
        listView.setAdapter(adapter);
        isLog = false;
    }

    private void readJson(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(CommentActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                list.clear();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Comment tmp = new Comment(object.getInt("ID"),object.getString("Username"),object.getString("Comment"));
                        if(tmp.getId() == id){
                            list.add(tmp);
                            adapter.notifyDataSetChanged();
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

    private void addComment(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(CommentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("Success")){
                    Toast.makeText(CommentActivity.this, "Comment has sent", Toast.LENGTH_SHORT).show();
                    readJson(urlGetComment);
                }
                else{
                    Toast.makeText(CommentActivity.this, "Error in sending comment", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String>params = new HashMap<>();
                params.put("ID",id+"");
                params.put("Username",username);
                params.put("Comment",edtComment.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
