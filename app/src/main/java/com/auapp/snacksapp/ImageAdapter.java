package com.auapp.snacksapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
   // private AdapterView.OnItemClickListener mListener;
    public ImageAdapter(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCur=mUploads.get(position);
        holder.img_description.setText(uploadCur.getName());
        holder.quant.setText(uploadCur.getQuantity());
        Picasso.with(mContext)
                .load(uploadCur.getImgUrl())
                .placeholder(R.drawable.imagepreview)
                .fit()
                .centerCrop()
                .into(holder.image_view);
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView img_description,quant;
        public ImageView image_view;
        public String key;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img_description=itemView.findViewById(R.id.img_description);
            image_view=itemView.findViewById(R.id.image_view);
            quant = itemView.findViewById(R.id.qu);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Intent i1 = new Intent(mContext,DeletePage.class);o
//                    //openDialogDelete(key,img_description,quant);
//                    View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_show,null);
//                    final EditText email = view.findViewById(R.id.email111);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setMessage("Please enter your email")
//                            .setView(view)
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String em = email.getText().toString().trim();
//                                    if (em.equals("abhijeetshinde1999@gmail.com")){
//                                        //Toast.makeText(mContext, "Working", Toast.LENGTH_SHORT).show();
////                                        Upload upload = new Upload();
//                                        Intent i1 = new Intent(mContext,DeletePage.class);
//                                        //i1.putExtra("key",key);
//                                        i1.putExtra("Name",img_description.getText().toString().trim());
//                                        i1.putExtra("Quan",quant.getText().toString().trim());
//                                        mContext.startActivity(i1);
//
//                                    }else{
//                                        Toast.makeText(mContext, "Sorry your not admin", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //Toast.makeText(ShowSnacks.this, "Sorry your not admin", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }
//            });
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
    }
//    private void openDialogDelete(String key, TextView name, TextView quan) {
//
//    }
}
