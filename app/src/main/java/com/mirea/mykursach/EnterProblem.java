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

public class EnterProblem extends AppCompatActivity {

    DatabaseHelper db;
    TextView foundName;
    AutoCompleteTextView enterTag;
    Button doSomething;
    ImageView back;
    ImageView profile;
    String medNameS;
    Button findMed;
    ArrayList<String> nameMeds = new ArrayList<String>();
    Set<String> set = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_problem);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);
        String username = getIntent().getStringExtra("username");
        foundName = (TextView) findViewById(R.id.foundName);
        enterTag = (AutoCompleteTextView) findViewById(R.id.enterTag);
        doSomething = (Button) findViewById(R.id.doSomething);
        back = (ImageView) findViewById(R.id.back);
        profile = (ImageView) findViewById(R.id.profile);
        findMed = (Button) findViewById(R.id.findMed);
        medNameS = "Я думаю вам поможет: \n\n";

        doSomething.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tags = enterTag.getText().toString();
                if (!tags.equals(" ") && !tags.equals(",") && !tags.equals("")) {
                    Cursor cursor = db.getByTag(tags);
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            String medNameT = cursor.getString(cursor.getColumnIndex("medName"));
                            medNameS += (medNameT + "\n\n");
                            foundName.setText(medNameS);
                            cursor.moveToNext();
                        }
                        medNameS = "Я думаю вам поможет: \n\n";
                    } else
                        Toast.makeText(getApplicationContext(), "Такого нет.", Toast.LENGTH_SHORT).show();
                    findMed.setVisibility(View.VISIBLE);
                } else
                    Toast.makeText(getApplicationContext(), "Введите проблему.", Toast.LENGTH_SHORT).show();
            }
        });

        Cursor cursorNames = db.getAllMedNames();
        if (cursorNames.moveToFirst()) {
            while (!cursorNames.isAfterLast()) {
                String name = cursorNames.getString(cursorNames.getColumnIndex("diseaseTag"));
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
        enterTag.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, nameMeds));

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterProblem.this, Profile.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        findMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterProblem.this, MedicamentFind.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });


    }
}