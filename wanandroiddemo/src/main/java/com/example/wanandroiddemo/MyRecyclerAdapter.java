package com.example.wanandroiddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter {


    private static final int normal_type = 1;
    private static final int banner_type = 2;
    List<ResultBean.DataBean.DatasBean> dataBeanList = new ArrayList<>();
    List<BannerBean.DataBean> bannerList = new ArrayList<>();


    Context context;

    public MyRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void initNormalList(List<ResultBean.DataBean.DatasBean> dataBeanList) {

        this.dataBeanList.addAll(dataBeanList);

        notifyDataSetChanged();

    }

    public void initBannerList(List<BannerBean.DataBean> bannerList) {

        this.bannerList.addAll(bannerList);

        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == normal_type) {
            View root = LayoutInflater.from(context).inflate(R.layout.normal_item, viewGroup, false);

            return new ViewHolder(root);
        } else {
            View root = LayoutInflater.from(context).inflate(R.layout.banner_item, viewGroup, false);

            return new BannerViewHolder(root);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {

        if (viewHolder instanceof ViewHolder) {
            if (bannerList.size() > 0) {
                pos = pos - 1;
            }

            final ResultBean.DataBean.DatasBean dataBean = dataBeanList.get(pos);

            ((ViewHolder) viewHolder).title.setText(dataBean.getTitle());
            ((ViewHolder) viewHolder).content.setText(dataBean.getDesc());


        } else {

            ((BannerViewHolder) viewHolder).banner
                    .setImages(bannerList)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {

                            String url = ((BannerBean.DataBean) path).getImagePath();

                            Log.d("LL", "displayImage: " + url);
                            Glide.with(context).load(url).into(imageView);

                        }
                    }).start();


        }


    }

    @Override
    public int getItemCount() {
        return bannerList.size() > 0 ? dataBeanList.size() + 1 : dataBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0 && bannerList != null && bannerList.size() > 0) {
            return banner_type;
        } else {
            return normal_type;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);

        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner banner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.banner);

        }
    }
}
