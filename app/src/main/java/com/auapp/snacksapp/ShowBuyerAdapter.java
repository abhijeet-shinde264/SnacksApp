package com.auapp.snacksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ShowBuyerAdapter extends RecyclerView.Adapter<ShowBuyerAdapter.Holder1> {
    private List<BuyerData> listData;
    private Context context;

    public ShowBuyerAdapter(List<BuyerData> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_card,parent,false);
        Holder1 holder = new Holder1(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull Holder1 holder, int position) {
        BuyerData register1 =listData.get(position);
        holder.bnam.setText(register1.getNa());
        holder.bitem.setText(register1.getIte());
        holder.bquan.setText(register1.getQuan());
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder1 extends RecyclerView.ViewHolder {
        TextView bnam,bitem,bquan;
        public Holder1(@NonNull View itemView) {
            super(itemView);
            bnam = itemView.findViewById(R.id.buyername);
            bitem = itemView.findViewById(R.id.buyeritem);
            bquan = itemView.findViewById(R.id.buyerquant);
        }
    }
}
