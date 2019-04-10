package com.example.n01202172.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class
MyBattery extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    Database mData;
    private TextView time;


    private TextView voltage;


    //private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybattery);

        getDatabase();
        findAllViews();
        showInfo();


       // mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void findAllViews() {
        voltage = findViewById(R.id.textView4);

        time = findViewById(R.id.textView16);
    }

    private void getDatabase(){
        // TODO: Find the refernce form the database.
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(mAuth.getUid()+"/Voltage/data1");
        //  myRef = database.getReference();
    }

    private void showInfo() {
        //mAuth = FirebaseAuth.getInstance();
        //myRef = FirebaseDatabase.getInstance().getReference("");
        //myRef = database.getReference();



        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String realtemp = dataSnapshot.child("temperature").getValue(String.class);
                Database ds = (Database) dataSnapshot.getValue(Database.class);

                voltage.setText("Volt: " + ds.getVolt());
                time.setText(convertTimestamp(ds.getTimestamp()));
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private String convertTimestamp(String timestamp) {

                long yourSeconds = Long.valueOf(timestamp);
                Date mDate = new Date(yourSeconds * 1000);
                DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
                return df.format(mDate);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyBattery.this, "Data unavailable, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
