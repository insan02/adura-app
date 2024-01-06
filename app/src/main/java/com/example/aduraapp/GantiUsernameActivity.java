package com.example.aduraapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GantiUsernameActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseDatabase db;
    private EditText kolomEditUsername;
    private Button buttonSimpan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantiusername);

        kolomEditUsername = findViewById(R.id.editTextUsername);
        buttonSimpan = findViewById(R.id.buttonSimpan);

        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("users").child(idUser);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    kolomEditUsername.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsername();
            }
        });
    }

    private void updateUsername() {
        String newUsername = kolomEditUsername.getText().toString().trim();

        if (newUsername.isEmpty()) {
            Toast.makeText(GantiUsernameActivity.this, "Isi semua kolom terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        reference.child("name").setValue(newUsername).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(GantiUsernameActivity.this, "Username berhasil diperbarui", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(GantiUsernameActivity.this, "Gagal memperbarui username", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
