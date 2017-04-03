package com.seneca.android.senefit;

/**
 * Created by dance on 3/31/2017.
 */

public class tables {


    public tables() {
    }

    public String Profile(){
        String USER_TABLE ="users";
        String COLUMN_ID ="id";
        String COLUMN_EMAIL ="email";
        String COLUMN_PASS ="password";

        String name = "CREATE TABLE " + USER_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT,"
                +COLUMN_PASS + " TEXT);";
        return name;
    }

    public String workOuts(){
        String USER_TABLE ="exercises";
        String COLUMN_ID ="id";
        String DESCRIPTION = "description";
        String NAMETYPE = "name";

        String workoutName = "CREATE TABLE " + USER_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DESCRIPTION + " TEXT,"
                +NAMETYPE + " TEXT);";

        return workoutName;
    }
}
