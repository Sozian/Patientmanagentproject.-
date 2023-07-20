package com.example.mediibase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.UncheckedIOException;

public class MainActivity extends AppCompatActivity {
    EditText medName,medDate,PName;
    Button btnInsert,btnFetch,trigger;
    Spinner timeDaySpinner;
    Switch swtch;
    DataBaseConn dbConnection;                         // to make the connection with db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbConnection = new DataBaseConn(this);          // from db connection to this java file
        setContentView(R.layout.activity_main);

        medName = findViewById(R.id.Medname);
        trigger=findViewById(R.id.Trigger);
        medDate = findViewById(R.id.Date);
        btnInsert = findViewById(R.id.buttonIn);
        btnFetch = findViewById(R.id.buttonFt);
        timeDaySpinner = findViewById(R.id.spinner);
        swtch = findViewById(R.id.toggleswitch);
        PName = findViewById(R.id.PatientName);


        trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!swtch.isChecked()){
                    btnFetch.setVisibility(View.INVISIBLE);
                    medName.setVisibility(View.VISIBLE);
                    btnInsert.setVisibility(View.VISIBLE);
                }
                else {
                    medName.setVisibility(View.INVISIBLE);
                    btnInsert.setVisibility(View.INVISIBLE);
                    btnFetch.setVisibility(View.VISIBLE);
                }


            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = medName.getText().toString();
                String date = medDate.getText().toString();
                String time = timeDaySpinner.getSelectedItem().toString();
                String patientName = PName.getText().toString();  // get the patient name from the EditText field
                boolean insert = dbConnection.insertvalues(name, date, time, patientName);  // pass the patient name to the insertvalues method
                if (insert) {
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    medName.setText(null);
                    PName.setText(null);  // clear the patient name EditText field
                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String date = medDate.getText().toString();
                String time = timeDaySpinner.getSelectedItem().toString();
                String patientName = PName.getText().toString();  // get the patient name
                String med = "";

                Cursor cursor = dbConnection.Fetchdata(date, time, patientName);  // pass the patient name to the Fetchdata method

                if (cursor.moveToFirst()) {
                    do {
                        med += cursor.getString(cursor.getColumnIndex("medicineName")) + "\n";
                    } while (cursor.moveToNext());
                } else {
                    med = "No data found";
                }

                Toast.makeText(getApplicationContext(), med, Toast.LENGTH_LONG).show();
            }
        });


    }
}