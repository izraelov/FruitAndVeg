package com.idoon.fruitandveg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idoon.fruitandveg.Common.Common;
import com.idoon.fruitandveg.Model.User;

import io.paperdb.Paper;

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

        //Init Papper
        Paper.init(this);


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
                bundle.putInt("Maps",view.getId());
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Maps");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Maps_Activity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
                Intent maps = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(maps);
            }
        });

        //Check remeber
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                login(user,pwd);
        }
    }


    private void login(final String phone, final String pwd) {
        //Login Code from SignIn:
        //Init FireBase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");



        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("אנא המתן...");
        mDialog.show();

        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Check if user not exist in database
                if(dataSnapshot.child(phone).exists()) {
                    //Get User info
                    mDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);

                    if (user.getImage()!=null &&  user.getPassword().equals(pwd)) {
                        {
                            if(Boolean.parseBoolean(user.getIsStaff())){
                                Toast.makeText(MainActivity.this, "התחברת כמנהל בהצלחה", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "התחברת בהצלחה!", Toast.LENGTH_SHORT).show();
                            }
                            Intent homeIntent = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();

                            table_user.removeEventListener(this);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "סיסמה שגויה!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "המשתמש לא קיים במערכת", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mDialog.dismiss();

            }
        });

    }

}
