package com.example.thirdplugin_source_analyse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    String url ="https://www.wanandroid.com/blogimgs/ba881591-c386-44ad-8d8a-d30d3984a63f.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ImageView imageView = new ImageView(this);

        Picasso.with(this)
                .load(url)
                .skipMemoryCache()
                .into(imageView);
        Glide.with(this).load(url).into(imageView);
        setContentView(imageView);


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
            }
            @Override
            public void onResponse( Call call,  Response response) throws IOException {

                Log.d(TAG, "onResponse: "+response.body().string());
                String result = response.body().string();
            }
        });



        Retrofit  retrofit = new Retrofit.Builder() .baseUrl(SeriviceApi.url)

                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(SeriviceApi.class).getData().enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }



}
