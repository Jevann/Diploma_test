package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MedicamentFind extends AppCompatActivity {

    DatabaseHelper db;
    ImageView profile;
    String userType;
    TextView medNameText;
    TextView manufacturerName;
    TextView expirationDate;
    TextView compositionMed;
    TextView recommendationsForUse;
    TextView contraindicationsForUse;
    TextView addedBy;
    TextView diseaseTag;
    AutoCompleteTextView enterMedName;
    Button check;
    ArrayList<String> nameMeds = new ArrayList<String>();
    Set<String> set = new HashSet<String>();
    ImageView back;
    Button editMed;
    Button delMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_find);
        String username = getIntent().getStringExtra("username");
        db = new DatabaseHelper(this);
        profile = (ImageView) findViewById(R.id.profile);
        medNameText = (TextView) findViewById(R.id.medName);
        manufacturerName = (TextView) findViewById(R.id.manufacturerName);
        expirationDate = (TextView) findViewById(R.id.expirationDate);
        compositionMed = (TextView) findViewById(R.id.compositionMed);
        recommendationsForUse = (TextView) findViewById(R.id.recommendationsForUse);
        contraindicationsForUse = (TextView) findViewById(R.id.contraindicationsForUse);
        addedBy = (TextView) findViewById(R.id.addedBy);
        diseaseTag = (TextView) findViewById(R.id.diseaseTag);
        enterMedName = (AutoCompleteTextView) findViewById(R.id.enterMedName);
        check = (Button) findViewById(R.id.check);
        back = (ImageView) findViewById(R.id.back);
        editMed = (Button) findViewById(R.id.editMed);
        delMed = (Button) findViewById(R.id.deleteMed);


        Cursor cursorNames = db.getAllMedNames();
        if (cursorNames.moveToFirst()) {
            while (!cursorNames.isAfterLast()) {
                String name = cursorNames.getString(cursorNames.getColumnIndex("medName"));
                ArrayList aList = new ArrayList(Arrays.asList(name.split("\\s*,\\s*")));
                for (int i = 0; i < aList.size(); i++) {
                    String abc = (String) aList.get(i);
                    set.add(abc);
                }
                nameMeds.clear();
                nameMeds.addAll(set);
                cursorNames.moveToNext();
            }
        }
        enterMedName.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, nameMeds));

        Cursor cursor = db.getDataCursor(username);
        cursor.moveToFirst();
        int idUserType = cursor.getColumnIndex("usertype");
        userType = cursor.getString(idUserType);

        if (userType.equals("admin")) {
            editMed.setVisibility(View.VISIBLE);
            delMed.setVisibility(View.VISIBLE);
        } else {
            editMed.setVisibility(View.GONE);
            delMed.setVisibility(View.GONE);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentFind.this, Profile.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        editMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentFind.this, EditMed.class);
                intent.putExtra("medName", enterMedName.getText().toString());
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String medName = enterMedName.getText().toString();
                Cursor cursorTwo = db.getInfoAboutMed(medName);
                if (cursorTwo != null && cursorTwo.moveToFirst()) {
                    int idMedName = cursorTwo.getColumnIndex("medName");
                    int idManufacturerName = cursorTwo.getColumnIndex("manufacturerName");
                    int idExpirationDate = cursorTwo.getColumnIndex("expirationDate");
                    int idCompositionMed = cursorTwo.getColumnIndex("compositionMed");
                    int idRecommendationsForUse = cursorTwo.getColumnIndex("recommendationsForUse");
                    int idContraindicationsForUse = cursorTwo.getColumnIndex("contraindicationsForUse");
                    int idAddedBy = cursorTwo.getColumnIndex("addedBy");
                    int idDiseaseTag = cursorTwo.getColumnIndex("diseaseTag");
                    String medNameT = cursorTwo.getString(idMedName);
                    String manufacturerNameT = cursorTwo.getString(idManufacturerName);
                    String expirationDateT = cursorTwo.getString(idExpirationDate);
                    String compositionMedT = cursorTwo.getString(idCompositionMed);
                    String recommendationsForUseT = cursorTwo.getString(idRecommendationsForUse);
                    String contraindicationsForUseT = cursorTwo.getString(idContraindicationsForUse);
                    String addedByT = cursorTwo.getString(idAddedBy);
                    String diseaseTagT = cursorTwo.getString(idDiseaseTag);
                    medNameText.setText(medNameT);
                    manufacturerName.setText(manufacturerNameT);
                    expirationDate.setText(expirationDateT);
                    compositionMed.setText(compositionMedT);
                    recommendationsForUse.setText(recommendationsForUseT);
                    contraindicationsForUse.setText(contraindicationsForUseT);
                    addedBy.setText(addedByT);
                    diseaseTag.setText(diseaseTagT);
                    cursorTwo.close();
                } else {
                    Toast.makeText(getApplicationContext(), "Такого лекарства не найдено.", Toast.LENGTH_SHORT).show();
                }
            }

            ;
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        delMed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String medName = enterMedName.getText().toString();
                Boolean deleted = db.deleteMed(medName);
                if (deleted) {
                    Toast.makeText(getApplicationContext(), "Лекарство удалено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}