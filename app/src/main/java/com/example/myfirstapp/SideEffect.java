package com.example.myfirstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.myfirstapp.MainActivity.EXTRA_MESSAGE;

public class SideEffect extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_effect);

        //get intent that started this activity and extract the string
        Intent intent = getIntent();
        String[] seInfo = intent.getStringArrayExtra(EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.seNameLabel);
        textView.setText(seInfo[0].substring(0, 1).toUpperCase() + seInfo[0].substring(1));
        textView = (TextView) findViewById(R.id.seOnsetLabel);
        textView.setText("This side effect has an " + seInfo[1].toLowerCase() + " onset.");
        textView = (TextView) findViewById(R.id.seSeverityLabel);
        textView.setText("This side effect is expected to be " + seInfo[2].toLowerCase() + " in its nature.");
    }
}
