package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EasterEgg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easteregg);
        getSupportActionBar().hide();
    }
}