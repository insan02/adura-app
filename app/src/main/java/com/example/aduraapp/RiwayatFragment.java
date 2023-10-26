package com.example.aduraapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.adapters.MenuAdapter;
import com.example.aduraapp.models.Menu;

import java.util.ArrayList;


public class RiwayatFragment extends Fragment {
    private RecyclerView rvMenu;

    public RiwayatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        rvMenu = view.findViewById(R.id.rv_menu);
        TextView textRiwayat = view.findViewById(R.id.textRiwayat);
        textRiwayat.setText("Riwayat Laporan Anda");
        MenuAdapter adapter = new MenuAdapter(getMenu());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        rvMenu.setLayoutManager(layoutManager);
        rvMenu.setAdapter(adapter);
        return view;
    }

    private ArrayList<Menu> getMenu() {
        ArrayList<Menu> listMenu = new ArrayList<>();

        listMenu.add(new Menu(
                null,
                "Medis"

        ));
        listMenu.add(new Menu(
                null,
                "Keamanan"
        ));
        listMenu.add(new Menu(
                null,
                "Kebakaran"
        ));
        // Add more menu items as needed

        return listMenu;
    }
}