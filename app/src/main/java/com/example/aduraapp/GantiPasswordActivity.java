package com.example.aduraapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GantiPasswordActivity extends AppCompatActivity {

    private TextInputEditText passwordLamaEditText, passwordBaruEditText;
    private TextInputLayout textLayoutPasswordLama, textLayoutPasswordBaru;
    private Button buttonSimpan;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantipassword);

        mAuth = FirebaseAuth.getInstance();

        passwordLamaEditText = findViewById(R.id.passwordlama);
        passwordBaruEditText = findViewById(R.id.passwordbaru);
        textLayoutPasswordLama = findViewById(R.id.textLayoutPassword);
        textLayoutPasswordBaru = findViewById(R.id.textLayoutPasswordBaru);
        buttonSimpan = findViewById(R.id.buttonSimpan);

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiPassword();
            }
        });
    }

    private void gantiPassword() {
        String passwordLama = passwordLamaEditText.getText().toString();
        String passwordBaru = passwordBaruEditText.getText().toString();

        if (passwordLama.isEmpty() || passwordBaru.isEmpty()) {
            Toast.makeText(this, "Harap isi kedua password", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            mAuth.signInWithEmailAndPassword(user.getEmail(), passwordLama)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(passwordBaru)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(GantiPasswordActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(GantiPasswordActivity.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Jika password lama salah
                            textLayoutPasswordLama.setError("Password lama salah");
                            textLayoutPasswordBaru.setError(null);
                        }
                    });
        } else {
            Toast.makeText(this, "Pengguna belum login", Toast.LENGTH_SHORT).show();
        }
    }
}
