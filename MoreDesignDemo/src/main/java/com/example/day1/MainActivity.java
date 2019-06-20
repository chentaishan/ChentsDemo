package com.example.day1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * okhttp 测试
     */
    private Button mClick;
    private TextView mResutl;
    /**
     * okhttp post测试
     */
    private Button mPostClick;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();


    }

    private void initListener() {


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerlayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_1:

                Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
                break;

            case R.id.menu_2:
                Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
                break;

            case R.id.menu_3:
                Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResutl = (TextView) findViewById(R.id.resutl);
        mPostClick = (Button) findViewById(R.id.post_click);
        mPostClick.setOnClickListener(this);
        mResutl.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Logo
        mToolbar.setLogo(R.drawable.home_highlight);
        // 主标题
        mToolbar.setTitle("Title");
        // 副标题
        mToolbar.setSubtitle("Sub Title");
        //设置toolbar
        setSupportActionBar(mToolbar);
        //左边的小箭头（注意某些版本api需要在setSupportActionBar(toolbar)之后才有效果）
        mToolbar.setNavigationIcon(R.drawable.home_unselected);
        //菜单点击事件（注意需要在setSupportActionBar(toolbar)之后才有效果）
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG, "onMenuItemClick: " + menuItem.getTitle());
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "setNavigationOnClickListener: ");
            }
        });

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
    }


    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        try {
            Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(menu, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:

                testOkhttp();
                break;
            case R.id.post_click:
                testPostOkhttp();
                break;
            case R.id.resutl:
                break;
        }
    }

    String loginUrl = "http://yun918.cn/study/public/index.php/login";

    /**
     * 测试post 表单形式
     * <p>
     * 登录接口：http://yun918.cn/study/public/index.php/login
     * post 参数（String username,String password） 注：username可以是注册的用户名或手机号
     */
    private void testPostOkhttp() {

        // 1.创建ok对象
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", "11")
                .add("password", "11")
                .build();

        // 创建request
        Request request = new Request.Builder()
                .post(formBody)
                .url(loginUrl)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResutl.setText(result);
                    }
                });
            }
        });


    }


    private static final String TAG = "MainActivity";

    /**
     * 测试 ok
     * 1.建造者模式
     * 2.同步异步
     * 3.Response
     */
    private void testOkhttp() {

        OkHttpClient okHttpClient = new OkHttpClient();

        // 建造者 模式  --构建和表示分离
        Request build = new Request.Builder()
                .url("http://apicloud.mob.com/appstore/health/search?key=1ac78a8602d58&name=%E8%BD%AC%E6%B0%A8%E9%85%B6")
                .build();
        Call call = okHttpClient.newCall(build);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();


                Log.d(TAG, "onResponse: " + result + "  是否是主线程===" + (Looper.getMainLooper() == Looper.myLooper()));


                //handler  runonUIThread

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mResutl.setText(result);
                    }
                });

            }
        });


    }
}
