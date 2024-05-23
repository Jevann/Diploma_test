package com.mirea.mykursach;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    DatabaseHelper db;
    TextView usertype;
    TextView userName;
    TextView createDatee;
    TextView uid;
    EditText newPass;
    ImageView back;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);
        String username = getIntent().getStringExtra("username");
        usertype = (TextView) findViewById(R.id.usertype);
        userName = (TextView) findViewById(R.id.username);
        createDatee = (TextView) findViewById(R.id.createDate);
        back = (ImageView) findViewById(R.id.back);
        Cursor cursor = db.getDataCursor(username);
        cursor.moveToFirst();
        if (cursor != null) {
            int idUserType = cursor.getColumnIndex("usertype");
            int idCreateDate = cursor.getColumnIndex("createDate");
            String userType = cursor.getString(idUserType);
            String createDate = cursor.getString(idCreateDate);
            usertype.setText(userType);
            userName.setText(username);
            createDatee.setText(createDate);
        } else {
            Toast.makeText(getApplicationContext(), "Ошибка! Данные отсутствуют!", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}