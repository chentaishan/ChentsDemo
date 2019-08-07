package com.example.wanandroiddemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mContent;
    private NavigationView mNavigation;
    private DrawerLayout mDrawerlayout;
    private Toolbar mToolbar;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        mNavigation = (NavigationView) findViewById(R.id.navigation);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mToolbar.setTitle("WAN");
        setSupportActionBar(mToolbar);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerlayout,mToolbar,R.string.open,R.string.close);

        mDrawerlayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();


        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.one:
                        Log.d(TAG, "onNavigationItemSelected: ");
                        break;
                }
                return false;
            }
        });


        final FragmentManager supportFragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content,new PageOneFragment());


        fragmentTransaction.commit();

    }
}
