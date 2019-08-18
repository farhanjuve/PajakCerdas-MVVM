package com.example.ryzen.pajakcerdas;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class ViewDatabase extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private ArrayList<String> mUsername = new ArrayList<>();

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);
        mListView = findViewById(R.id.listv);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dataObjek");

        ArrayAdapter<String> arrayku = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsername);

        mListView.setAdapter(arrayku);

        NumberFormat formatku = NumberFormat.getInstance(Locale.getDefault());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String alamat = ds.child("alamat").getValue(String.class);
                    long harga = (long) ds.child("harga").getValue();
                    long njkp = (long) ds.child("njkp").getValue();
                    Integer pbb = Integer.parseInt(ds.child("pbb").getValue()+"");
                    Log.d("TAG", alamat + " / " + harga);
                    mUsername.add("Alamatnya : " + alamat + "\n" + "Harganya : Rp. " + formatku.format(harga)+ "\n" + "NJKP nya : Rp. " + formatku.format(njkp) + "\n" + "PBB nya : Rp. " + formatku.format(pbb));
                    arrayku.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
