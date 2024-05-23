package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditMed extends AppCompatActivity {

    DatabaseHelper db;
    ImageView back;
    Button editMed;
    EditText medName;
    EditText manufacturerName;
    EditText expirationDate;
    EditText compositionMed;
    EditText recommendationsForUse;
    EditText contraindicationsForUse;
    EditText diseaseTag;
    Button find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_med);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);
        back = (ImageView) findViewById(R.id.back);
        editMed = (Button) findViewById(R.id.editMed);
        medName = (EditText) findViewById(R.id.medName);
        manufacturerName = (EditText) findViewById(R.id.manufacturerName);
        expirationDate = (EditText) findViewById(R.id.expirationDate);
        compositionMed = (EditText) findViewById(R.id.compositionMed);
        recommendationsForUse = (EditText) findViewById(R.id.recommendationsForUse);
        contraindicationsForUse = (EditText) findViewById(R.id.contraindicationsForUse);
        diseaseTag = (EditText) findViewById(R.id.diseaseTag);
        find = (Button) findViewById(R.id.findMed);
        String username = getIntent().getStringExtra("username");
        String medNameEdit = getIntent().getStringExtra("medName");
        medName.setText(medNameEdit);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        editMed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String MedName = medName.getText().toString();
                String ManufacturerName = manufacturerName.getText().toString();
                String ExpirationDate = expirationDate.getText().toString();
                String CompositionMed = compositionMed.getText().toString();
                String RecommendationForUse = recommendationsForUse.getText().toString();
                String ContraindicationsForUse = contraindicationsForUse.getText().toString();
                String DiseaseTag = diseaseTag.getText().toString();
                Boolean updated = db.updateMed(MedName, ManufacturerName, ExpirationDate, CompositionMed, RecommendationForUse, ContraindicationsForUse, DiseaseTag);
                if (updated) {
                    Toast.makeText(getApplicationContext(), "Лекарство отредактировано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String MedNameTwo = medName.getText().toString();
                Cursor cursor = db.getInfoAboutMed(MedNameTwo);
                if (cursor != null && cursor.moveToFirst()) {
                    int idMedName = cursor.getColumnIndex("medName");
                    int idManufacturerName = cursor.getColumnIndex("manufacturerName");
                    int idExpirationDate = cursor.getColumnIndex("expirationDate");
                    int idCompositionMed = cursor.getColumnIndex("compositionMed");
                    int idRecommendationsForUse = cursor.getColumnIndex("recommendationsForUse");
                    int idContraindicationsForUse = cursor.getColumnIndex("contraindicationsForUse");
                    int idDiseaseTag = cursor.getColumnIndex("diseaseTag");
                    String medNameT = cursor.getString(idMedName);
                    String manufacturerNameT = cursor.getString(idManufacturerName);
                    String expirationDateT = cursor.getString(idExpirationDate);
                    String compositionMedT = cursor.getString(idCompositionMed);
                    String recommendationsForUseT = cursor.getString(idRecommendationsForUse);
                    String contraindicationsForUseT = cursor.getString(idContraindicationsForUse);
                    String diseaseTagT = cursor.getString(idDiseaseTag);
                    manufacturerName.setText(manufacturerNameT);
                    expirationDate.setText(expirationDateT);
                    compositionMed.setText(compositionMedT);
                    recommendationsForUse.setText(recommendationsForUseT);
                    contraindicationsForUse.setText(contraindicationsForUseT);
                    diseaseTag.setText(diseaseTagT);
                    cursor.close();
                } else {
                    Toast.makeText(getApplicationContext(), "Такого нет.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}