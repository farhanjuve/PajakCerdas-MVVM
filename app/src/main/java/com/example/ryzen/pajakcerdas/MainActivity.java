package com.example.ryzen.pajakcerdas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryzen.pajakcerdas.tflitecamerademo.Camera2BasicFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.ryzen.pajakcerdas.tflitecamerademo.Camera2BasicFragment.EXTRA_TENSOR;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String EXTRA_JENIS = "com.example.ryzen.pajakcerdas.EXTRA_TEXT";
    public static final String EXTRA_LANTAI = "com.example.ryzen.pajakcerdas.EXTRA_LANTAI";
    public static final String EXTRA_TANAH = "com.example.ryzen.pajakcerdas.EXTRA_TANAH";
    public static final String EXTRA_PAGAR = "com.example.ryzen.pajakcerdas.EXTRA_PAGAR";

    public static final String EXTRA_CARPORT = "com.example.ryzen.pajakcerdas.EXTRA_CARPORT";
    public static final String EXTRA_GARASI = "com.example.ryzen.pajakcerdas.EXTRA_GARASI";
    public static final String EXTRA_TAMAN = "com.example.ryzen.pajakcerdas.EXTRA_TAMAN";
    public static final String EXTRA_TAMPAK = "com.example.ryzen.pajakcerdas.EXTRA_TAMPAK";

    public static final String EXTRA_LINGKUNGAN = "com.example.ryzen.pajakcerdas.EXTRA_LINGKUNGAN";
    public static final String EXTRA_KONSTRUKSI = "com.example.ryzen.pajakcerdas.EXTRA_KONSTRUKSI";
    public static final String EXTRA_NILAI = "com.example.ryzen.pajakcerdas.EXTRA_NILAI";
    private static final String sembarangwes = "";

//    ImageView imgView;
    Button btnCamera;
    Button nextPage;
    Spinner jbangun;
    EditText lebarrb;
    TextView tensorr;
    ImageView imgView;

    String record= "";
    String posisi= "";
    String lantai= "";
    String tanah= "";

    String pagar= "";
    String carport= "";
    String garasi= "";
    String taman= "";

    String tampak= "";
    String tampakTensor = "";
    String lingkungan= "";
    String konstruksi= "";

    String lebarbeneran="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.btnCamera);
        nextPage = findViewById(R.id.nextpage);
        imgView = findViewById(R.id.previewThumbnail);
        lebarrb = findViewById(R.id.lebarb);
        tensorr = findViewById(R.id.hasiltensorflow);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("jobs").push().getKey();
        DatabaseReference myRef = database.getReference().child("dataObjek");

        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(sembarangwes, "Value is: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(sembarangwes, "Failed to read value.", error.toException());
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCam();
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inipindah();
            }
        });
        jbangun = (Spinner) findViewById(R.id.jbangunan);
        ArrayAdapter<CharSequence> adapter14 = ArrayAdapter.createFromResource(this,
                R.array.jenis_bangunan, android.R.layout.simple_spinner_item);
        adapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jbangun.setAdapter(adapter14);
        jbangun.setOnItemSelectedListener(this);

        Intent x = getIntent();
        String tensor = x.getStringExtra(EXTRA_TENSOR);
        if (tensor != null) {
            Log.d(sembarangwes, "onCreate: " + tensor);
            switch (tensor){
                case "tampak standar" :
                    tampakTensor = "1";
                    Toast.makeText(this, "Terpilih Rumah Standar", Toast.LENGTH_SHORT).show();
                    break;
                case "tampak cukup" :
                    tampakTensor = "2";
                    Toast.makeText(this, "Terpilih Rumah Cukup", Toast.LENGTH_SHORT).show();
                    break;
                case "tampak mewah" :
                    tampakTensor = "3";
                    Toast.makeText(this, "Terpilih Rumah Mewah", Toast.LENGTH_SHORT).show();
                    break;
                case "tanah kosong" :
                    tanah = "1";
                    Intent xxx = new Intent("com.example.ryzen.pajakcerdas.LokasiObject");
                    xxx.putExtra(EXTRA_TANAH, tanah);
                    startActivity(xxx);
                    Toast.makeText(this, "Tidak perlu mengisi LUAS BANGUNAN", Toast.LENGTH_LONG).show();
                    break;
            }
            tensorr.setText("Terdeteksi berkategori " +tensor);

        } else {
            Toast.makeText(this, "Selamat datang", Toast.LENGTH_SHORT).show();
        }
        if(getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imgView.setImageBitmap(b);

        }
    }

    public void openCam() {
        Intent intent = new Intent("com.example.ryzen.pajakcerdas.tflitecamerademo.CameraActivity");
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        posisi = parent.getItemAtPosition(position).toString();
        switch (position){
            case 0:
                record = "Perumahan";
                break;

            case 1:
                record = "Kantor/tempat usaha";
                break;

            case 2:
                record = "Pabrik";
                break;

            case 3:
                record = "Toko/Apotik/Rumah sakit/Klinik";
                break;

            case 4:
                record = "Gudang";
                break;

            case 5:
                record = "Gedung Pemerintahan";
                break;

            case 6:
                record = "Apartemen";
                break;

            case 7:
                record = "Bangunan parkir";
                break;

            case 8:
                record = "Gedung sekolah";
                break;

            case 9:
                record = "Pompa bensin";
                break;

            case 10:
                record = "Tangki Minyak";
                break;
        }
    }

    public void radioLantai(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_lantai_1:
                if(checked)
                    lantai = "1";
                break;
            case R.id.r_lantai_2:
                if(checked)
                    lantai = "2";
                break;
            case R.id.r_lantai_3:
                if(checked)
                    lantai = "3";
                break;
        } //sudah benar
    }

    public void radioTanah(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_tanah_2:
                if(checked)
                    tanah = "2";
                break;
            case R.id.r_tanah_3:
                if(checked)
                    tanah = "3";
                break;
        }
    }

    public void radioPagar(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_pagar_1:
                if(checked)
                    pagar = "2";
                break;
            case R.id.r_pagar_2:
                if(checked)
                    pagar = "1";
                break;
        } //sudah benar
    }

    public void radioCarport(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_carport_1:
                if(checked)
                    carport = "2";
                break;
            case R.id.r_carport_2:
                if(checked)
                    carport = "1";
                break;
        } //sudah benar
    }

    public void radioGarasi(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_garasi_1:
                if(checked)
                    garasi = "2";
                break;
            case R.id.r_garasi_2:
                if(checked)
                    garasi = "1";
                break;
        } //sudah benar
    }

    public void radioTaman(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_taman_1:
                if(checked)
                    taman = "2";
                break;
            case R.id.r_taman_2:
                if(checked)
                    taman = "1";
                break;
        } //sudah benar
    }

    public void radioLingkungan(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_jalan_1:
                if(checked)
                    lingkungan = "1";
                break;
            case R.id.r_jalan_2:
                if(checked)
                    lingkungan = "2";
                break;
            case R.id.r_jalan_3:
                if(checked)
                    lingkungan = "3";
                break;
        } //sudah benar
    }

    public void radioKonstruksi(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.r_konstruksi_1:
                if(checked)
                    konstruksi = "1";
                break;
            case R.id.r_konstruksi_2:
                if(checked)
                    konstruksi = "2";
                break;
        } //sudah benar
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void inipindah(){
        Intent intent = new Intent("com.example.ryzen.pajakcerdas.LokasiObject");

        if(tanah.equals("") || lantai.equals("") || konstruksi.equals("") || pagar.equals("") || carport.equals("") || garasi.equals("") || taman.equals("") || lingkungan.equals("") || tampakTensor.equals("") || String.valueOf(lebarrb.getText()).equals("")){
            Toast.makeText(this, "Isi dulu datanya", Toast.LENGTH_SHORT).show();
        } else {
            int lebarr = Integer.parseInt(String.valueOf(lebarrb.getText()));

            Log.d(sembarangwes, "hasil lebarr : " + lebarr);

            if (lebarr < 7) {
                lebarbeneran = "1";
            } else if(7 <= lebarr && lebarr <= 10){
                lebarbeneran = "2";
            } else {
                lebarbeneran = "3";
            }

            String hasilneural = neural(lantai, konstruksi, pagar, carport, garasi, taman, lingkungan, tampakTensor, lebarbeneran);

            intent.putExtra(EXTRA_JENIS, posisi);
            intent.putExtra(EXTRA_LANTAI, lantai);
            intent.putExtra(EXTRA_TANAH, tanah);
            intent.putExtra(EXTRA_PAGAR, pagar);

            intent.putExtra(EXTRA_CARPORT, carport);
            intent.putExtra(EXTRA_GARASI, garasi);
            intent.putExtra(EXTRA_TAMAN, taman);
            intent.putExtra(EXTRA_TAMPAK, tampakTensor);

            intent.putExtra(EXTRA_LINGKUNGAN, lingkungan);
            intent.putExtra(EXTRA_KONSTRUKSI, konstruksi);
            intent.putExtra(EXTRA_NILAI, hasilneural);

            Log.d(sembarangwes, "hasil lantai : " + lantai);
            Log.d(sembarangwes, "hasil konstruksi : " + konstruksi);
            Log.d(sembarangwes, "hasil pagar : " + pagar);
            Log.d(sembarangwes, "hasil carport : " + carport);
            Log.d(sembarangwes, "hasil garasi : " + garasi);
            Log.d(sembarangwes, "hasil taman : " + taman);
            Log.d(sembarangwes, "hasil lingkungan : " + lingkungan);
            Log.d(sembarangwes, "hasil tampak Tensor : " + tampakTensor);
            Log.d(sembarangwes, "hasil lebarbeneran : " + lebarbeneran);
            Log.d(sembarangwes, "hasil neural : " + hasilneural);

            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imgView.setImageBitmap(b);
        }
        else {
            Toast.makeText(this, "Kamera gagal terbuka", Toast.LENGTH_SHORT).show();
        }
    }

    public String neural(String a, String b, String c, String d, String e, String f, String g, String h, String i){
        String hasil = "";
        /* 1 */if(a.equals("3") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("3") && i.equals("3"))
        { hasil = "3"; }
        /* 2 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("2"))
        { hasil = "3"; }
        /* 3 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("3") && h.equals("2") && i.equals("3"))
        { hasil = "3"; }
        /* 4 */else if(a.equals("3") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("3") && h.equals("3") && i.equals("3"))
        { hasil = "3"; }
        /* 5 */else if(a.equals("3") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("3"))
        { hasil = "3"; }
        /* 6 */else if(a.equals("3") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("3") && h.equals("2") && i.equals("3"))
        { hasil = "3"; }
        /* 7 */else if(a.equals("2") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("3") && i.equals("2"))
        { hasil = "3"; }
        /* 8 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("3") && h.equals("2") && i.equals("3"))
        { hasil = "3"; }
        /* 9 */else if(a.equals("2") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("3"))
        { hasil = "3"; }
        /* 10 */else if(a.equals("3") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("3") && h.equals("2") && i.equals("2"))
        { hasil = "3"; }

        /* 11 */else if(a.equals("3") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("3") && h.equals("3") && i.equals("2"))
        { hasil = "2"; }
        /* 12 */else if(a.equals("1") && b.equals("1") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("3") && h.equals("3") && i.equals("2"))
        { hasil = "2"; }
        /* 13 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("1") && e.equals("2") && f.equals("1") && g.equals("3") && h.equals("2") && i.equals("2"))
        { hasil = "2"; }
        /* 14 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("1"))
        { hasil = "2"; }
        /* 15 */else if(a.equals("1") && b.equals("1") && c.equals("2") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("3") && h.equals("3") && i.equals("3"))
        { hasil = "2"; }
        /* 16 */else if(a.equals("2") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("2"))
        { hasil = "2"; }
        /* 17 */else if(a.equals("1") && b.equals("2") && c.equals("1") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("3"))
        { hasil = "2"; }
        /* 18 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("3") && h.equals("2") && i.equals("2"))
        { hasil = "2"; }
        /* 19 */else if(a.equals("2") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("3") && i.equals("3"))
        { hasil = "2"; }
        /* 20 */else if(a.equals("1") && b.equals("2") && c.equals("2") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("3") && i.equals("3"))
        { hasil = "2"; }

        /* 21 */else if(a.equals("1") && b.equals("1") && c.equals("2") && d.equals("1") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("2"))
        { hasil = "1"; }
        /* 22 */else if(a.equals("1") && b.equals("1") && c.equals("1") && d.equals("1") && e.equals("2") && f.equals("2") && g.equals("1") && h.equals("1") && i.equals("2"))
        { hasil = "1"; }
        /* 23 */else if(a.equals("1") && b.equals("1") && c.equals("1") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("1"))
        { hasil = "1"; }
        /* 24 */else if(a.equals("1") && b.equals("1") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("1") && h.equals("1") && i.equals("1"))
        { hasil = "1"; }
        /* 25 */else if(a.equals("1") && b.equals("1") && c.equals("1") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("3") && h.equals("2") && i.equals("1"))
        { hasil = "1"; }
        /* 26 */else if(a.equals("1") && b.equals("1") && c.equals("1") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("1") && h.equals("1") && i.equals("1"))
        { hasil = "1"; }
        /* 27 */else if(a.equals("1") && b.equals("1") && c.equals("1") && d.equals("1") && e.equals("1") && f.equals("2") && g.equals("1") && h.equals("2") && i.equals("2"))
        { hasil = "1"; }
        /* 28 */else if(a.equals("1") && b.equals("1") && c.equals("2") && d.equals("2") && e.equals("1") && f.equals("2") && g.equals("2") && h.equals("1") && i.equals("1"))
        { hasil = "1"; }
        /* 29 */else if(a.equals("1") && b.equals("1") && c.equals("2") && d.equals("2") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("2") && i.equals("2"))
        { hasil = "1"; }
        /* 30 */else if(a.equals("1") && b.equals("1") && c.equals("1") && d.equals("1") && e.equals("2") && f.equals("2") && g.equals("2") && h.equals("1") && i.equals("1"))
        { hasil = "1"; }
        else {
            Toast.makeText(this, "Tidak ada dalam data training, skor otomatis : 3", Toast.LENGTH_SHORT).show();
            hasil = "3";
        }
        return hasil;
    }

}
