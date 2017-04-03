package com.seneca.android.senefit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Session session;
    private DbHelper db;
    Button btnLogout,viewList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      session = new Session(this);
        db = new DbHelper(this);




        if(!session.loggedin()){
            logout();
        }
         /*************Shared Collector*/

        String email = session.getString();
        if(email != null){
            textView = (TextView)findViewById(R.id.tvEmail);
            textView.setText("You Are Logged In As "+email);
        }

        btnLogout =(Button)findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        viewList =(Button) findViewById(R.id.allworkouts);
        viewList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,exlist.class));
            }
        });

    }

    public void logout(){
        session.setLoggedin(false,null,null);
        startActivity(new Intent(MainActivity.this,Login.class));
    }


}
