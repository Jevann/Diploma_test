package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView profile;
    Button addNewMed;
    DatabaseHelper db;
    String userType;
    Button toTest;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);
        String username = getIntent().getStringExtra("username");
        profile = (ImageView) findViewById(R.id.profile);
        addNewMed = (Button) findViewById(R.id.addNewMed);
        toTest = (Button) findViewById(R.id.toTest);
        logo = (ImageView) findViewById(R.id.logo);

        toTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnterProblem.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        String usertype = getIntent().getStringExtra("usertype");
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        Cursor cursor = db.getDataCursor(username);
        cursor.moveToFirst();
        if (cursor != null) {
            int idUserType = cursor.getColumnIndex("usertype");
            userType = cursor.getString(idUserType);
        }


        addNewMed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (userType.equals("admin")) {
                    Intent intent = new Intent(MainActivity.this, AddNewMed.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "У вас нет прав.", Toast.LENGTH_SHORT).show();
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reference.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }
}
