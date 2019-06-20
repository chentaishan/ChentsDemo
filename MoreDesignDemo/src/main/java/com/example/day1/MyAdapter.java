package com.example.day1;

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
import com.example.day1.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    private static final String TAG = "MyAdapter";

    List<Food.DataBean> dataBeanList = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
    }


    /**
     * 初始化  刷新
     * @param dataBeanList
     */
    public void initData( List<Food.DataBean> dataBeanList){

        if (this.dataBeanList!=null){

            this.dataBeanList.clear();
        }
        this.dataBeanList.addAll(dataBeanList);

        notifyDataSetChanged();

    }


    /**
     * 加载更多
     * @param dataBeanList
     */
    public void loadMoreData( List<Food.DataBean> dataBeanList){

        if (this.dataBeanList!=null){
            this.dataBeanList.addAll(dataBeanList);

            notifyDataSetChanged();

        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, null);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Food.DataBean dataBean = dataBeanList.get(i);

//        myViewHolder.img
        myViewHolder.title.setText(dataBean.getTitle());
        Glide.with(context).load(dataBean.getPic()).into(myViewHolder.img);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d(TAG, "onLongClick: ");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
