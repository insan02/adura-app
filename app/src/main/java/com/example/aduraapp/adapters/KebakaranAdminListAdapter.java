package com.example.aduraapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.KebakaranAdminDetailRiwayatActivity;
import com.example.aduraapp.R;
import com.example.aduraapp.models.KebakaranAdminRiwayat;

import java.util.ArrayList;

public class KebakaranAdminListAdapter extends RecyclerView.Adapter<KebakaranAdminListAdapter.MyViewHolder> {

    Context context;

    ArrayList<KebakaranAdminRiwayat> list;


    public KebakaranAdminListAdapter(Context context, ArrayList<KebakaranAdminRiwayat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_kebakaran_adminriwayat,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        KebakaranAdminRiwayat KebakaranAdminRiwayat = list.get(position);
        holder.kolomtanggalkejadian.setText(KebakaranAdminRiwayat.getTanggalkejadian());
        holder.kolomketerangan.setText(KebakaranAdminRiwayat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                Intent intent = new Intent(context, KebakaranAdminDetailRiwayatActivity.class);
                intent.putExtra("tanggalkejadian", KebakaranAdminRiwayat.getTanggalkejadian());
                intent.putExtra("keterangan", KebakaranAdminRiwayat.getKeterangan());
                intent.putExtra("imageUrl", KebakaranAdminRiwayat.getImageUrl());
                intent.putExtra("imageName", KebakaranAdminRiwayat.getImageName());
                intent.putExtra("namapelapor", KebakaranAdminRiwayat.getNamapelapor());
                intent.putExtra("nomorpelapor", KebakaranAdminRiwayat.getNomorpelapor());
                intent.putExtra("lokasikejadian", KebakaranAdminRiwayat.getLokasikejadian());
                intent.putExtra("status", KebakaranAdminRiwayat.getStatus());

                intent.putExtra("nextIdLaporan", KebakaranAdminRiwayat.getnextIdLaporan());
                intent.putExtra("idUser", KebakaranAdminRiwayat.getidUser());
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
