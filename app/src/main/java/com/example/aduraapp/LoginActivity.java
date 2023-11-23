package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aduraapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText email, editPassword;
    private Button login;

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        email = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        login = findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = editPassword.getText().toString();
                loginUser(txt_email, txt_password);
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "LoginSuccessfull", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

                mainIntent.putExtra("IS_LOGGED_IN", true);
                startActivity(mainIntent);
            }
        });
    }

//    public void onButtonLoginClicked(View view) {
//        //explicit intent
//        String username = binding.editUsername.getText().toString();
//        String password = binding.editPassword.getText().toString();
//
//
//        if(password.equals("123")){
//            Intent mainIntent = new Intent(this, MainActivity.class);
//            mainIntent.putExtra("USERNAME", username);
//            mainIntent.putExtra("IS_LOGGED_IN", true);
//            startActivity(mainIntent);
//        }else{
//            Toast.makeText(this, "Kombinasi Username dan Password Anda Salah!", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void onButtonRegisterClicked(View view) {
        Log.d("LoginActivity", "onButtonRegisterClicked: Navigating to RegisterActivity");
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}