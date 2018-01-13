package com.idoon.fruitandveg;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idoon.fruitandveg.Common.Common;
import com.idoon.fruitandveg.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnSignIn,BtnIn;
    TextView txtForgotPwd;

    FirebaseDatabase database;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        BtnIn = (Button) findViewById(R.id.BtnIn);
        txtForgotPwd = (TextView)findViewById(R.id.txtForgotPwd);

        //Init FireBase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPwdDialog();
            }
        });

        BtnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("מתחבר כאורח...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User Guest = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                        Toast.makeText(SignIn.this,"התחברת בהצלחה כאורח!",Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(SignIn.this, Home.class);
                        Common.currentUser = Guest;
                        startActivity(homeIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("אנא המתן...");
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Check if user not exist in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //Get User info
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                                if (user.getImage()!=null &&  user.getPassword().equals(edtPassword.getText().toString())) {
                                    {
                                        if(Boolean.parseBoolean(user.getIsStaff())){
                                            Toast.makeText(SignIn.this, "התחברת כמנהל בהצלחה", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(SignIn.this, "התחברת בהצלחה!", Toast.LENGTH_SHORT).show();
                                        }
                                        Intent homeIntent = new Intent(SignIn.this, Home.class);
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();

                                        table_user.removeEventListener(this);
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(SignIn.this, "סיסמה שגויה!", Toast.LENGTH_SHORT).show();
                                }
                        }
                    else
                    {
                        mDialog.dismiss();
                        Toast.makeText(SignIn.this, "המשתמש לא קיים במערכת", Toast.LENGTH_SHORT).show();
                    }
                }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mDialog.dismiss();

                    }
                });
            }

        });
    }

    private void showForgotPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("שכחתי סיסמה");
        builder.setMessage("הכנס את הקוד אבטחה:");

        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.forgot_password_layout,null);

        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final MaterialEditText edtPhone = (MaterialEditText)forgot_view.findViewById(R.id.edtPhone);
        final MaterialEditText edtSecureCode = (MaterialEditText)forgot_view.findViewById(R.id.edtSecureCode);

        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Chcek if user available
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(edtPhone.getText().toString())
                                .getValue(User.class);
                        if(user.getSecureCode().equals(edtSecureCode.getText().toString()))
                            Toast.makeText(SignIn.this,"הסיסמה שלך: "+user.getPassword(),Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SignIn.this,"קוד אבטחה שגוי",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        builder.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }
}
