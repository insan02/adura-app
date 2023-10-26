package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        rvMenu = view.findViewById(R.id.rv_menu);
        MenuAdapter adapter = new MenuAdapter(getMenu(), new MenuAdapter.ItemMenuClickListener() {
            @Override
            public void onItemMenuClick(Menu menu) {
                if (menu.getNamaMenu().equals("Medis")) {
                    Intent medisIntent = new Intent(requireContext(), MedisCreateActivity.class);
                    medisIntent.putExtra("Nama_Menu", menu.getNamaMenu());
                    startActivity(medisIntent);
                }
                if (menu.getNamaMenu().equals("Kebakaran")) {
                    Intent medisIntent = new Intent(requireContext(), kebakarancreateactivity.class);
                    medisIntent.putExtra("Nama_Menu", menu.getNamaMenu());
                    startActivity(medisIntent);
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

        // Add sample menu items; replace with your actual data
        listMenu.add(new Menu(
                null,
                "Panduan"
        ));
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