package com.example.aduraapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.KebakaranDetailRiwayatActivity;
import com.example.aduraapp.models.KebakaranRiwayat;
import com.example.aduraapp.R;

import java.util.ArrayList;

public class KebakaranListAdapter extends RecyclerView.Adapter<KebakaranListAdapter.MyViewHolder> {

    Context context;

    ArrayList<KebakaranRiwayat> list;


    public KebakaranListAdapter(Context context, ArrayList<KebakaranRiwayat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_kebakaran_riwayat,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        KebakaranRiwayat KebakaranRiwayat = list.get(position);
        holder.kolomtanggalkejadian.setText(KebakaranRiwayat.getTanggalkejadian());
        holder.kolomketerangan.setText(KebakaranRiwayat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                Intent intent = new Intent(context, KebakaranDetailRiwayatActivity.class);
                intent.putExtra("tanggalkejadian", KebakaranRiwayat.getTanggalkejadian());
                intent.putExtra("keterangan", KebakaranRiwayat.getKeterangan());
                intent.putExtra("imageUrl", KebakaranRiwayat.getImageUrl());
                intent.putExtra("imageName", KebakaranRiwayat.getImageName());
                intent.putExtra("namapelapor", KebakaranRiwayat.getNamapelapor());
                intent.putExtra("nomorpelapor", KebakaranRiwayat.getNomorpelapor());
                intent.putExtra("lokasikejadian", KebakaranRiwayat.getLokasikejadian());
                intent.putExtra("nextIdLaporan", KebakaranRiwayat.getnextIdLaporan());
                intent.putExtra("status", KebakaranRiwayat.getStatus());
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