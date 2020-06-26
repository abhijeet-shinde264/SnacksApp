package com.auapp.snacksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ShowHistoryAdapter extends RecyclerView.Adapter<ShowHistoryAdapter.ShowHolder> {
    List<BuyerData> data;
    Context context;

    public ShowHistoryAdapter(List<BuyerData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card,parent,false);
        ShowHolder showHolder = new ShowHolder(view);
        return showHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {
        BuyerData data1 = data.get(position);
        holder.t1.setText(data1.getIte());
        holder.t2.setText(data1.getQuan());
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ShowHolder extends RecyclerView.ViewHolder {
        TextView t1,t2;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.namehis);
            t2 = itemView.findViewById(R.id.quanthis);
        }
    }
}
