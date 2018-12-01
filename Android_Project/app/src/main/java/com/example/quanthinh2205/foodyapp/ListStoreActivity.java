package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListStoreActivity extends AppCompatActivity {
    public static String API_KEY = "AIzaSyD8Ujp4Fr7kyvzIKE0pgxX0dYoHTOIISo8";
    String ID_PLAYLIST = "PL_PyYkzicJWM63iJz5r9Q2j9t-mZAj5nl";
    String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ ID_PLAYLIST +"&key="+ API_KEY +"&maxResults=50";
    ArrayList<VideoYoutube>list;
    ImageView imgVideo;
    VideoYoutube video;
    int position;
    ListView listview;
    Meal_Adapter adapter;
    ArrayList<meal>meals;
    TextView txtPrevious,txtNext;
    String username;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_store);
        Intent intentUser = getIntent();
        int isLog = intentUser.getIntExtra("IsLog",0);
        if(isLog!=0){
            username = intentUser.getStringExtra("User");
        }
        else{
            username = "Guest";
        }
        position=0;
        size=0;
        listview = (ListView)findViewById(R.id.listview);
        txtPrevious = (TextView)findViewById(R.id.txtPrevious);
        txtNext = (TextView)findViewById(R.id.txtNext);
        meals = new ArrayList<>();
        meals.add(new meal(R.drawable.breakfast,"Breakfast"));
        meals.add(new meal(R.drawable.school_lunch_title,"Lunch"));
        meals.add(new meal(R.drawable.dinner,"Dinner"));
        meals.add(new meal(R.drawable.dessert,"Dessert"));
        adapter = new Meal_Adapter(ListStoreActivity.this,R.layout.meal_rows,meals);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int type = i+1;
                Intent intent = new Intent(ListStoreActivity.this,MealStoreActivity.class);
                intent.putExtra("Type",type);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        list = new ArrayList<>();
        imgVideo = (ImageView)findViewById(R.id.imgVideo);
        getJsonYoutube(urlGetJson);
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==size){
                    position = 0;
                }
                else{
                    position = position+1;
                }
                getJsonYoutube(urlGetJson);
            }
        });
        txtPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0){
                    position = size;
                }
                else{
                    position = position-1;
                }
                getJsonYoutube(urlGetJson);
            }
        });
    }

    private void getJsonYoutube(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(ListStoreActivity.this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    String title="", url="", videoID="";
                    list.clear();
                    size = jsonItems.length()-1;
                    for(int i=0;i<jsonItems.length();i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        title = jsonSnippet.getString("title");
                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                        url = jsonMedium.getString("url");
                        JSONObject jsonResourceID = jsonSnippet.getJSONObject("resourceId");
                        videoID = jsonResourceID.getString("videoId");
                        list.add(new VideoYoutube(title,url,videoID));
                        if(i==position){
                            video = list.get(i);
                            Picasso.get().load(video.getThumbnail()).into(imgVideo);
                            imgVideo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ListStoreActivity.this,VideoActivity.class);
                                    intent.putExtra("IDVideoSelected",video.getIdVideo());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListStoreActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(objectRequest);
    }
}
