package com.waveview.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ddsc on 8/26/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<String> strings;
    private Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
        strings = new ArrayList<>();
        strings.add("理财");
        strings.add("保险");
        strings.add("基金");
        strings.add("保险");
        strings.add("基金");
        strings.add("保险");
        strings.add("基金");
        strings.add("保险");
        strings.add("基金");
        strings.add("保险");
        strings.add("基金");
        strings.add("保险");
        strings.add("基金");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_myadapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.content.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tv_adapter_content);
        }

    }
}
