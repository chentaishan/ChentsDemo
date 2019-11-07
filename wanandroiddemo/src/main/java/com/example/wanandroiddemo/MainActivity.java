package com.example.wanandroiddemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigation;
    private DrawerLayout mDrawerlayout;
    private Toolbar mToolbar;

    private static final String TAG = "MainActivity";
    private LinearLayout mContentMain;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {

        mNavigation = (NavigationView) findViewById(R.id.navigation);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mToolbar.setTitle("WAN");
        setSupportActionBar(mToolbar);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbar, R.string.open, R.string.close);

        mDrawerlayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();


        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.one:
                        Log.d(TAG, "onNavigationItemSelected: ");
                        break;
                }
                return false;
            }
        });

        mDrawerlayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                float nax = mNavigation.getRight();
                Log.d(TAG, "onScrollChange: "+nax);

            }
        });

        mDrawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                float nax = mNavigation.getRight();

                if (nax>0){

                    mContentMain.setX(nax);
                }

                Log.d(TAG, "onDrawerSlide: "+nax);
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {
                float nax = mNavigation.getRight();

                Log.d(TAG, "onDrawerStateChanged: "+nax);
            }
        });



        final FragmentManager supportFragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content, new PageOneFragment());


        fragmentTransaction.commit();

        mContentMain = (LinearLayout) findViewById(R.id.main_content);
    }
}
