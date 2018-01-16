package com.idoon.fruitandveg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.idoon.fruitandveg.Common.Common;
import com.idoon.fruitandveg.Interface.ItemClickListener;
import com.idoon.fruitandveg.Model.Category;
import com.idoon.fruitandveg.ViewHolder.MenuViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;

    TextView txtFullName;
    ImageView imgprofile;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("תפריט");
        setSupportActionBar(toolbar);

        //Init FireBase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        Paper.init(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set Name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        imgprofile = (ImageView)findViewById(R.id.imgprofile);

        txtFullName.setText(Common.currentUser.getName());

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
    }

    private void loadMenu() {

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(Home.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                        //get categoryID and send to new activity
                        Intent foodList = new Intent(Home.this,FoodList.class);
                        //Because CategoryID is key , so we just get key of this item
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            getinfo();

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_log_out) {

            //Delete Remeber user & pass
            Paper.book().destroy();

            //LogOut
            Intent SignIn = new Intent(Home.this, com.idoon.fruitandveg.SignIn.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(SignIn);

        }
        else if (id == R.id.nav_change_pwd){
            DatabaseReference table_user = database.getReference("User");
            if(table_user == null && table_user.equals("")) {
                Toast.makeText(Home.this,"אתה אורח - לא רשאי לשנות סיסמה",Toast.LENGTH_SHORT).show();
            }
            else{
                showChangePasswordDialog();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getinfo() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("פרטים אישיים");

        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_pwd = inflater.inflate(R.layout.info_user_layout,null);

        final TextView txtName = (TextView) layout_pwd.findViewById(R.id.txtName);
        final TextView txtPhone = (TextView) layout_pwd.findViewById(R.id.txtPhone);
        final TextView txtgetCode = (TextView) layout_pwd.findViewById(R.id.txtgetCode);
        final ImageView imgprofile =(ImageView)layout_pwd.findViewById(R.id.imgprofile);

        alertDialog.setView(layout_pwd);

        alertDialog.setNegativeButton("אישור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final android.app.AlertDialog waitingDialog = new SpotsDialog(Home.this);
                //waitingDialog.show();
                dialogInterface.dismiss();



                //Name of user:
                txtName.setText(Common.currentUser.getName());
                txtPhone.setText(Common.currentUser.getPhone());
                txtgetCode.setText(Common.currentUser.getSecureCode());
                //imgprofile.ge(Common.currentUser.getImage());
            }
        });
        alertDialog.show();
    }


    private void showChangePasswordDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("שנה סיסמה");
        alertDialog.setMessage("אנא מלא את הפרטים הבאים");

        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_pwd = inflater.inflate(R.layout.change_password_layout,null);


        final MaterialEditText editPassword = (MaterialEditText)layout_pwd.findViewById(R.id.edtPhone);
        final MaterialEditText edtNewPassword = (MaterialEditText)layout_pwd.findViewById(R.id.edtNewPassword);
        final MaterialEditText edtRepeatPassword = (MaterialEditText)layout_pwd.findViewById(R.id.edtRepeatPassword);
        alertDialog.setView(layout_pwd);

        //Button
        alertDialog.setPositiveButton("שנה סיסמה", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Change Password here

                        final android.app.AlertDialog waitingDialog = new SpotsDialog(Home.this);
                        waitingDialog.show();

                        //Check old pass
                        if(editPassword.getText().toString().equals(Common.currentUser.getPassword()))
                        {
                            //check new password and repeat password
                            if(edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString()))
                            {
                                Map<String,Object> passwordUpdate = new HashMap<>();
                                passwordUpdate.put("password",edtNewPassword.getText().toString());

                                //Make update
                                DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
                                user.child(Common.currentUser.getPhone())
                                        .updateChildren(passwordUpdate)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                waitingDialog.dismiss();
                                                Toast.makeText(Home.this,"הסיסמה עודכנה בהצלחה",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Home.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else {
                                waitingDialog.dismiss();
                                Toast.makeText(Home.this,"הסיסמה החדשה לא מתאימה",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            waitingDialog.dismiss();
                            Toast.makeText(Home.this,"הסיסמה המקורית לא מתאימה",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        alertDialog.setNegativeButton("בטל", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();
    }
}
