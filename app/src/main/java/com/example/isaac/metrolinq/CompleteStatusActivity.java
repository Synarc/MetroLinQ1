package com.example.isaac.metrolinq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompleteStatusActivity extends AppCompatActivity {


    Button changeTrip, completeTrip;
    private int posnum;
    DatabaseReference mDatabase, mDatabase2;
    private  int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_status);


        changeTrip = findViewById(R.id.changeTrip);
        completeTrip = findViewById(R.id.completeTrip);
        i = 0;

        final Intent intent =getIntent();
        posnum = intent.getIntExtra("POSITION_NUMBER", 0);

        mDatabase = FirebaseDatabase.getInstance().getReference("Journey Info");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Completed Journey");


        completeTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                            if (posnum == i){

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
                                        postSnapshot.child("driver").getValue(),
                                        postSnapshot.child("plateNumber").getValue(),
                                        postSnapshot.child("clientName").getValue(),
                                        postSnapshot.child("payType").getValue(),
                                        postSnapshot.child("currentDate").getValue()
                                );
                                String uploadId = mDatabase2.push().getKey();
                                mDatabase2.child(uploadId).setValue(journeyInfo);

                                mDatabase.child(postSnapshot.getKey()).removeValue();

                                Intent intent1 = new Intent(CompleteStatusActivity.this, CompletedActivity.class);

                                startActivity(intent1);
                            }

                            i++;

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        changeTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                            if (posnum == i){

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
                                        postSnapshot.child("driver").getValue(),
                                        postSnapshot.child("plateNumber").getValue(),
                                        postSnapshot.child("clientName").getValue(),
                                        postSnapshot.child("payType").getValue(),
                                        postSnapshot.child("currentDate").getValue()
                                );
                                String uploadId = mDatabase2.push().getKey();
                                mDatabase2.child(uploadId).setValue(journeyInfo);

                                mDatabase.child(postSnapshot.getKey()).removeValue();

                                Intent intent1 = new Intent(CompleteStatusActivity.this, CompletedActivity.class);

                                startActivity(intent1);
                            }

                            i++;

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
