package com.example.ryzen.pajakcerdas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ryzen.pajakcerdas.MainActivity.EXTRA_JENIS;
import static com.example.ryzen.pajakcerdas.MainActivity.EXTRA_LANTAI;
import static com.example.ryzen.pajakcerdas.MainActivity.EXTRA_NILAI;
import static com.example.ryzen.pajakcerdas.MainActivity.EXTRA_TANAH;

public class LokasiObject extends AppCompatActivity {

    public static final String EXTRA_ALAMAT = "com.example.ryzen.pajakcerdas.EXTRA_ALAMAT";
    public static final String EXTRA_LUAST = "com.example.ryzen.pajakcerdas.EXTRA_LUAST";
    public static final String EXTRA_LUASB = "com.example.ryzen.pajakcerdas.EXTRA_LUASB";
    public static final String EXTRA_ZONA = "com.example.ryzen.pajakcerdas.EXTRA_ZONA";
    public static final String EXTRA_EXTRA = "com.example.ryzen.pajakcerdas.EXTRA_EXTRA";
    public static final String EXTRA_NILAI2 = "com.example.ryzen.pajakcerdas.EXTRA_EXTRA";

    Button btnMaps;
    Button btnsubmit;
    TextView result;
    EditText luass;
    EditText luasb;
    EditText zona;
    String hasilnya1, hasilnya2;

    int hasiltanah, hasilbangunan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_object);

        btnMaps = findViewById(R.id.carialamat);

        btnsubmit = findViewById(R.id.submit);
        result = (TextView) findViewById(R.id.result);

        luass = findViewById(R.id.luastanah);
        if(TextUtils.isEmpty(luass.toString().trim())){
            luass.setError("Mohon diisi");
        }

        luasb = findViewById(R.id.luasbangunan);
        if(TextUtils.isEmpty(luasb.toString().trim())){
            luasb.setError("Mohon diisi");
        }
        Intent intent2 = getIntent();
        hasilnya1 = intent2.getStringExtra(EXTRA_TANAH);
        hasilnya2 = intent2.getStringExtra(EXTRA_NILAI);

        zona = findViewById(R.id.zonaa);

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.ryzen.pajakcerdas.Lokasi");
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });

        final String valueintent = getIntent().getStringExtra("alamat");
        result.setText(valueintent);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inipindah2();
            }
        });
    }

    public void inipindah2(){
        Intent x = new Intent("com.example.ryzen.pajakcerdas.review");

        if (luass.getText().toString().matches("") || zona.getText().toString().matches("")) {
            Toast.makeText(this, "Isi dulu formnya", Toast.LENGTH_SHORT).show();
        } else {
            hasiltanah = Integer.parseInt(luass.getText().toString());
            hasilbangunan = Integer.parseInt(luasb.getText().toString());

            x.putExtra(EXTRA_ALAMAT, result.getText());
            x.putExtra(EXTRA_LUAST, hasiltanah);
            x.putExtra(EXTRA_LUASB, hasilbangunan);
            x.putExtra(EXTRA_ZONA, zona.getText().toString().toUpperCase());
            x.putExtra(EXTRA_EXTRA, hasilnya1);
            x.putExtra(EXTRA_NILAI2, hasilnya2);
            x.putExtras(getIntent());

            startActivity(x);
        }
    }
}
