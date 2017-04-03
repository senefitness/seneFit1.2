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
  ;


    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";



    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            +COLUMN_PASS + " TEXT);";



    //------------------------//
    private static final String EXERCISE_TABLE ="exercises";
    private static final String DESCRIPTION = "description";
    private static final String NAMETYPE = "name";
    private static final String EXERCISE_ID = "id";

    public String CREATE_TABLE_EXERCISE = "CREATE TABLE " + EXERCISE_TABLE + "("
            + EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAMETYPE + " TEXT,"
            +DESCRIPTION + " TEXT);";
    //----------------------------------//


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EXERCISE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS" + EXERCISE_TABLE);

        onCreate(db);
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






}
