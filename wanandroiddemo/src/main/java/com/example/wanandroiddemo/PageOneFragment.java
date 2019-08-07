package com.example.wanandroiddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PageOneFragment extends Fragment {
    private XRecyclerView mXrecyclerview;
    MyRecyclerAdapter myRecyclerAdapter;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj instanceof BannerBean) {


                BannerBean bannerBean = (BannerBean) msg.obj;

                myRecyclerAdapter.initBannerList(bannerBean.getData());


            } else {

                ResultBean resultBean = (ResultBean) msg.obj;
                myRecyclerAdapter.initNormalList(resultBean.getData().getDatas());

            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.page_fragment, null);


        initView(root);

        initData();

        return root;


    }

    private void initData() {

        new Thread() {
            @Override
            public void run() {
                super.run();

                String list = getNetData(homeList);
                String banner = getNetData(bannerList);


                if (list != null) {

                    final ResultBean resultBean = new Gson().fromJson(list, ResultBean.class);
                    Message message = new Message();

                    message.obj = resultBean;

                    handler.sendMessage(message);
                }


                if (banner != null) {

                    final BannerBean bannerBean = new Gson().fromJson(banner, BannerBean.class);
                    Message message = new Message();

                    message.obj = bannerBean;

                    handler.sendMessage(message);
                }


//
//


            }
        }.start();

    }

    String homeList = "https://www.wanandroid.com/article/list/1/json";
    String bannerList = "https://www.wanandroid.com/banner/json";

    private String getNetData(String netUrl) {

        try {
            final URL url = new URL(netUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];

            int readLength = 0;
            while ((readLength = inputStream.read(bytes)) != -1) {

                outputStream.write(bytes, 0, readLength);
            }


            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    private void initView(@NonNull final View itemView) {
        mXrecyclerview = (XRecyclerView) itemView.findViewById(R.id.xrecyclerview);

        mXrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerAdapter = new MyRecyclerAdapter(getActivity());

        mXrecyclerview.setAdapter(myRecyclerAdapter);
    }
}
