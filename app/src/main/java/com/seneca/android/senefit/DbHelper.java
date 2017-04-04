package com.seneca.android.senefit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dance on 3/4/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private ArrayList<Integer> m_ids = new ArrayList<Integer>();
    private ArrayList<String> m_names = new ArrayList<String>();
    private SQLiteDatabase database;

    // Databse info
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    //// Table Names
    private static final String USER_TABLE = "users";
    private static final String EXERCISE_TABLE = "exercises";

    // user Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";

    //exercise Table Columns
    private static final String AUTHOR = "author";
    private static final String DESCRIPTION = "description";
    private static final String NAMETYPE = "name";
    private static final String ORIGNALNAME = "originalname";
    private static final String CREATIONDATE = "creationdate";
    private static final String CATEGORY = "category";
    private static final String EXERCISE_ID = "id";





    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT,"
                +COLUMN_PASS + " TEXT);";

        String CREATE_TABLE_EXERCISE = "CREATE TABLE " + EXERCISE_TABLE + "("
                + EXERCISE_ID + " INTEGER PRIMARY KEY,"
                + AUTHOR + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + NAMETYPE + " TEXT,"
                + ORIGNALNAME + " TEXT,"
                + CREATIONDATE + " TEXT,"
                +CATEGORY + " TEXT);";

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL("CREATE TABLE muscles (_id INTEGER PRIMARY KEY, name TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS" + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS" + EXERCISE_TABLE);
        db.execSQL("CREATE TABLE muscles (_id INTEGER PRIMARY KEY, name TEXT);");
        onCreate(db);*/
    }

    public void addUsers(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(USER_TABLE, null, values);
        db.close();
    }

    public boolean getUser(String email, String pass) {
        String selectQuery = "select * from " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'" + email + "'" + " and " + COLUMN_PASS + " = " + "'" + pass + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean checkEmailExists(String email) {
        String query = "Select * from " + USER_TABLE + " where email like '" + email + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //ADD Exercise
    public void addExercise(String aut,String desc,String ntype,String ontype,String cdate,String catt,String idd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXERCISE_ID,idd);
        values.put(AUTHOR,aut);
        values.put(DESCRIPTION,desc);
        values.put(NAMETYPE,ntype);
        values.put(ORIGNALNAME,ontype);
        values.put(CREATIONDATE,cdate);
        values.put(CATEGORY,catt);


        long info = db.insert(EXERCISE_TABLE,null,values);

    }
    //GET Exercises
    public List getExercise(){
        List<String> results = new ArrayList<>();

        String Query = "SELECT * FROM " + EXERCISE_TABLE;
        Log.d("GETEX", Query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        cursor.moveToFirst();
        Log.d("Events",Integer.toString(cursor.getCount()));


           if(cursor.moveToFirst()) {
                do {

                    results.add(cursor.getString(0));
                    results.add(cursor.getString(1));
                    results.add(cursor.getString(2));
                    results.add(cursor.getString(3));
                    results.add(cursor.getString(4));
                    results.add(cursor.getString(5));
                    results.add(cursor.getString(6));
                }while(cursor.moveToNext());
            };
        return results;
    }

   public void inserBodyParts(String jstring){
       try {
           JSONObject jsonObj = new JSONObject(jstring);

           // Getting JSON Array node
           JSONArray contacts = jsonObj.getJSONArray("results");

           // looping through All Contacts
           for (int i = 0; i < contacts.length(); i++) {
               JSONObject c = contacts.getJSONObject(i);
               m_ids.add(c.getInt("id"));
               m_names.add(c.getString("name"));
;
           }
       } catch (final JSONException e) {
           Log.e("TAG", "Json parsing error: " + e.getMessage());
       }

       Integer[] idArray = new Integer[m_ids.size()];
       idArray = m_ids.toArray(idArray);

       String[] nameArray = new String[m_names.size()];
       nameArray = m_names.toArray(nameArray);

       this.database = this.getWritableDatabase();
       for(int i = 0; i < m_ids.size(); i++) {
           this.database.execSQL("INSERT INTO muscles VALUES(" + idArray[i] + ", '" + nameArray[i] + "');");
       }

       this.database.close();
   }

   public ArrayList<Muscle> getBodyParts(){
       Log.d("SELECT DB", "Selecting muscles....");
       Cursor c = getReadableDatabase().rawQuery("SELECT * FROM muscles ORDER BY name COLLATE NOCASE", null);

       if(c == null)
           Log.d("ex db", "NOTHING");

       if(c.getCount() == 0) {
           Log.d("NOTHING", "0 nothing");
       }
       ArrayList<Muscle> temp = new ArrayList<Muscle>();

       c.moveToFirst();
       do {
           Muscle newItems = new Muscle();
           newItems.setId(c.getInt(c.getColumnIndex("_id")));
           newItems.setName(c.getString(c.getColumnIndex("name")));

       }while (c.moveToNext());

       c.close();
       getReadableDatabase().close();
       return temp;
   }





}
