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

import com.example.isaac.metrolinq.Adapters.AdapeterComplete;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.DriverCar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCompletedActivity extends AppCompatActivity {


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

        mAdapter = new AdapeterComplete(AllCompletedActivity.this, namesFD);

        mRecyclerView.setAdapter(mAdapter);

//
//        clientNameDB = FirebaseDatabase.getInstance().getReference("Completed Journey");
//        mDB = FirebaseDatabase.getInstance().getReference("Journey Info");


        clientNameDB = FirebaseDatabase.getInstance().getReference("TestCompleteJourney");
        mDB = FirebaseDatabase.getInstance().getReference("TestJourney");

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
        inflater.inflate(R.menu.changescreencomplete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.requested3:
                Intent intent = new Intent(this, QueueActivity.class);
                startActivity(intent);
                break;

            case R.id.currentRunQueue3:
                Intent intent1= new Intent(this, CompletedActivity.class);
                startActivity(intent1);
                break;

            case R.id.map3:
                Intent intent2 = new Intent(this, MapsActivity.class);
                startActivity(intent2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
