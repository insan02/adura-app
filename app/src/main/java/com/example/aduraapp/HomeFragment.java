package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.adapters.MenuAdapter;
import com.example.aduraapp.models.Menu;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rvMenu;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the buttons in the layout
        Button buttonPanduan = view.findViewById(R.id.buttonPanduan);
        Button buttonMedis = view.findViewById(R.id.buttonMedis);
        Button buttonKeamanan = view.findViewById(R.id.buttonKeamanan);
        Button buttonKebakaran = view.findViewById(R.id.buttonKebakaran);

        // Set click listeners for each button
        buttonPanduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PanduanActivity
                Intent intent = new Intent(getActivity(), PanduanActivity.class);
                startActivity(intent);
            }
        });

        buttonMedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start KebakaranActivity
                Intent intent = new Intent(getActivity(), MedisCreateActivity.class);
                startActivity(intent);
            }
        });

        buttonKeamanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start KeamananActivity
                Intent intent = new Intent(getActivity(), KeamananCreateActivity.class);
                startActivity(intent);
            }
        });

        buttonKebakaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start KeamananActivity
                Intent intent = new Intent(getActivity(), KebakaranCreateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
