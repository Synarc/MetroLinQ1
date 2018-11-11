package com.example.isaac.metrolinq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.isaac.metrolinq.Adapters.AdapterQueue;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.JourneyInfo;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.QueueTimeName;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.ScheduleInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity implements AdapterQueue.OnItemClickListener{


    private List<QueueTimeName> namesFD;
    private DatabaseReference clientNameDB, mDB;
    private AdapterQueue mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

     int  iterate = 0;
     int iterateNumber =0;
     String driver,car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        mRecyclerView = findViewById(R.id.recyclerViewQueue);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        namesFD = new ArrayList<>();

        mAdapter = new AdapterQueue(QueueActivity.this, namesFD);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(QueueActivity.this);

        clientNameDB = FirebaseDatabase.getInstance().getReference("Scheduled Info");
        mDB = FirebaseDatabase.getInstance().getReference("Scheduled Info");
        mDatabase = FirebaseDatabase.getInstance().getReference("Journey Info");



        clientNameDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namesFD.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    if (postSnapshot.child("assignDriver").getValue().toString().equals("no")) {
                        String name = postSnapshot.child("clientName").getValue().toString();
                        String hour = postSnapshot.child("hour").getValue().toString();
                        String min = postSnapshot.child("min").getValue().toString();
                        namesFD.add(new QueueTimeName(hour+":"+min,name));
                    }
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //startActivity(mAdapter.getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_screen_queue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.currentRunQueue:
                Intent intent = new Intent(this, CompletedActivity.class);
                startActivity(intent);
                break;

            case R.id.mapQueue:
                Intent intent2 = new Intent(this, MapsActivity.class);
                startActivity(intent2);
                break;

            case R.id.complete1:
                Intent intent3 = new Intent(this, AllCompletedActivity.class);
                startActivity(intent3);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(final int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();



//        Intent intent = new Intent(QueueActivity.this, SelectDriverCarActivity.class);
//        intent.putExtra("POSITION_NUMBER",position);
//        startActivity(intent);

        AlertDialog.Builder builders = new AlertDialog.Builder(QueueActivity.this);
        builders.setTitle("Client Name");

        Context context = QueueActivity.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add a TextView here for the "Title" label, as noted in the comments
        final EditText driver1 = new EditText(context);
        driver1.setHint("Driver");
        layout.addView(driver1); // Notice this is an add method

        // Add another TextView here for the "Description" label
        final EditText Car = new EditText(context);
        Car.setHint("Car");
        layout.addView(Car); // Another add method

        builders.setView(layout); // Again this is a set method, not add

// Set up the buttons
        builders.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                driver = driver1.getText().toString();
                car = Car.getText().toString();


                clientNameDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                            if (position == iterateNumber) {

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
                                        driver,
                                        car,
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

            }
        });


        builders.show();


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
                                    postSnapshot.child("desLon").getValue(),
                                    postSnapshot.child("fare").getValue(),
                                    postSnapshot.child("min").getValue(),
                                    postSnapshot.child("hour").getValue(),
                                    postSnapshot.child("day").getValue(),
                                    postSnapshot.child("month").getValue(),
                                    postSnapshot.child("year").getValue(),
                                   null,
                                    null,
                                    postSnapshot.child("clientName").getValue(),
                                    postSnapshot.child("payType").getValue(),
                                    postSnapshot.child("currentDate").getValue()
                            );


                            Intent intent = new Intent(QueueActivity.this,MapsDriverActivity.class);
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
