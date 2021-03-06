package com.example.isaac.metrolinq;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.isaac.metrolinq.Adapters.AdapterQueue;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.JourneyInfo;
import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.QueueTimeName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QueueActivity extends AppCompatActivity implements AdapterQueue.OnItemClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    private List<QueueTimeName> namesFD;
    private DatabaseReference clientNameDB, mDB;
    private AdapterQueue mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    String clientName= "";
    int Pos;


     int  iterate = 0;
     int iterateNumber =0;
     String driver,car;
     String payment;
    private int day, month, year, hour, min;
    private int finalday, finalmonth, finalyear, finalHour, finalMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);


        initMaps();
        requestRecView();

    }

    private void requestRecView() {
        clientNameDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namesFD.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    if (postSnapshot.child("assignDriver").getValue().toString().equals("no")) {
                        String name = postSnapshot.child("clientName").getValue().toString();
                        String hour = postSnapshot.child("hour").getValue().toString();
                        String min = postSnapshot.child("min").getValue().toString();
                        String paymentType = postSnapshot.child("payType").getValue().toString();
                        String day1 = postSnapshot.child("day").getValue().toString();
                        String month1 = postSnapshot.child("month").getValue().toString();
                        String year1 = postSnapshot.child("year").getValue().toString();

                        namesFD.add(new QueueTimeName(hour+":"+min,name,paymentType,day1+"/"+month1+"/"+year1));
                    }
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initMaps() {

        mRecyclerView = findViewById(R.id.recyclerViewQueue);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        namesFD = new ArrayList<>();

        mAdapter = new AdapterQueue(QueueActivity.this, namesFD);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(QueueActivity.this);

        /*
        * change to original list when deploying app
        *
        *
        *
        * */
//
//        clientNameDB = FirebaseDatabase.getInstance().getReference("Scheduled Info");
//        mDB = FirebaseDatabase.getInstance().getReference("Scheduled Info");
//        mDatabase = FirebaseDatabase.getInstance().getReference("Journey Info");

        clientNameDB = FirebaseDatabase.getInstance().getReference("TestRequest");
        mDB = FirebaseDatabase.getInstance().getReference("TestRequest");
        mDatabase = FirebaseDatabase.getInstance().getReference("TestJourney");
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

                creatPendingDB(position,Car,driver1);

            }
        });


        builders.show();


    }

    private void creatPendingDB(final int position, EditText Car, EditText driver1) {
        driver = driver1.getText().toString();
        car = Car.getText().toString();
        iterateNumber = 0;


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

    @Override
    public void onJourneyInfoClick(final int position) {
        Toast.makeText(this, "Journey Info Click at position: " + position, Toast.LENGTH_SHORT).show();



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
    public void onDeleteClick(final int position) {

        iterateNumber = 0;
        clientNameDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren() ){

                    if (position == iterateNumber) {
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

    @Override
    public void onAmendClick(final int position) {

        Pos = position;
        Toast.makeText(QueueActivity.this, "AmendClick", Toast.LENGTH_SHORT).show();

        final String [] amendOption = {"Locations", "Pick up Times", "Payment Type","Name"};



        AlertDialog.Builder builder = new AlertDialog.Builder(QueueActivity.this)  ;
        builder.setCancelable(true);
        builder.setSingleChoiceItems(amendOption, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(QueueActivity.this, amendOption[which], Toast.LENGTH_SHORT).show();


                switch (which){
                    case 0:
                        // open up map and get the cordinates of new points with new fares
                        Intent intent = new Intent(QueueActivity.this, MapAmendActivity.class);
                        intent.putExtra("POSITION", position);
                        startActivity(intent);
                        break;


                    case 1:

                        // open time dialog followed by day dialog

                        openTimeDate();
                        break;


                    case 2:
                        //Open dialog with payment types
                        final String [] payType = {"Cash", "Pre Paid", "Post Paid"};


                        AlertDialog.Builder builder = new AlertDialog.Builder(QueueActivity.this)  ;
                        builder.setCancelable(true);
                        builder.setSingleChoiceItems(payType, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                iterateNumber =0;
                                Toast.makeText(QueueActivity.this, payType[which], Toast.LENGTH_SHORT).show();
                                payment = payType[which];

                                if (payment.equals("Pre Paid")){
                                    openEditTextAmountDialog(position);
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
                                Toast.makeText(QueueActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog dialog1 = builder.create();
                        dialog1.show();
                        break;

                    case 3:
                        //open dialog that changes name


                        AlertDialog.Builder builders = new AlertDialog.Builder(QueueActivity.this);
                        builders.setTitle("Client Name");

// Set up the input
                        final EditText input = new EditText(QueueActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT );
                        builders.setView(input);

// Set up the buttons
                        builders.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clientName = input.getText().toString();
                                iterateNumber = 0;

                                Toast.makeText(QueueActivity.this, clientName, Toast.LENGTH_SHORT).show();

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

                }
               dialog.dismiss();
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QueueActivity.this, "Cancel", Toast.LENGTH_SHORT).show();


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void openEditTextAmountDialog(final int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(QueueActivity.this);
        builder1.setTitle("Enter Amount");

// Set up the input
        final EditText input = new EditText(QueueActivity.this);
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

    private void openTimeDate() {

        Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR);
        min = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(QueueActivity.this, QueueActivity.this,hour, min, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        finalHour = hourOfDay;
        finalMin = minute;

        Calendar calendar = Calendar.getInstance();

        year =calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(QueueActivity.this, QueueActivity.this,year,month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        finalyear = year;
        finalmonth = month +1;
        finalday = dayOfMonth;


        iterateNumber = 0;


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
