package com.example.aduraapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.KeamananAdminDetailRiwayatActivity;
import com.example.aduraapp.R;
import com.example.aduraapp.models.KeamananAdminRiwayat;

import java.util.ArrayList;

public class KeamananAdminListAdapter extends RecyclerView.Adapter<KeamananAdminListAdapter.MyViewHolder> {

    Context context;

    ArrayList<KeamananAdminRiwayat> list;


    public KeamananAdminListAdapter(Context context, ArrayList<KeamananAdminRiwayat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_keamanan_adminriwayat,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        KeamananAdminRiwayat KeamananAdminRiwayat = list.get(position);
        holder.kolomtanggalkejadian.setText(KeamananAdminRiwayat.getTanggalkejadian());
        holder.kolomketerangan.setText(KeamananAdminRiwayat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                Intent intent = new Intent(context, KeamananAdminDetailRiwayatActivity.class);
                intent.putExtra("tanggalkejadian", KeamananAdminRiwayat.getTanggalkejadian());
                intent.putExtra("keterangan", KeamananAdminRiwayat.getKeterangan());
                intent.putExtra("imageUrl", KeamananAdminRiwayat.getImageUrl());
                intent.putExtra("imageName", KeamananAdminRiwayat.getImageName());
                intent.putExtra("namapelapor", KeamananAdminRiwayat.getNamapelapor());
                intent.putExtra("nomorpelapor", KeamananAdminRiwayat.getNomorpelapor());
                intent.putExtra("lokasikejadian", KeamananAdminRiwayat.getLokasikejadian());
                intent.putExtra("status", KeamananAdminRiwayat.getStatus());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kolomtanggalkejadian, kolomketerangan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kolomtanggalkejadian = itemView.findViewById(R.id.kolomtanggalkejadian);
            kolomketerangan = itemView.findViewById(R.id.kolomketerangan);

        }
    }

}
