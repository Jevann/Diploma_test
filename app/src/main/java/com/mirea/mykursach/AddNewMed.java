package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNewMed extends AppCompatActivity {

    DatabaseHelper db;
    Button admin;
    EditText medName;
    EditText manufacturerName;
    EditText expirationDate;
    EditText compositionMed;
    EditText recommendationsForUse;
    EditText contraindicationsForUse;
    EditText diseaseTag;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmed);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);
        medName = (EditText) findViewById(R.id.medName);
        manufacturerName = (EditText) findViewById(R.id.manufacturerName);
        expirationDate = (EditText) findViewById(R.id.expirationDate);
        compositionMed = (EditText) findViewById(R.id.compositionMed);
        recommendationsForUse = (EditText) findViewById(R.id.recommendationsForUse);
        contraindicationsForUse = (EditText) findViewById(R.id.contraindicationsForUse);
        diseaseTag = (EditText) findViewById(R.id.diseaseTag);
        admin = (Button) findViewById(R.id.admin);
        back = (ImageView) findViewById(R.id.back);

        String username = getIntent().getStringExtra("username");
        Cursor cursor = db.getDataCursor(username);
        cursor.moveToFirst();
        int idUserType = cursor.getColumnIndex("usertype");
        String userType = cursor.getString(idUserType);
        if (userType.equals("admin")) {
            admin.setVisibility(View.VISIBLE);
        } else
            admin.setVisibility(View.INVISIBLE);

        admin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String MedName = medName.getText().toString();
                String ManufacturerName = manufacturerName.getText().toString();
                String ExpirationDate = expirationDate.getText().toString();
                String CompositionMed = compositionMed.getText().toString();
                String RecommendationForUse = recommendationsForUse.getText().toString();
                String ContraindicationsForUse = contraindicationsForUse.getText().toString();
                String DiseaseTag = diseaseTag.getText().toString();
                Boolean insertMeds = db.insertMeds(MedName, ManufacturerName, ExpirationDate, CompositionMed, RecommendationForUse, ContraindicationsForUse, username, DiseaseTag);
                if (insertMeds) {
                    Toast.makeText(getApplicationContext(), "Лекарство добавлено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Такое лекарство уже существует", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNewMed.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }
}

