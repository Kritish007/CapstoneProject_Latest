package com.example.n01202172.myapplication;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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

import static com.google.android.gms.flags.FlagSource.G;

public class MyScan extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    Database mData;
    private TextView result;
    private TextView fruitname;
    private TextView time;



    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myscan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = findViewById(R.id.textView6);
                fruitname = findViewById(R.id.textView17);
                time = findViewById(R.id.textView13);
                dataProcess();
            }
        });
    }

    public void dataProcess(){
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(mAuth.getUid()+"/sensordata/data1");
       // myRef = FirebaseDatabase.getInstance().getReference("sensordata/data");
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Database ds = (Database) dataSnapshot.getValue(Database.class);
                String rgb = ds.getRgb();

                String fruit = ds.getFruitname();

                String result1 = rgb.substring(1, rgb.length()-1);
                String[] data = result1.split(", ");
                //System.out.println(data[0]);
                String Red = data[0];
                String Green = data[1];
                String Blue = data[2];

                int RGala = Integer.parseInt(Red);
                int GGala = Integer.parseInt(Green);
                int BGala = Integer.parseInt(Blue);
                int RGreenApple = Integer.parseInt(Red);
                int GGreenApple = Integer.parseInt(Green);
                int BGreenApple = Integer.parseInt(Blue);
                int RStrawberry = Integer.parseInt(Red);
                int GStrawberry = Integer.parseInt(Green);
                int BStrawberry = Integer.parseInt(Blue);
                int RBanana = Integer.parseInt(Red);
                int GBanana = Integer.parseInt(Green);
                int BBanana = Integer.parseInt(Blue);
                int ROrange = Integer.parseInt(Red);
                int GOrange = Integer.parseInt(Green);
                int BOrange = Integer.parseInt(Blue);

//Gala Apple
                if(fruit.equals("Gala Apple")){
                if( RGala >= 159 && RGala <= 232) {
                    if (GGala >= 79 && GGala <= 155) {
                        if (BGala >= 77 && BGala <= 130) {            //Check Red
                            result.setText("Result: Gala Apple is ready");
                        }
                    }

                }

                    else {
                        result.setText("Result: Gala Apple is not ready");
                    }
                }
 // Green Apple
                if(fruit.equals("Green Apple")) {

                    if (RGreenApple >= 125 && RGreenApple <= 200) {            //Check Red
                        if (GGreenApple >= 140 && GGreenApple <= 220) {         //Check Green
                            if (BGreenApple >= 67 && BGreenApple <= 120) {     //Check Blue
                                result.setText("Result: Green Apple is ready");
                            }
                        }

                    } else {
                        result.setText("Result: Green Apple is not ready");
                    }
                }
// Strawberry
                if(fruit.equals("Strawberry")) {
                    if (RStrawberry >= 75 && RStrawberry <= 254) {            //Check Red
                        if (GStrawberry >= 25 && GStrawberry <= 170) {         //Check Green
                            if (BStrawberry >= 25 && BStrawberry <= 130) {     //Check Blue
                                result.setText("Result: Strawberry is ready");
                            }
                        }

                    } else {
                        result.setText("Result: Strawberry is not ready");
                    }
                }
//Banana
                if(fruit.equals("Banana")){
                if( RBanana >= 55 && RBanana <= 251 ) {            //Check Red
                    if ( GBanana >= 35 && GBanana <= 240 ){         //Check Green
                        if ( BBanana >= 25 && BBanana <= 200 ){     //Check Blue
                            result.setText("Result: Banana is ready");
                        }
                    }
                }
                else {
                    result.setText("Result: Banana is not ready");
                }
                }


                if(fruit.equals("Orange")){

                if( ROrange >= 130 && ROrange <= 255 ) {            //Check Red
                    if ( GOrange >= 40 && GOrange <= 212 ){         //Check Green
                        if ( BOrange >= 25 && BOrange <= 150 ){     //Check Blue
                            result.setText("Result: Orange is ready");
                        }
                    }
                }
                else {
                    result.setText("Result: Orange is not ready");
                }
                }

                time.setText("Scan time: " +convertTimestamp(ds.getTimestamp()));
                fruitname.setText("Fruit Name:" + ds.getFruitname());
            }

                private String convertTimestamp(String timestamp) {

                long yourSeconds = Long.valueOf(timestamp);
                Date mDate = new Date(yourSeconds * 1000);
                DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
                return df.format(mDate);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyScan.this, "Data unavailable, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }}

