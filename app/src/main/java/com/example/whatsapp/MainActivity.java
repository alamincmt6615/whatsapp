package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp.adapter.TabAccessorAdapter;
import com.example.whatsapp.user_auth.ui.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager main_page_view_pager;
    private TabLayout tabLayout;
    private TabAccessorAdapter tabAccessorAdapter;
    private FirebaseUser currient_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_page_app_bar);
        main_page_view_pager = findViewById(R.id.main_page_view_pager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Whatsapp");

        tabLayout = findViewById(R.id.main_page_tab_layout);
        tabAccessorAdapter = new TabAccessorAdapter(getSupportFragmentManager());
        main_page_view_pager.setAdapter(tabAccessorAdapter);
        tabLayout.setupWithViewPager(main_page_view_pager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currient_user == null){
            sentUserToLoginActivity();
        }
    }

    private void sentUserToLoginActivity() {
        Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login_intent);
    }
}
