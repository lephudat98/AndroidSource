package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MealStoreActivity extends AppCompatActivity{

    AutoCompleteTextView edtSearch;
    TextView txtCounty;
    Button btnNearby, btnFind;
    int REQUEST_CODE_COUNTY_SELECT = 101;
    int type;
    String urlGetData = "https://thinhnvqfm220697.000webhostapp.com/getdata.php";
    ArrayList<Shop> list;
    ArrayList<String> menuList;
    ListView listView;
    Store_Adapter adapter;
    ArrayAdapter<String> s_adapter;
    String countySelected;
    String username;
    double latitude, longtitude;
    GoogleMap map;
    ArrayList<Double>distances;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_store);
        latitude = -1;
        longtitude = -1;
        final Intent intent = getIntent();
        type = intent.getIntExtra("Type", 0);
        username = intent.getStringExtra("username");
        setID();
        txtCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MealStoreActivity.this, CountyActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_COUNTY_SELECT);
            }
        });
        readJson(urlGetData);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_tmp = edtSearch.getText().toString();
                for (int i = 0; i < list.size(); i++) {
                    if (search_tmp.compareTo(list.get(i).getName()) == 0) {
                        Shop search = list.get(i);
                        Intent intentResult = new Intent(MealStoreActivity.this, StoreInfoActivity.class);
                        intentResult.putExtra("Shop", search);
                        intentResult.putExtra("User", username);
                        startActivity(intentResult);
                    }
                }
            }
        });
        btnNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Input Code Dat here
                ///////////////////////////////
                getCurrentLocation();
                ///////////////////////////////
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shop tmp = list.get(i);
                Intent intent2 = new Intent(MealStoreActivity.this, StoreInfoActivity.class);
                intent2.putExtra("Shop", tmp);
                intent2.putExtra("User", username);
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_COUNTY_SELECT && resultCode == RESULT_OK && data != null) {
            txtCounty.setText(data.getStringExtra("County"));
            countySelected = txtCounty.getText().toString();
            readJson(urlGetData);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readJson(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(MealStoreActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                menuList.clear();
                list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Shop shop = new Shop(object.getInt("ID"), object.getInt("Type"), object.getString("Name"), object.getString("PicIcon"),
                                object.getString("Pic1"), object.getString("Pic2"), object.getString("Street"), object.getString("County"),
                                object.getInt("Likes"), object.getInt("Dislikes"), object.getString("Menu"), object.getInt("Seats"),
                                object.getString("Phone"), object.getString("Email"), object.getString("Username"), object.getString("Password"));
                        if (shop.getType() == type) {
                            if (countySelected.compareTo("County") == 0 || countySelected.compareTo("All") == 0) {
                                list.add(shop);
                                menuList.add(shop.getName());
                                s_adapter.notifyDataSetChanged();
                            } else {
                                if (countySelected.compareTo(shop.getCounty()) == 0) {
                                    list.add(shop);
                                    menuList.add(shop.getName());
                                    s_adapter.notifyDataSetChanged();
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
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

    private void setID() {
        edtSearch = (AutoCompleteTextView) findViewById(R.id.searchMenu);
        txtCounty = (TextView) findViewById(R.id.searchCounty);
        btnNearby = (Button) findViewById(R.id.btnSearch);
        btnFind = (Button) findViewById(R.id.btnFind);
        listView = (ListView) findViewById(R.id.listStores);
        list = new ArrayList<>();
        adapter = new Store_Adapter(MealStoreActivity.this, R.layout.store_rows, list);
        listView.setAdapter(adapter);
        menuList = new ArrayList<>();
        s_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuList);
        edtSearch.setAdapter(s_adapter);
        countySelected = "County";
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "ACCESS_FINE_LOCATION must be permitted", Toast.LENGTH_SHORT).show();
            return;
        }
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                    createDistance();

                    int min_idx;
                    for(int i=0;i<list.size()-1;i++){
                        min_idx = i;
                        for(int j=i+1;j<list.size();j++){
                            if(list.get(min_idx).getDistance()>list.get(j).getDistance()){
                                min_idx = j;
                            }
                        }
                        Collections.swap(list,i,min_idx);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MealStoreActivity.this, "location is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDistance()
    {
        String txtAddresss;
        Geocoder geocoder = new Geocoder(this);
        for(int i=0;i<list.size();i++){
            txtAddresss = list.get(i).getStreet() + ", "+list.get(i).getCounty();
            List<Address> listAddress = new ArrayList<>();
            listAddress.clear();
            try{
                listAddress = geocoder.getFromLocationName(txtAddresss, 1);
            }catch (IOException e){

            }

            if(listAddress.size() > 0){
                Address address = listAddress.get(0);
                double Latitude = address.getLatitude();
                double Longtitude = address.getLongitude();
                double distance = Math.abs(Latitude-latitude)+Math.abs(Longtitude-longtitude);
                list.get(i).setDistance(distance);
            }
        }
    }

    private void sort(){
        Collections.sort(list, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return (int) (s1.getDistance()-s2.getDistance());
            }
        });
    }

    private float getDistances(double x, double y, double cx, double cy){
        float result[] = new float[10];
        Location.distanceBetween(x,y,cx,cy,result);
        return result[0];
    }

}
