package com.example.isaac.metrolinq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.isaac.metrolinq.Adapters.AdapeterComplete;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.DriverCar;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.JourneyInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CompletedActivity extends AppCompatActivity implements AdapeterComplete.OnItemClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {



    private List<DriverCar> namesFD;
    private DatabaseReference clientNameDB;
    private AdapeterComplete mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDB;
    DatabaseReference mDatabase, mDatabase2;
    private int day, month, year, hour, min;
    private int finalday, finalmonth, finalyear, finalHour, finalMin;

    private int iterate;
    private int i = 0;
    private int Pos = 0;

    private  int COMACT = 250;


    private int iterateNumber;
    private String payment;
    private String clientName, driver,car;

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

        clientNameDB = FirebaseDatabase.getInstance().getReference("TestJourney");
        mDB = FirebaseDatabase.getInstance().getReference("TestJourney");

        mDatabase = FirebaseDatabase.getInstance().getReference("TestJourney");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("TestCompleteJourney");
//
//        clientNameDB = FirebaseDatabase.getInstance().getReference("Journey Info");
//        mDB = FirebaseDatabase.getInstance().getReference("Journey Info");
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("Journey Info");
//        mDatabase2 = FirebaseDatabase.getInstance().getReference("Completed Journey");



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

            case R.id.complete2:
                Intent intent3 = new Intent(this, AllCompletedActivity.class);
                startActivity(intent3);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(final int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();



        showDialog(CompletedActivity.this, "Choose Action","Choose action for List Itme",position);
//        Intent intent = new Intent(CompletedActivity.this, CompleteStatusActivity.class);
//        intent.putExtra("POSITION_NUMBER",position);
//        startActivity(intent);





    }

    public void showDialog(Activity activity, String title, CharSequence message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                i = 0;
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                            if (position == i){

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

        /*
        *
        * both cancel and complete has same effect
        *
        * */

        builder.setNeutralButton("Close",null);
        builder.show();
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
    public void onDeleteClick(final int position) {

        Toast.makeText(this, "Delete Click", Toast.LENGTH_SHORT).show();

        i = 0;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    if (position == i){


                        mDatabase.child(postSnapshot.getKey()).removeValue();


                    }

                    i++;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAmendClick(final int position) {



        Pos = position;
        Toast.makeText(CompletedActivity.this, "AmendClick", Toast.LENGTH_SHORT).show();

        final String [] amendOption = {"Locations", "Pick up Times", "Payment Type","Name","Driver"};



        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CompletedActivity.this)  ;
        builder.setCancelable(true);
        builder.setSingleChoiceItems(amendOption, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(CompletedActivity.this, amendOption[which], Toast.LENGTH_SHORT).show();


                switch (which){
                    case 0:
                        // open up map and get the cordinates of new points with new fares
                        Intent intent = new Intent(CompletedActivity.this, MapAmendActivity.class);
                        intent.putExtra("POSITION", position);
                        intent.putExtra("CompletedAct", COMACT);

                        startActivity(intent);
                        break;


                    case 1:

                        // open time dialog followed by day dialog

                        Calendar c = Calendar.getInstance();

                        hour = c.get(Calendar.HOUR);
                        min = c.get(Calendar.MINUTE);


                        TimePickerDialog timePickerDialog = new TimePickerDialog(CompletedActivity.this, CompletedActivity.this,hour, min, true);
                        timePickerDialog.show();



                        break;


                    case 2:

                        //Open dialog with payment types
                        final String [] payType = {"Cash", "Pre Paid", "Post Paid"};


                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CompletedActivity.this)  ;
                        builder.setCancelable(true);
                        builder.setSingleChoiceItems(payType, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                iterateNumber =0;
                                Toast.makeText(CompletedActivity.this, payType[which], Toast.LENGTH_SHORT).show();
                                payment = payType[which];

                                if (payment.equals("Pre Paid")){
                                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(CompletedActivity.this);
                                    builder1.setTitle("Enter Amount");

// Set up the input
                                    final EditText input = new EditText(CompletedActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                    input.setInputType(InputType.TYPE_CLASS_NUMBER );
                                    builder1.setView(input);

// Set up the buttons
                                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final int roundedfare = Integer.parseInt(input.getText().toString()) ;

                                            clientNameDB.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                                                        if (position == iterateNumber) {

                                                            clientNameDB.child(postSnapshot.getKey()).child("fare"). setValue(roundedfare);
                                                            clientNameDB.child(postSnapshot.getKey()).child("payType").setValue(payment);



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


                                    builder1.show();
                                }
                                else {
                                    clientNameDB.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                                                if (position == iterateNumber) {

                                                    clientNameDB.child(postSnapshot.getKey()).child("payType").setValue(payment);

                                                }
                                                iterateNumber++;
                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }

                                dialog.dismiss();
                            }
                        });

                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CompletedActivity.this, "Cancel", Toast.LENGTH_SHORT).show();


                            }
                        });
                        android.support.v7.app.AlertDialog dialog1 = builder.create();
                        dialog1.show();
                        break;

                    case 3:
                        //open dialog that changes name


                        android.support.v7.app.AlertDialog.Builder builders = new android.support.v7.app.AlertDialog.Builder(CompletedActivity.this);
                        builders.setTitle("Client Name");

// Set up the input
                        final EditText input = new EditText(CompletedActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT );
                        builders.setView(input);

// Set up the buttons
                        builders.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clientName = input.getText().toString();
                                iterateNumber = 0;

                                Toast.makeText(CompletedActivity.this, clientName, Toast.LENGTH_SHORT).show();

                                clientNameDB.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                                            if (position == iterateNumber) {

                                                clientNameDB.child(postSnapshot.getKey()).child("clientName"). setValue(clientName);



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
                        break;

                    case 4:

                        AlertDialog.Builder builders1 = new AlertDialog.Builder(CompletedActivity.this);
                        builders1.setTitle("Client Name");

                        Context context = CompletedActivity.this;
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

                        builders1.setView(layout); // Again this is a set method, not add

// Set up the buttons
                        builders1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                driver = driver1.getText().toString();
                                car = Car.getText().toString();
                                iterateNumber = 0;


                                clientNameDB.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                                            if (position == iterateNumber) {


                                                mDatabase.child(postSnapshot.getKey()).child("driver").setValue(driver);
                                                mDatabase.child(postSnapshot.getKey()).child("plateNumber").setValue(car);

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


                        builders1.show();

                }
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CompletedActivity.this, "Cancel", Toast.LENGTH_SHORT).show();


            }
        });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        finalHour = hourOfDay;
        finalMin = minute;

        Calendar calendar = Calendar.getInstance();

        year =calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);




        DatePickerDialog datePickerDialog = new DatePickerDialog(CompletedActivity.this, CompletedActivity.this,year,month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        finalyear = year;
        finalmonth = month +1;
        finalday = dayOfMonth;


        iterateNumber = 0;

        Toast.makeText(CompletedActivity.this, clientName, Toast.LENGTH_SHORT).show();

        clientNameDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                    if (Pos == iterateNumber) {

                        clientNameDB.child(postSnapshot.getKey()).child("hour"). setValue(finalHour);
                        clientNameDB.child(postSnapshot.getKey()).child("min"). setValue(finalMin);
                        clientNameDB.child(postSnapshot.getKey()).child("day"). setValue(finalday);
                        clientNameDB.child(postSnapshot.getKey()).child("month"). setValue(finalmonth);
                        clientNameDB.child(postSnapshot.getKey()).child("year"). setValue(finalyear);


                    }
                    iterateNumber++;
                }

                Pos = 0;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}
