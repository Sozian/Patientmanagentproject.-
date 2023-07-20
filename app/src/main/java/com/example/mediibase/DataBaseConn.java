package com.example.mediibase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseConn extends SQLiteOpenHelper {        // adding the sql helper class , then we overide the function i.e helper by cntrl+o
    public DataBaseConn(Context context) {
        super(context, "MedicineDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table MDTable(medicineName TEXT primary key, date TEXT, time TEXT, patientName TEXT)");  // adding the patientName column
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists MDTable");


    }

    public boolean insertvalues(String medname, String meddate, String medtime, String patientName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("medicineName", medname);
        contentValues.put("date", meddate);
        contentValues.put("time", medtime);
        contentValues.put("patientName", patientName);  // adding the patient name to the ContentValues

        long res = database.insert("MDTable", null, contentValues);

        if (res == -1)
            return false;
        else
            return true;
    }


    public Cursor Fetchdata(String date, String time, String patientName) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM MDTable WHERE date='" + date + "' AND time='" + time + "' AND patientName='" + patientName + "'", null);
        return cursor;
    }


}


