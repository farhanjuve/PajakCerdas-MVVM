package com.example.ryzen.pajakcerdas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class home extends AppCompatActivity {
    Button btnDatabase;
    Button y;
    Button z;
    Button xx;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = getApplicationContext();

        xx = findViewById(R.id.btn4);
        xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent("com.example.ryzen.pajakcerdas.MainActivity");
                startActivity(ii);
            }
        });

        btnDatabase = findViewById(R.id.btn1);
        btnDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ee = new Intent("com.example.ryzen.pajakcerdas.ViewDatabase");
                startActivity(ee);
            }
        });
        y = findViewById(R.id.btn2);
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "Fitur belum tersedia", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        z = findViewById(R.id.btn3);
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "Fitur belum tersedia", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
