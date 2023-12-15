package com.example.aduraapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.MedisDetailRiwayatActivity;
import com.example.aduraapp.MedisRiwayat;
import com.example.aduraapp.R;

import java.util.ArrayList;

public class MedisListAdapter extends RecyclerView.Adapter<MedisListAdapter.MyViewHolder> {
    Context context;
    ArrayList<MedisRiwayat> list;


    public MedisListAdapter(Context context, ArrayList<MedisRiwayat> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_medis_riwayat,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        MedisRiwayat MedisRiwayat = list.get(position);
        holder.kolomtanggalkejadian.setText(MedisRiwayat.getTanggalkejadian());
        holder.kolomketerangan.setText(MedisRiwayat.getKeterangan());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(context, MedisDetailRiwayatActivity.class);
                intent.putExtra("tanggalkejadian", MedisRiwayat.getTanggalkejadian());
                intent.putExtra("keterangan", MedisRiwayat.getKeterangan());
                intent.putExtra("imageUrl", MedisRiwayat.getImageUrl());
                intent.putExtra("namapelapor", MedisRiwayat.getNamapelapor());
                intent.putExtra("nomorpelapor", MedisRiwayat.getNomorpelapor());
                intent.putExtra("lokasikejadian", MedisRiwayat.getLokasikejadian());

//                menyimpan id laporan
                intent.putExtra("nextIdLaporan", MedisRiwayat.getnextIdLaporan());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){return list.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView kolomtanggalkejadian, kolomketerangan;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            kolomtanggalkejadian = itemView.findViewById(R.id.kolomtanggalkejadian);
            kolomketerangan = itemView.findViewById(R.id.kolomketerangan);
        }
    }

}


