package com.karaoke.karaokebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirstLaunchActivity extends AppCompatActivity {
    String sharedPrefKey;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        if(!sharedPref.contains(sharedPrefKey)) {
            setContentView(R.layout.activity_first_launch);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
