package com.example.aduraapp;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class KeamananCreateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamanancreate);

    }
    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}