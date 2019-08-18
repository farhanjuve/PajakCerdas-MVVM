package com.example.ryzen.pajakcerdas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.ryzen.pajakcerdas.LokasiObject.EXTRA_ALAMAT;
import static com.example.ryzen.pajakcerdas.LokasiObject.EXTRA_EXTRA;
import static com.example.ryzen.pajakcerdas.LokasiObject.EXTRA_LUASB;
import static com.example.ryzen.pajakcerdas.LokasiObject.EXTRA_LUAST;
import static com.example.ryzen.pajakcerdas.LokasiObject.EXTRA_NILAI2;
import static com.example.ryzen.pajakcerdas.LokasiObject.EXTRA_ZONA;
import static com.example.ryzen.pajakcerdas.MainActivity.EXTRA_TANAH;

public class review extends AppCompatActivity {

    TextView TV1;
    TextView hasil1;
    TextView TV2;
    TextView hasil2;
    TextView TV3;
    TextView hasil3;
    Button btn11, btn22;
    private static final String sembarangwes = "";
    int hargabangunan, score, luasbangunan, hasiltanah;
    double hitungan;
    String skoring, cektanah;
    private DatabaseReference mDatabase;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Locale.setDefault(new Locale("in","ID"));
        Currency c = Currency.getInstance("IDR");

        context = getApplicationContext();
        NumberFormat formatku = NumberFormat.getInstance(Locale.getDefault());

        TV1 = (TextView) findViewById(R.id.text1);
        hasil1 = (TextView) findViewById(R.id.text2);
        Intent x = getIntent();
        cektanah = x.getStringExtra(EXTRA_TANAH);
        Log.d(sembarangwes, "isi RADIO tanah : " + cektanah);
        if (cektanah == null){
            Toast.makeText(this, "Radio Tanah belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(cektanah.equals("1")) {
            hitungan = zona(x.getStringExtra(EXTRA_ZONA)) * x.getIntExtra(EXTRA_LUAST, 0);

            Log.d(sembarangwes, "isi cektanah1 = " + cektanah);
            Log.d(sembarangwes, "Mari berhitung tanahnya 1");
        } else if (cektanah.equals("2") || cektanah.equals("3")){
            skoring = x.getStringExtra(EXTRA_NILAI2);
            int sskoring = Integer.parseInt(x.getStringExtra(EXTRA_NILAI2));

            Log.d(sembarangwes, "isi RADIO nilai : " + skoring);
            luasbangunan = x.getIntExtra(EXTRA_LUASB, 0);
            switch (sskoring){
                case 3:
                    score = 3;
                    hargabangunan = 6000000;
                    break;
                case 2:
                    score = 2;
                    hargabangunan = 4500000;
                    break;
                case 1:
                    score = 1;
                    hargabangunan = 3000000;
                    break;
                case 10:
                    score = 10;
                    hargabangunan = 356789;
                    break;
            }
            int Aa = sskoring * hargabangunan * luasbangunan;
            double Bb = 0.5 * Aa ;
            double nilaibangunan = Aa - Bb;
            hitungan = (zona(x.getStringExtra(EXTRA_ZONA)) * x.getIntExtra(EXTRA_LUAST, 0)) + nilaibangunan ;

            Log.d(sembarangwes, "isi cektanah 23 = " + cektanah);
            Log.d(sembarangwes, "Mari berhitung tanahnya 23");
            Log.d(sembarangwes, "int Aa = " + sskoring + " * " + hargabangunan + " * " + luasbangunan + " = " + Aa);
            Log.d(sembarangwes, "double Bb = " + "0.5" + " * " + Aa + " = " + Bb);
            Log.d(sembarangwes, "double nilaibangunan = " + Aa + " - " + Bb + " = " + nilaibangunan);
            Log.d(sembarangwes, "double hitungan = " + zona(x.getStringExtra(EXTRA_ZONA)) + " * " + x.getIntExtra(EXTRA_LUAST, 0) + " + " + nilaibangunan + " = " + hitungan);
        }
        else {
            Toast.makeText(this, "tidak terdeteksi", Toast.LENGTH_SHORT).show();
        }
        hasil1.setText("Rp. " + formatku.format(hitungan));

        TV2 = (TextView) findViewById(R.id.text3);
        hasil2 = (TextView) findViewById(R.id.text4);
        double njkp = hitungan * 0.2;
        hasil2.setText("Rp. " + formatku.format(njkp));

        TV3 = (TextView) findViewById(R.id.text5);
        hasil3 = (TextView) findViewById(R.id.text6);
        double pbb = njkp * 0.005;
        hasil3.setText("Rp. " + formatku.format(pbb));

        Log.d(sembarangwes, "isi alamat : " + x.getStringExtra(EXTRA_ALAMAT).toString());
        Log.d(sembarangwes, "isi luast : " + x.getIntExtra(EXTRA_LUAST, 0));
        Log.d(sembarangwes, "isi luasb : " + x.getIntExtra(EXTRA_LUASB, 0));
        Log.d(sembarangwes, "isi zona : " + x.getStringExtra(EXTRA_ZONA));
        Log.d(sembarangwes, "isi hitungan : " + formatku.format(hitungan));
        Log.d(sembarangwes, "isi njkp : " + formatku.format(njkp));
        Log.d(sembarangwes, "isi pbb : " + formatku.format(pbb));

        btn11 = findViewById(R.id.btn1);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_pindah();
            }
        });
        btn22 = findViewById(R.id.btn2);
        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_database(x.getStringExtra(EXTRA_ALAMAT).toString(), hitungan, njkp, pbb);
            }
        });
    }

    public void do_pindah(){
        final Intent yy = new Intent("com.example.ryzen.pajakcerdas.MainActivity");
        startActivity(yy);
    }
    public void do_database(String alamat, double harga, double njkp, double pbb){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("dataObjek").push().getKey();

        HashMap<String, Object> obyekpajak = new HashMap<>();
        obyekpajak.put("alamat", alamat);
        obyekpajak.put("harga", harga);
        obyekpajak.put("njkp", njkp);
        obyekpajak.put("pbb", pbb);

        Map<String, Object> wadah = new HashMap<>();
        wadah.put("/dataObjek/" + key, obyekpajak);

        mDatabase.updateChildren(wadah);

        Intent aa = new Intent("com.example.ryzen.pajakcerdas.ViewDatabase");
        startActivity(aa);
    }
    public static int zona(String kode){
        int nilai = 0;
        switch (kode){
            case "AA":
                nilai = 5285700;
                break;
            case "AB":
                nilai = 5404000;
                break;
            case "AC":
                nilai = 14425000;
                break;
            case "AD":
                nilai = 12166700;
                break;
            case "AE":
                nilai = 8450000;
                break;
            case "AF":
                nilai = 5766700;
                break;
            case "AG":
                nilai = 2425000;
                break;
            case "AH":
                nilai = 3401600;
                break;
            case "AI":
                nilai = 4204700;
                break;
            case "AJ":
                nilai = 2742900;
                break;
            case "AK":
                nilai = 3333300;
                break;
            case "AL":
                nilai = 4847900;
                break;
            case "AM":
                nilai = 4341700;
                break;
            case "AN":
                nilai = 4275000;
                break;
            case "AO":
                nilai = 3098800;
                break;
            case "AP":
                nilai = 1639000;
                break;
            case "AQ":
                nilai = 4589000;
                break;
            case "AR":
                nilai = 2633300;
                break;
            case "BA":
                nilai = 7166700;
                break;
            case "BB":
                nilai = 11071500;
                break;
            case "BC":
                nilai = 14616900;
                break;
            case "BD":
                nilai = 14890400;
                break;
            case "BE":
                nilai = 3238500;
                break;
            case "BF":
                nilai = 2535500;
                break;
            case "BG":
                nilai = 12248900;
                break;
            case "BH":
                nilai = 7658400;
                break;
            case "BI":
                nilai = 3061800;
                break;
            case "CA":
                nilai = 2386200;
                break;
            case "CB":
                nilai = 2656900;
                break;
            case "CC":
                nilai = 8338400;
                break;
            case "CD":
                nilai = 2743000;
                break;
            case "CE":
                nilai = 1791400;
                break;
            case "CF":
                nilai = 1990400;
                break;
            case "CG":
                nilai = 2290500;
                break;
            case "CH":
                nilai = 4225000;
                break;
            case "CI":
                nilai = 1068400;
                break;
            case "CJ":
                nilai = 5915600;
                break;
            case "CK":
                nilai = 4319200;
                break;
            case "DA":
                nilai = 6000000;
                break;
            case "DB":
                nilai = 1844100;
                break;
            case "DC":
                nilai = 2055100;
                break;
            case "DD":
                nilai = 2189600;
                break;
            case "DE":
                nilai = 6469900;
                break;
            case "DF":
                nilai = 4907900;
                break;
            case "DG":
                nilai = 1734200;
                break;
            case "DH":
                nilai = 3137400;
                break;
            case "DI":
                nilai = 2817900;
                break;
            case "DJ":
                nilai = 4458400;
                break;
            case "DK":
                nilai = 4461300;
                break;
            case "EA":
                nilai = 3200400;
                break;
            case "EB":
                nilai = 2637500;
                break;
            case "EC":
                nilai = 1742400;
                break;
            case "ED":
                nilai = 1741400;
                break;
            case "EE":
                nilai = 1939100;
                break;
            case "EF":
                nilai = 2535500;
                break;
            case "EG":
                nilai = 5054000;
                break;
            case "EH":
                nilai = 2597400;
                break;
            case "EI":
                nilai = 2734300;
                break;
            case "EJ":
                nilai = 2884700;
                break;
            case "EK":
                nilai = 3081000;
                break;
            case "EL":
                nilai = 7400300;
                break;
            case "EM":
                nilai = 3385900;
                break;
            case "EN":
                nilai = 3037000;
                break;
            case "EO":
                nilai = 1879700;
                break;
            case "FA":
                nilai = 1768400;
                break;
            case "FB":
                nilai = 1151700;
                break;
            case "FC":
                nilai = 685200;
                break;
            case "FD":
                nilai = 1070600;
                break;
            case "FE":
                nilai = 2816700;
                break;
            case "FF":
                nilai = 1858300;
                break;
            case "FG":
                nilai = 2016300;
                break;
            case "FH":
                nilai = 4194800;
                break;
            case "FI":
                nilai = 3138200;
                break;
            case "FJ":
                nilai = 3101100;
                break;
            case "FK":
                nilai = 2906000;
                break;
            case "FL":
                nilai = 2539600;
                break;
            case "FM":
                nilai = 667900;
                break;
            case "FN":
                nilai = 3485700;
                break;
        }
        return nilai;
    }
}
