package com.seneca.android.senefit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class musclelist extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private List<Muscle> muscles;
    private ListView listView;
    private DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musclelist);

        muscles = new ArrayList<Muscle>();
        listView = (ListView) findViewById(R.id.mlistView);

       //muscles = db.getBodyParts();





    }



}
