package com.example.isaac.metrolinq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompletedActivity extends AppCompatActivity implements AdapeterComplete.OnItemClickListener{



    private List<DriverCar> namesFD;
    private DatabaseReference clientNameDB;
    private AdapeterComplete mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDB;
    private int iterate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);


        mRecyclerView = findViewById(R.id.recyclerViewCompletef);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        namesFD = new ArrayList<>();

        mAdapter = new AdapeterComplete(CompletedActivity.this, namesFD);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CompletedActivity.this);

        clientNameDB = FirebaseDatabase.getInstance().getReference("Journey Info");
        mDB = FirebaseDatabase.getInstance().getReference("Journey Info");



        clientNameDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namesFD.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){


                        DriverCar name = new DriverCar(
                                postSnapshot.child("driver").getValue().toString(),
                                postSnapshot.child("plateNumber").getValue().toString(),
                                postSnapshot.child("clientName").getValue().toString());
                        namesFD.add(name);

                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_screen_complete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.queueComplete:
                Intent intent = new Intent(this, QueueActivity.class);
                startActivity(intent);
                break;

            case R.id.mapComplete:
                Intent intent2 = new Intent(this, MapsActivity.class);
                startActivity(intent2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(final int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();



        Intent intent = new Intent(CompletedActivity.this, CompleteStatusActivity.class);
        intent.putExtra("POSITION_NUMBER",position);
        startActivity(intent);


    }

    @Override
    public void onWhateverClick(final int position) {
        Toast.makeText(this, "Whatever Click at position: " + position, Toast.LENGTH_SHORT).show();



        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    if (iterate == position) {
                        JourneyInfo journeyInfo = new JourneyInfo(
                                postSnapshot.child("oriLat").getValue(),
                                postSnapshot.child("oriLon").getValue(),
                                postSnapshot.child("desLat").getValue(),
                                postSnapshot.child("delLon").getValue(),
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


                        Intent intent = new Intent(CompletedActivity.this,MapsPendingActivity.class);
                        intent.putExtra("JI",journeyInfo);
                        startActivity(intent);

                    }
                    iterate++;
                }
                iterate = 0;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onDeleteClick(int position) {

    }
}
