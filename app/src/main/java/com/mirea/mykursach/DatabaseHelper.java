package com.mirea.mykursach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(username TEXT primary key, password TEXT, usertype TEXT, createDate TEXT)");
        db.execSQL("Create table medicament(medName text primary key, manufacturerName text, expirationDate text, compositionMed text, recommendationsForUse text, contraindicationsForUse text, addedBy text, diseaseTag text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    public boolean insert(String username, String usertype, String currentData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("usertype", usertype);
        contentValues.put("createDate", currentData);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public Boolean checkLogin(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public Boolean checkLoginPass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public Boolean checkAdmin(String usertype) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (usertype == "admin") {
            return true;
        } else return false;
    }

    public void onUpgradeMeds(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists medicament");
    }

    public boolean insertMeds(String MedName, String ManufacturerName, String ExpirationDate, String CompositionMed, String RecommendationsForUse, String ContraindicationsForUse, String AddedBy, String DiseaseTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("medName", MedName);
        contentValues.put("manufacturerName", ManufacturerName);
        contentValues.put("expirationDate", ExpirationDate);
        contentValues.put("compositionMed", CompositionMed);
        contentValues.put("recommendationsForUse", RecommendationsForUse);
        contentValues.put("contraindicationsForUse", ContraindicationsForUse);
        contentValues.put("addedBy", AddedBy);
        contentValues.put("diseaseTag", DiseaseTag);
        long ins = db.insert("medicament", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public Cursor getDataCursor(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username ='" + username + "'", null);
            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateMed(String medName, String manufacturerName, String expirationDate, String compositionMed, String recommendationsForUse, String contraindicationsForUse, String diseaseTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("medName", medName);
            values.put("manufacturerName", manufacturerName);
            values.put("expirationDate", expirationDate);
            values.put("compositionMed", compositionMed);
            values.put("recommendationsForUse", recommendationsForUse);
            values.put("contraindicationsForUse", contraindicationsForUse);
            values.put("diseaseTag", diseaseTag);
            db.update("medicament", values, "medName=?", new String[]{medName});
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            SQLiteStatement stmt = db.compileStatement("UPDATE user SET password =? WHERE username = ?");
            stmt.bindString(1, password);
            stmt.bindString(2, username);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getInfoAboutMed(String medName) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM medicament WHERE medName ='" + medName + "'", null);
            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor getAllMedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from medicament", null);
        return cursor;
    }

    public Cursor getByTag(String tags) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select medName from medicament where diseaseTag like '%" + tags + "%'", null);
        return cursor;
    }

    public Boolean deleteMed(String medName) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete("medicament", "medName=?", new String[]{medName});
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
