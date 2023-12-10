package com.example.aduraapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.KeamananDetailRiwayatActivity;
import com.example.aduraapp.KeamananRiwayat;
import com.example.aduraapp.R;

import java.util.ArrayList;

public class KeamananListAdapter extends RecyclerView.Adapter<KeamananListAdapter.MyViewHolder> {

    Context context;

    ArrayList<KeamananRiwayat> list;


    public KeamananListAdapter(Context context, ArrayList<KeamananRiwayat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_keamanan_riwayat,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        KeamananRiwayat KeamananRiwayat = list.get(position);
        holder.kolomtanggalkejadian.setText(KeamananRiwayat.getTanggalkejadian());
        holder.kolomketerangan.setText(KeamananRiwayat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here
                Intent intent = new Intent(context, KeamananDetailRiwayatActivity.class);
                intent.putExtra("tanggalkejadian", KeamananRiwayat.getTanggalkejadian());
                intent.putExtra("keterangan", KeamananRiwayat.getKeterangan());
                intent.putExtra("imageUrl", KeamananRiwayat.getImageUrl());
                intent.putExtra("imageUri", KeamananRiwayat.getImageUri());
                intent.putExtra("namapelapor", KeamananRiwayat.getNamapelapor());
                intent.putExtra("nomorpelapor", KeamananRiwayat.getNomorpelapor());
                intent.putExtra("lokasikejadian", KeamananRiwayat.getLokasikejadian());
                // Add more data if needed
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
