package com.example.aduraapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.MedisAdminDetailRiwayatActivity;
import com.example.aduraapp.R;
import com.example.aduraapp.models.MedisAdminRiwayat;

import java.util.ArrayList;

public class MedisAdminListAdapter extends RecyclerView.Adapter<MedisAdminListAdapter.MyViewHolder> {

    Context context;

    ArrayList<MedisAdminRiwayat> list;


    public MedisAdminListAdapter(Context context, ArrayList<MedisAdminRiwayat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_medis_adminriwayat,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MedisAdminRiwayat MedisAdminRiwayat = list.get(position);
        holder.kolomtanggalkejadian.setText(MedisAdminRiwayat.getTanggalkejadian());
        holder.kolomketerangan.setText(MedisAdminRiwayat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                Intent intent = new Intent(context, MedisAdminDetailRiwayatActivity.class);
                intent.putExtra("tanggalkejadian", MedisAdminRiwayat.getTanggalkejadian());
                intent.putExtra("keterangan", MedisAdminRiwayat.getKeterangan());
                intent.putExtra("imageUrl", MedisAdminRiwayat.getImageUrl());
                intent.putExtra("imageName", MedisAdminRiwayat.getImageName());
                intent.putExtra("namapelapor", MedisAdminRiwayat.getNamapelapor());
                intent.putExtra("nomorpelapor", MedisAdminRiwayat.getNomorpelapor());
                intent.putExtra("lokasikejadian", MedisAdminRiwayat.getLokasikejadian());
                intent.putExtra("status", MedisAdminRiwayat.getStatus());
                intent.putExtra("nextIdLaporan", MedisAdminRiwayat.getnextIdLaporan());
                intent.putExtra("idUser", MedisAdminRiwayat.getidUser());
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
