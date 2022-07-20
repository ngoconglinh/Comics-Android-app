package com.example.baithi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.baithi.fragment.ChangePasswordFragment;
import com.example.baithi.fragment.HomeFragment;
import com.example.baithi.fragment.MyProfileFragment;
import com.example.baithi.fragment.NewComicsFragment;
import com.example.baithi.fragment.SettingFragment;
import com.example.baithi.fragment.SupportFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_ADD_COMICS = 1;
    private static final int FRAGMENT_SETTING = 2;
    private static final int FRAGMENT_SUPPORT = 3;
    private static final int FRAGMENT_MY_PROFILE = 4;
    private static final int FRAGMENT_CHANGE_PASSWORD = 5;

    private int mCurrentFragment = FRAGMENT_HOME;

    private NavigationView navigationView;

    private ImageView img_avatar;
    private TextView textView_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.home).setChecked(true);

        showUserInformation();
    }
    private void initUi(){
        navigationView = findViewById(R.id.navigation_view);
        img_avatar = navigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        textView_email = navigationView.getHeaderView(0).findViewById(R.id.text_view_email);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }else if (id == R.id.add_comics){
            if(mCurrentFragment != FRAGMENT_ADD_COMICS){
                replaceFragment(new NewComicsFragment());
                mCurrentFragment = FRAGMENT_ADD_COMICS;
            }
        }else if (id == R.id.setting){
            if(mCurrentFragment != FRAGMENT_SETTING){
                replaceFragment(new SettingFragment());
                mCurrentFragment = FRAGMENT_SETTING;
            }
        }else if (id == R.id.support){
            if(mCurrentFragment != FRAGMENT_SUPPORT){
                replaceFragment(new SupportFragment());
                mCurrentFragment = FRAGMENT_SUPPORT;
            }
        }else if (id == R.id.my_profile){
            if(mCurrentFragment != FRAGMENT_MY_PROFILE){
                replaceFragment(new MyProfileFragment());
                mCurrentFragment = FRAGMENT_MY_PROFILE;
            }
        }else if (id == R.id.change_password){
            if(mCurrentFragment != FRAGMENT_CHANGE_PASSWORD){
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
            }
        }else if (id == R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            Intent i1 = new Intent(this, LoginActivity.class);
            startActivity(i1);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        textView_email.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.icons8_user_100).into(img_avatar);
    }
}