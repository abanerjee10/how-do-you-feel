package com.example.myfirstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.myfirstapp.MainActivity.EXTRA_MESSAGE;

public class SelectSideEffect extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private class DownloadFilesTask extends AsyncTask<String, Integer, JSONArray> {
        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
        protected JSONArray doInBackground(String... input) {
            String sideEffectExampleString = "{\"data\": [{\"name\": \"(a)\", \"description\": \"b\", \"severity\": \"c\", \"onset\": \"d\", \"withdrawl\": \"e\"}]}";
            /*JSONArray jsonArray = null;
            try {
                JSONObject meds = new JSONObject(sideEffectExampleString);
                jsonArray = meds.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonArray;*/
            URL url = null;
            try {
                url = new URL("https://ghb0cw24ul.execute-api.us-east-1.amazonaws.com/beta/getAdverseEffects?drug=" + input[0]);
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = null;
                in = new BufferedInputStream(urlConnection.getInputStream());
                urlConnection.disconnect();

                JSONObject sideEffects = null;
                sideEffects = new JSONObject(readStream(in));
                JSONArray jsonArray = sideEffects.getJSONArray("results");
                return jsonArray;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONArray result) {
            List<String> allEffects = new ArrayList<String>();
            List<String> allOnset = new ArrayList<String>();
            List<String> allSeverity = new ArrayList<String>();
            for (int i=0; i<result.length(); i++) {
                JSONObject med = null;
                try {
                    med = result.getJSONObject(i);
                    String effect = med.getString("effect");
                    allEffects.add(effect);
                    String onset = med.getString("onset");
                    allOnset.add(onset);
                    String severity = med.getString("severity");
                    allSeverity.add(severity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            String[] listEffect = new String[allEffects.size()];
            String[] listOnset = new String[allOnset.size()];
            String[] listSeverity = new String[allSeverity.size()];
            for(int i = 0; i < allEffects.size(); i++){
                listEffect[i] = allEffects.get(i);
                listOnset[i] = allOnset.get(i);
                listSeverity[i] = allSeverity.get(i);
            }

            // specify an adapter (see also next example)
            mAdapter = new MyAdapter2(listEffect,listOnset,listSeverity);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_side_effect);

        //get intent that started this activity and extract the string
        Intent intent = getIntent();
        String chosenMed = intent.getStringExtra(EXTRA_MESSAGE);
        new DownloadFilesTask().execute(chosenMed);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}