package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfilFragment extends Fragment {
    TextView kolomemail, kolomusername;
    private DatabaseReference reference;
    private FirebaseDatabase db;
    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        kolomusername = view.findViewById(R.id.textViewKolomUsername2);
        kolomemail = view.findViewById(R.id.textViewKolomEmail);
        Button buttonGantiPassword = view.findViewById(R.id.buttonGantiPassword);
        Button buttonEdit = view.findViewById(R.id.buttonEdit);
        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("users").child(idUser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);

                kolomusername.setText(name);
                kolomemail.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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