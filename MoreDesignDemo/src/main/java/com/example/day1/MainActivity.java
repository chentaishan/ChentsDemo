package com.example.day1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.day1.bean.Food;
import com.example.day1.utils.Contants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

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
    private RecyclerView mViewRecycler;
    private int pageIndex=1;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();


        initData();
    }

    private void initData() {


        Log.d(TAG, "initData: url"+(Contants.foodUrl +pageIndex));
        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(Contants.foodUrl +pageIndex)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();

                Log.d(TAG, "onResponse: "+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    //json 解析
                    JSONArray array = jsonObject.getJSONArray("data");
                    // title pic
                    final ArrayList<Food.DataBean> dataBeans = new ArrayList<>();


                    for (int i = 0; i <array.length() ; i++) {

                        JSONObject obj= array.getJSONObject(i);
                        Food.DataBean dataBean = new Food.DataBean();

                        dataBean.setTitle(obj.optString("title"));
                        dataBean.setPic(obj.optString("pic"));

                        dataBeans.add(dataBean);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pageIndex==1){
                                myAdapter.initData(dataBeans);

                            }else{
                                myAdapter.loadMoreData(dataBeans);

                            }


                        }
                    });




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
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



        return super.onOptionsItemSelected(item);
    }

    private void initView() {

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
        mToolbar.setNavigationIcon(R.drawable.back);
        //菜单点击事件（注意需要在setSupportActionBar(toolbar)之后才有效果）
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(TAG, "onMenuItemClick: " + menuItem.getTitle());

                switch (menuItem.getItemId()) {

                    case R.id.menu_1:

                        mViewRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        Log.d(TAG, "onOptionsItemSelected: " + menuItem.getTitle());
                        break;

                    case R.id.menu_2:
                        mViewRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                        Log.d(TAG, "onOptionsItemSelected: " + menuItem.getTitle());
                        break;

                    case R.id.menu_3:
                        mViewRecycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                        Log.d(TAG, "onOptionsItemSelected: " + menuItem.getTitle());
                        break;
                }
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
        mViewRecycler = (RecyclerView) findViewById(R.id.recycler_view);


        registerForContextMenu(mViewRecycler);

        mViewRecycler.setLayoutManager(new LinearLayoutManager(this));
        mViewRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter(this);
        mViewRecycler.setAdapter(myAdapter);
    }


    private static final String TAG = "MainActivity";
    private int GROUP_ID = 0;
    private final  int ITEM_ID = 0;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


        getMenuInflater().inflate(R.menu.context_menu,menu);

//        menu.add(GROUP_ID,ITEM_ID,0,"删除");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.menu_2:

                Log.d(TAG, "onContextItemSelected: "+item.getItemId());
                break;
            case R.id.menu_3:

                Log.d(TAG, "onContextItemSelected: "+item.getItemId());
                break;
        }

        return super.onContextItemSelected(item);
    }
}
