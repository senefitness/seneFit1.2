package com.seneca.android.senefit;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.lang.reflect.Field;


public class exlist extends AppCompatActivity {

    private ListView lv;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private Set<Exercise> exercises;
    private DbHelper db;
    private  int[] prgmImages;


    // URL to get contacts JSON
    private static String url =  "https://wger.de/api/v2/exercise.json/?language=2&ordering=id&ordering=name&status=2&limit=200";
    private static String m_url = "https://wger.de/api/v2/muscle.json/?ordering=name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exlist);

        exercises = new LinkedHashSet<Exercise>();
        db = new DbHelper(this);

        Resources r = getResources();
       // String[] items = r.getStringArray(R.array.exercise_array);
        prgmImages = new int[7]; //changes size when we get more img
        for (int i=0; i <7; i++) {
          String url = "drawable/"+"img"+i;
          prgmImages[i] =  getResources().getIdentifier(url, "drawable", getPackageName());
        }


        lv= (ListView) findViewById(R.id.listView);
        //custAdapter cus = new custAdapter(this,items);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
       // lv.setAdapter(adapter);


        new GetFitt().execute();
        new GetMus().execute();







    }

    /******************************ASyncTasK*********/

    public class GetFitt extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(exlist.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            jsonHandler sh = new jsonHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String aut = c.getString("license_author");
                        String dsc = c.getString("description");
                        String htmlDescRemover = Html.fromHtml(dsc).toString();
                        String name = c.getString("name");
                        String nO = c.getString("name_original");
                        String date = c.getString("creation_date");
                        String cat = c.getString("category");

                        Exercise exercise = new Exercise(name,htmlDescRemover,id,aut,nO,date,cat);
                        db.addExercise(aut,dsc,name,nO,date,cat,id);
                        exercises.add(exercise);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }




            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            //List<String> newCnt = db.getExercise();

            List<Exercise> newItems = new ArrayList<Exercise>();
            newItems.addAll(exercises);
            custAdapter cus = new custAdapter(exlist.this,newItems,prgmImages);
            //ArrayAdapter cus = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,newCnt);
            lv.setAdapter(cus);



        }
    }

    public class GetMus extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... arg0) {
            jsonHandler sh = new jsonHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(m_url);

            Log.e(TAG, "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {

                    db.inserBodyParts(jsonStr);
                } catch (Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }


            return null;
        }
    }


    }

