package com.auapp.snacksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ShowUserAdapter extends RecyclerView.Adapter<ShowUserAdapter.Holder> {
    private List<Register1> listData;
    private Context context;

    public ShowUserAdapter(List<Register1> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Register1 register1 =listData.get(position);
        holder.tnam.setText(register1.getName());
        holder.temi.setText(register1.getEmail());
        holder.tdes.setText(register1.getProf());
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tnam,temi,tdes;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tnam = itemView.findViewById(R.id.name);
            temi = itemView.findViewById(R.id.email);
            tdes = itemView.findViewById(R.id.desig);
        }
    }
}
