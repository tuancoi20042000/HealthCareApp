package com.example.healthcareapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.data_local.DataLocalManager;
import com.example.healthcareapp.fragment.FragmentHome;
import com.example.healthcareapp.model.Users;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    TextView textViewEmail;
    ImageView imageViewPro;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Users users = DataLocalManager.getUsers();

        if(users !=null){
            setContentView(R.layout.activity_home);
            navigationView = findViewById(R.id.navigationView);
            imageViewPro = navigationView.getHeaderView(0).findViewById(R.id.imageViewPro);
            textViewEmail = navigationView.getHeaderView(0). findViewById(R.id.textViewEmail);

            String email=users.getEmail();
            String avatar = users.getAvatar();
            textViewEmail.setText(email);
            Glide.with(this).load(avatar).error(R.drawable.girl).into(imageViewPro);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawerLayout = findViewById(R.id.drawLayout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_Drawer_Open, R.string.navigation_Drawer_Close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);


            replaceActivity(new FragmentHome());
            navigationView.getMenu().findItem(R.id.item_home).setChecked(true);

            getSupportActionBar().setDisplayShowTitleEnabled(false);


        }else{
            setContentView(R.layout.activity_home1);
            navigationView = findViewById(R.id.navigationView);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawerLayout = findViewById(R.id.drawLayout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_Drawer_Open, R.string.navigation_Drawer_Close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);


            replaceActivity(new FragmentHome());
            navigationView.getMenu().findItem(R.id.item_home).setChecked(true);

            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_home) {
            replaceActivity(new FragmentHome());


        } else if (id == R.id.item_profile) {


            Users users = DataLocalManager.getUsers();
            if(users !=null){
                Intent intent1 = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent1);
                finish();
            }else{
                Toast.makeText(HomeActivity.this, "Hãy đăng nhập để xem thông tin", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.item_info) {
            Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
            startActivity(intent);
        }else if(id == R.id.item_Login){
            Intent intent3 = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent3);
            finish();
        }else if(id == R.id.item_logout){
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Đăng Xuất")
                    .setMessage("Xác Nhận Đăng Xuất?")
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                        }
                    })
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            DataLocalManager.getInstance().myShareReference.putStringValue("fieldName","");
                            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceActivity(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}