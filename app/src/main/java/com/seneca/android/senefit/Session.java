package com.seneca.android.senefit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dance on 3/4/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin,String email,String password){
        editor.putBoolean("loggedInmode",logggedin);
        editor.putString("email",email);
        editor.putString("password",password);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode",false);
    }

    public String getString(){
        return prefs.getString("email",null);
    }

}