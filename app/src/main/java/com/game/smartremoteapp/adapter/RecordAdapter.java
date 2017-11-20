package com.game.smartremoteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.game.smartremoteapp.R;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/17.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder1> {
    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mInflater;

    public RecordAdapter(Context context, List<String> datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.recordadpter_item,parent,false);
        MyViewHolder1 myviewHolder=new MyViewHolder1(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, int position) {
        holder.name_tv.setText("可爱熊");
        holder.times_tv.setText("2017/10/17 15:27");
        holder.results_tv.setText("抓取成功");
        Glide.with(mContext).load(R.drawable.wawa).into(holder.title_img);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder{

        ImageView title_img;
        TextView name_tv,times_tv,results_tv;

        public MyViewHolder1(View itemView) {
            super(itemView);
            title_img= (ImageView) itemView.findViewById(R.id.title_img);
            name_tv= (TextView) itemView.findViewById(R.id.name_tv);
            times_tv= (TextView) itemView.findViewById(R.id.times_tv);
            results_tv= (TextView) itemView.findViewById(R.id.results_tv);
        }
    }
}

