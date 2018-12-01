package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CountyActivity extends AppCompatActivity {
    ListView listCounty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        listCounty = (ListView)findViewById(R.id.listCounty);
        final ArrayList<String>counties = new ArrayList<>();
        counties.add("All");
        counties.add("Quận 1");
        counties.add("Quận 2");
        counties.add("Quận 3");
        counties.add("Quận 4");
        counties.add("Quận 5");
        counties.add("Quận 6");
        counties.add("Quận 7");
        counties.add("Quận 8");
        counties.add("Quận 9");
        counties.add("Quận 10");
        counties.add("Quận 11");
        counties.add("Quận 12");
        counties.add("Tân Bình");
        counties.add("Bình Thạnh");
        counties.add("Bình Tân");
        counties.add("Thủ Đức");
        ArrayAdapter adapter = new ArrayAdapter(CountyActivity.this,android.R.layout.simple_list_item_1,counties);
        listCounty.setAdapter(adapter);
        listCounty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("County",counties.get(i));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
