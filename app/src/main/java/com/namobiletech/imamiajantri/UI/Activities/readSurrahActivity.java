package com.namobiletech.imamiajantri.UI.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.namobiletech.imamiajantri.R;
import com.namobiletech.imamiajantri.Utils.dbUtility.DbHelper;

public class readSurrahActivity extends AppCompatActivity {

    String SURRAHNAME;
    String SURRAHNUMBER;
    String REVELATIONTTYPE;

    //Textviews
    private TextView surrahType;
    private TextView SurrahName;

    //Cursor
    Cursor cursor;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_surrah);

        Intent intent = getIntent();

        SURRAHNUMBER = intent.getStringExtra("surrahNumber");
        SURRAHNAME = intent.getStringExtra("surrahName");
        REVELATIONTTYPE = intent.getStringExtra("type");

        surrahType = (TextView) findViewById(R.id.revelationType_tv);
        SurrahName = (TextView) findViewById(R.id.surrahNameTv);

        surrahType.setText(REVELATIONTTYPE);
        SurrahName.setText(SURRAHNAME);

        dbHelper = new DbHelper(this);

        cursor = dbHelper.returnnumRows(dbHelper, SURRAHNUMBER);
        cursor.moveToNext();

        Toast.makeText(this, "" + cursor.getCount(), Toast.LENGTH_SHORT).show();
        DataFromDB();
    }

    private void DataFromDB() {

    }
}