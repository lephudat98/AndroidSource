package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StoreInfoActivity extends AppCompatActivity {
    ImageView picStore1,picStore2;
    TextView txtName,txtStatus,txtAddress,txtMenu;
    ImageView imgCal,imgFind,imgReserve;
    Button btnLikes,btnDislikes;
    TextView txtLikes,txtDislikes;
    Button btnComment;
    Shop shop;
    String username;
    int likes,dislikes;
    boolean isLog;
    int id;
    boolean isLike;
    boolean isDislike;
    String urlUpdateData = "https://thinhnvqfm220697.000webhostapp.com/updatedata.php";
    String urlPostLike = "https://thinhnvqfm220697.000webhostapp.com/postlike.php";
    String urlUnpostLike = "https://thinhnvqfm220697.000webhostapp.com/unpostlike.php";
    String urlGetLike = "https://thinhnvqfm220697.000webhostapp.com/getlike.php";
    String urlPostDislike = "https://thinhnvqfm220697.000webhostapp.com/postdislike.php";
    String urlUnpostDislike = "https://thinhnvqfm220697.000webhostapp.com/unpostdislike.php";
    String urlGetDislike = "https://thinhnvqfm220697.000webhostapp.com/getdislike.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        setID();
        final Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra("Shop");
        username = intent.getStringExtra("User");
        id = shop.getId();
        likes = shop.getLikes();
        dislikes = shop.getDislikes();
        readJson(urlGetLike,1);
        readJson(urlGetDislike,2);
        Picasso.get().load(shop.getPic1()).into(picStore1);
        Picasso.get().load(shop.getPic2()).into(picStore2);
        txtName.setText(shop.getName());
        txtAddress.setText(shop.getStreet()+", "+shop.getCounty());
        txtMenu.setText(shop.getMenu());
        imgCal.setImageResource(R.drawable.android_call);
        imgFind.setImageResource(R.drawable.search);
        imgReserve.setImageResource(R.drawable.booking);
        if(username.compareTo("Guest")!=0){
            isLog = true;
        }
        imgCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = shop.getPhone();
                Intent intentCal = new Intent();
                intentCal.setAction(Intent.ACTION_CALL);
                String call = "tel:"+phone;
                intentCal.setData(Uri.parse(call));
                startActivity(intentCal);
            }
        });
        imgFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Input Code Dat here
                /////////////////////////////////////
                Intent intenFind = new Intent(StoreInfoActivity.this,MapActivity.class);
                intenFind.putExtra("Shop",shop);
                startActivity(intenFind);


                /////////////////////////////////////
            }
        });

        imgReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLog){
                    Intent intentReserve = new Intent(StoreInfoActivity.this,ReserveActivity.class);
                    intentReserve.putExtra("Shop",shop);
                    intentReserve.putExtra("Username",username);
                    startActivity(intentReserve);
                }
                else{
                    Toast.makeText(StoreInfoActivity.this, "You must login to use this function", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLog){
                    if(isLike==true){
                        isLike = false;
                        likes = likes-1;
                        post(urlUnpostLike);
                        btnLikes.setText("Likes");
                    }
                    else{
                        isLike = true;
                        likes = likes+1;
                        post(urlPostLike);
                        btnLikes.setText("Unlike");
                    }
                    update(urlUpdateData);

                }
                else{
                    Toast.makeText(StoreInfoActivity.this, "You must Log in to like", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtLikes.setText(likes+"");
        btnDislikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLog){
                    if(isDislike==true){
                        isDislike = false;
                        dislikes = dislikes-1;
                        post(urlUnpostDislike);
                        btnDislikes.setText("Dislikes");
                    }
                    else{
                        isDislike = true;
                        dislikes = dislikes+1;
                        post(urlPostDislike);
                        btnDislikes.setText("UnDislike");
                    }
                    update(urlUpdateData);

                }
                else{
                    Toast.makeText(StoreInfoActivity.this, "You must Log in to dislike", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtDislikes.setText(dislikes+"");
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StoreInfoActivity.this,CommentActivity.class);
                intent2.putExtra("ID",shop.getId());
                intent2.putExtra("username",username);
                startActivity(intent2);
            }
        });
        txtStatus.setText("Trust");
    }
    private void setID(){
        picStore1 = (ImageView)findViewById(R.id.picStore1);
        picStore2 = (ImageView)findViewById(R.id.picStore2);
        txtName = (TextView)findViewById(R.id.txtStore);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtMenu = (TextView)findViewById(R.id.txtMenu);
        imgCal = (ImageView)findViewById(R.id.imgCal);
        imgFind = (ImageView)findViewById(R.id.imgFind);
        imgReserve = (ImageView)findViewById(R.id.imgReserve);
        btnLikes = (Button)findViewById(R.id.btnLikes);
        btnDislikes = (Button)findViewById(R.id.btnDislikes);
        txtLikes = (TextView)findViewById(R.id.txtLikes);
        txtDislikes = (TextView)findViewById(R.id.txtDislikes);
        btnComment = (Button)findViewById(R.id.btnShowComments);
        isLog = false;
        isLike = false;
        isDislike = false;
    }

    private void readJson(String url, final int type){
        RequestQueue requestQueue = Volley.newRequestQueue(StoreInfoActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String tmp_user = object.getString("Username");
                        int tmp_id = object.getInt("ID");
                        if(tmp_user.compareTo(username)==0 && tmp_id==id){
                            if(type == 1){
                                isLike = true;
                                btnLikes.setText("Unlike");
                            }
                            else{
                                isDislike = true;
                                btnDislikes.setText("Undislike");
                            }
                            break;
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

    private void update(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(StoreInfoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("Success")){
                    Toast.makeText(StoreInfoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    txtLikes.setText(likes+"");
                    txtDislikes.setText(dislikes+"");
                }
                else{
                    Toast.makeText(StoreInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                params.put("Likes",likes+"");
                params.put("Dislikes",dislikes+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void post(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(StoreInfoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("Success")){
                    Toast.makeText(StoreInfoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(StoreInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                params.put("ID",id+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
