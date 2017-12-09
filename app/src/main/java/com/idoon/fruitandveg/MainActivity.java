package com.idoon.fruitandveg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn,btnSignUp,readme,maps,picture;
    TextView txtSlogan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        readme = (Button) findViewById(R.id.readme);
        maps = (Button) findViewById(R.id.map);
        picture = (Button) findViewById(R.id.picture);


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picture = new Intent(MainActivity.this, com.idoon.fruitandveg.picture.class);
                startActivity(picture);
            }
        });
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
                 Toast.makeText(MainActivity.this,"חנות אינטרנטית למכירת פירות וירקות", Toast.LENGTH_LONG).show();
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent maps = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(maps);
            }
        });
    }
}
