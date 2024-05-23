package com.mirea.mykursach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AuthActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText username;
    EditText password;
    Button login;
    Button register;
    Spinner usertype;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_auth);
        db = new DatabaseHelper(this);
        getSupportActionBar().hide();
        username = (EditText) findViewById(R.id.edit_login);
        password = (EditText) findViewById(R.id.edit_pass);
        usertype = (Spinner) findViewById(R.id.usertype);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                String Usertype = usertype.getSelectedItem().toString();
                Boolean CheckLoginPass = db.checkLoginPass(Username, Password);
                Boolean CheckAdmin = db.checkAdmin(Usertype);
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                firebaseAuth.signInWithEmailAndPassword(Username, Password)
                        .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    intent.putExtra("username", username.getText().toString());
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    String Username = username.getText().toString();
                    String Password = password.getText().toString();
                    String Usertype = usertype.getSelectedItem().toString();
                    DateFormat cT = new SimpleDateFormat("d MMM yyyy, HH:mm");
                    cT.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
                    String currentDate = cT.format(Calendar.getInstance().getTime());
                        if (Username.equals("") || Password.equals("")) {
                        } else {
                    Boolean checkLogin = db.checkLogin(Username);
                    if (checkLogin == true) {
                      Boolean insert = db.insert(Username, Usertype, currentDate);
                     if (insert == true) {
                    }
                     } else {
                     }
        }
                    firebaseAuth.createUserWithEmailAndPassword(Username, Password)
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AuthActivity.this, "Регистрация успешна by Firebase", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                        intent.putExtra("usertype", usertype.getSelectedItem().toString());
                                        intent.putExtra("username", username.getText().toString());
                                    } else {
                                        Toast.makeText(AuthActivity.this, "Ошибка, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
        }
    });
}
}

