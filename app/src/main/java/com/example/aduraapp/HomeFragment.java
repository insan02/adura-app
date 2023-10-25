package com.example.aduraapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aduraapp.adapters.MenuAdapter;
import com.example.aduraapp.models.Menu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvMenu;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvMenu = view.findViewById(R.id.rv_menu);
        // Initialize and set the RecyclerView's layout manager and adapter here
        MenuAdapter adapter = new MenuAdapter(getMenu());
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