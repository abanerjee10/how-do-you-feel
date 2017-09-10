package com.example.myfirstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class SelectMedicine extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private class DownloadFilesTask extends AsyncTask<String, Integer, JSONArray> {
        protected JSONArray doInBackground(String... input) {
            /*String medsExampleString = "{\"data\": [{\"small_name\": \"(a)\", \"name\": \"b\", \"id\": \"c\"}]}";
            JSONArray jsonArray = null;
            try {
                JSONObject meds = new JSONObject(medsExampleString);
                jsonArray = meds.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonArray;*/

            URL url = null;
            try {
                url = new URL("https://ghb0cw24ul.execute-api.us-east-1.amazonaws.com/beta/getDrugName?drug=" + input[0]);
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = null;
                in = new BufferedInputStream(urlConnection.getInputStream());
                urlConnection.disconnect();

                JSONObject meds = null;
                meds = new JSONObject(readStream(in));
                JSONArray jsonArray = meds.getJSONArray("results");
                return jsonArray;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONArray result) {
            List<String> allNames = new ArrayList<String>();
            Log.i("something", "onPostExecute: " + result.length());
            for (int i=0; i<result.length(); i++) {
                JSONObject med = null;
                try {
                    med = result.getJSONObject(i);
                    String name = med.getString("name");
                    allNames.add(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            String[] listNames = new String[allNames.size()];
            for(int i = 0; i < allNames.size(); i++){
                listNames[i] = allNames.get(i);
            }
            List<String> allIds = new ArrayList<String>();
            Log.i("something", "onPostExecute: " + result.length());
            for (int i=0; i<result.length(); i++) {
                JSONObject med = null;
                try {
                    med = result.getJSONObject(i);
                    String name = med.getString("id");
                    allIds.add(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            String[] listIds = new String[allIds.size()];
            for(int i = 0; i < allIds.size(); i++){
                listIds[i] = allIds.get(i);
            }
            // specify an adapter (see also next example)
            mAdapter = new MyAdapter(listNames, listIds);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_medicine);

        //get intent that started this activity and extract the string
        Intent intent = getIntent();
        String commonName = intent.getStringExtra(EXTRA_MESSAGE);
        new DownloadFilesTask().execute(commonName);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



    }
}
