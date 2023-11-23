package com.example.aduraapp;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MedisCreateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediscreate);

    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void SendData(View view) {
        Intent intent = new Intent(this, MedisDetailRiwayatActivity.class);
        startActivity(intent);
    }
}
