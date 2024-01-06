package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProfilFragment extends Fragment {

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        Button buttonGantiPassword = view.findViewById(R.id.buttonGantiPassword);
        Button buttonEdit = view.findViewById(R.id.buttonEdit);
        // Set click listeners for each button
        buttonGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PanduanActivity
                Intent intent = new Intent(getActivity(), GantiPasswordActivity.class);
                startActivity(intent);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PanduanActivity
                Intent intent = new Intent(getActivity(), GantiUsernameActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}