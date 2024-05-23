package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Reference extends AppCompatActivity {

    ImageView back;
    TextView doneBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        back = (ImageView) findViewById(R.id.back);
        getSupportActionBar().hide();
        String username = getIntent().getStringExtra("username");
        doneBy = (TextView) findViewById(R.id.doneBy);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reference.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

        doneBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reference.this, EasterEgg.class);
                startActivity(intent);
            }
        });
    }
}