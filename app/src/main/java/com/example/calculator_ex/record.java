package com.example.calculator_ex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class record extends AppCompatActivity {
    Intent intent;
    ListView Listview;
    ArrayList<String> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        intent = getIntent();
        datalist = intent.getStringArrayListExtra("send_data");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_design, datalist);

        Listview = findViewById(R.id.record_List);
        Listview.setAdapter(adapter);
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = (String)parent.getAdapter().getItem(position);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, selected_item);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}