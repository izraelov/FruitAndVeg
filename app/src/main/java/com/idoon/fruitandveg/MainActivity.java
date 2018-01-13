package com.idoon.fruitandveg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn,btnSignUp,maps;
    ImageButton readme;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        readme = (ImageButton) findViewById(R.id.readme);
        maps = (Button) findViewById(R.id.map);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //move to login page
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent signIn = new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);
            }
        });
        //move to register page
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent signUp = new Intent(MainActivity.this,SignUp.class);
                startActivity(signUp);
            }
        });

        readme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "ReadMe");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "ReadMe_Btn");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
                 Toast.makeText(MainActivity.this,"חנות אינטרנטית למכירת פירות וירקות", Toast.LENGTH_LONG).show();
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Maps");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Maps_Activity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
                Intent maps = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(maps);
            }
        });
    }

}
