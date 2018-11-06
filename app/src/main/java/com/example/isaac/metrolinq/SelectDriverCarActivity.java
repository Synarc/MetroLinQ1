package com.example.isaac.metrolinq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectDriverCarActivity extends AppCompatActivity {

    private EditText plateNumber, driverName;
    private Button confirmDriver;
    private DatabaseReference mDatabase;
    private DatabaseReference clientNameDB, newDB;
    private String positionNumber, keyList = "";
    private int iterateNumber, posnum;



    /*
    *
    * trips requesed
    *
    *
    * trips pending
    *
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_driver_car);

        plateNumber =  findViewById(R.id.plateNumber);
        driverName = findViewById(R.id.driverName);

        iterateNumber = 0;
        confirmDriver = findViewById(R.id.confrimDriver);
        clientNameDB = FirebaseDatabase.getInstance().getReference("Scheduled Info");
        mDatabase = FirebaseDatabase.getInstance().getReference("Journey Info");

        Intent intent =getIntent();
        positionNumber =intent.getStringExtra("POSITION");
        posnum = intent.getIntExtra("POSITION_NUMBER", 0);


        confirmDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterateNumber =0;

                clientNameDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                                if (posnum == iterateNumber) {

                                    JourneyInfo journeyInfo = new JourneyInfo(
                                            postSnapshot.child("oriLat").getValue(),
                                            postSnapshot.child("oriLon").getValue(),
                                            postSnapshot.child("desLat").getValue(),
                                            postSnapshot.child("desLon").getValue(),
                                            postSnapshot.child("fare").getValue(),
                                            postSnapshot.child("min").getValue(),
                                            postSnapshot.child("hour").getValue(),
                                            postSnapshot.child("day").getValue(),
                                            postSnapshot.child("month").getValue(),
                                            postSnapshot.child("year").getValue(),
                                            driverName.getText().toString(),
                                            plateNumber.getText().toString(),
                                            postSnapshot.child("clientName").getValue(),
                                            postSnapshot.child("payType").getValue(),
                                            postSnapshot.child("currentDate").getValue()
                                    );


                                    String uploadId = mDatabase.push().getKey();
                                    mDatabase.child(uploadId).setValue(journeyInfo);

                                    clientNameDB.child(postSnapshot.getKey()).removeValue();

                                }
                                iterateNumber++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                Intent intent = new Intent(SelectDriverCarActivity.this, QueueActivity.class);
                startActivity(intent);

            }





        });


    }



}
