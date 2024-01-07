package com.example.aduraapp;

import android.content.Intent;
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


public class RiwayatAdminFragment extends Fragment {
    private RecyclerView rvMenu;

    public RiwayatAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        rvMenu = view.findViewById(R.id.rv_menu);

        TextView textRiwayat = view.findViewById(R.id.textRiwayat);
        textRiwayat.setText("Daftar Laporan Masuk");
        MenuAdapter adapter = new MenuAdapter(getMenu(), new MenuAdapter.ItemMenuClickListener() {
            @Override
            public void onItemMenuClick(Menu menu) {
                if (menu.getNamaMenu().equals("Keamanan")) {
                    Intent keamananIntent = new Intent(requireContext(), KeamananAdminRiwayatList.class);
                    keamananIntent.putExtra("Nama_Menu", menu.getNamaMenu());
                    startActivity(keamananIntent);
                }
                if (menu.getNamaMenu().equals("Medis")) {
                    Intent keamananIntent = new Intent(requireContext(), MedisAdminRiwayatList.class);
                    keamananIntent.putExtra("Nama_Menu", menu.getNamaMenu());
                    startActivity(keamananIntent);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        rvMenu.setLayoutManager(layoutManager);
        rvMenu.setAdapter(adapter);
        return view;
    }

    private ArrayList<Menu> getMenu() {
        ArrayList<Menu> listMenu = new ArrayList<>();

        listMenu.add(new Menu(
                R.drawable.ic_medis,
                "Medis",
                R.color.blue

        ));
        listMenu.add(new Menu(
                R.drawable.ic_keamanan,
                "Keamanan",
                R.color.kuning
        ));
        listMenu.add(new Menu(
                R.drawable.ic_kebakaran,
                "Kebakaran",
                R.color.merah
        ));
        // Add more menu items as needed

        return listMenu;
    }
}