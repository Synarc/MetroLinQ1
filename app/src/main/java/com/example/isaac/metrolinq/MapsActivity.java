package com.example.isaac.metrolinq;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.ScheduleInfo;
import com.example.isaac.metrolinq.MapsClasses.DirectionFinder;
import com.example.isaac.metrolinq.MapsClasses.DirectionFinderListener;
import com.example.isaac.metrolinq.MapsClasses.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final int LOCATION_REQUEST = 500 ;
    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    ArrayList<LatLng> listpoints;


    int scase = 0;

    private Date currentDate;

    private static  final String CASH = "cash";
    private static final String PREPAID = "prepaid";
    private static final String POSTPAID = "postpaid";
    private static final String ASSIGN_DRIVER = "no";

    private String payment = "";

    ArrayList<Double> distancesToTotal;
    TimePicker timePicker;
    DatePicker datePicker;
    Button selectTimeButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseFare;
    private ScheduleInfo scheduleInfo;
    private LatLng Base;
    double latOrigin;
    double lonOrigin;
    double latDesti;
    double lonDesti;
    private EditText enterName, prepaidAmount;
    private Button confirmName;
    private LinearLayout horiLLClientName;
    private  Button dateConfirmButton;
    private LinearLayout dateLL, choosePayLL;
    private Button cash, prepaid, postpaid;

    private String m_Text;

    int day, month, year, hour, min;
    int finalday, finalmonth, finalyear, finalHour, finalMin;


    private Double m;
    private  Double y;
    int roundedfare = 0;
    private static String TIMECONFIRM = "Confirm PickUp Time";
    private static String JOURNEYCONFIRM = "Confirm Journey";
    private static String DATECONFIRM = "Confirm Date";
    private static String PAYMENT_TYPE = "Choose Payment";
    private static String CLIENT_NAME = "Confirm Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listpoints = new ArrayList<>();
        distancesToTotal = new ArrayList<>();

        final String [] payType = {"Cash", "Pre Paid", "Post Paid"};

        Base = new LatLng(-9.447991923108408,147.1935924142599);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);
        selectTimeButton = findViewById(R.id.selectTimeButton);
        confirmName = findViewById(R.id.buttonClientName);
        enterName = findViewById(R.id.editTextClientname);
        horiLLClientName = findViewById(R.id.LLclientName);
        dateConfirmButton = findViewById(R.id.datePickerButton);
        dateLL = findViewById(R.id.LLDatePicker);

        choosePayLL = findViewById(R.id.LLChoosePay);
        cash = findViewById(R.id.cash);
        prepaid = findViewById(R.id.prepaid);
        postpaid = findViewById(R.id.postPaid);
        prepaidAmount = findViewById(R.id.edittextPrepaid);
       currentDate = Calendar.getInstance().getTime();



       // mDatabase = FirebaseDatabase.getInstance().getReference("Scheduled Info");
        mDatabase = FirebaseDatabase.getInstance().getReference("TestRequest");
        mDatabaseFare = FirebaseDatabase.getInstance().getReference("Fare Change");

        mDatabaseFare.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String m_string = dataSnapshot.child("m").getValue().toString();
                    String y_string = dataSnapshot.child("y").getValue().toString();

                    m = Double.parseDouble(m_string);
                    y = Double.parseDouble(y_string);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                switch (scase){

                    case 0:
                        findViewById(R.id.map).setVisibility(View.INVISIBLE);
                        Calendar c = Calendar.getInstance();

                        hour = c.get(Calendar.HOUR);
                        min = c.get(Calendar.MINUTE);


                        TimePickerDialog timePickerDialog = new TimePickerDialog(MapsActivity.this, MapsActivity.this,hour, min, true);

                        timePickerDialog.show();

                        selectTimeButton.setText(DATECONFIRM);


                        LatLng sydney = new LatLng(-9.4438, 147.1803);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

                        scase =1;
                        break;

                    case 1:
                        Calendar calendar = Calendar.getInstance();

                        year =calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this, MapsActivity.this,year,month, day);

                        datePickerDialog.show();
                        Toast.makeText(getApplicationContext(),timePicker.getCurrentHour()+" : "+timePicker.getCurrentMinute(),Toast.LENGTH_SHORT).show();



                        selectTimeButton.setText(PAYMENT_TYPE);


                        listpoints.clear();
                        mMap.clear();

                        scase = 2;
                        break;

                    case 2:

                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this)  ;
                        builder.setCancelable(true);
                        builder.setSingleChoiceItems(payType, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(MapsActivity.this, payType[which], Toast.LENGTH_SHORT).show();
                                payment = payType[which];

                                if (payment.equals("Pre Paid")){
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                                    builder1.setTitle("Enter Amount");

// Set up the input
                                    final EditText input = new EditText(MapsActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                    input.setInputType(InputType.TYPE_CLASS_NUMBER );
                                    builder1.setView(input);

// Set up the buttons
                                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                           roundedfare = Integer.parseInt(input.getText().toString()) ;
                                        }
                                    });


                                    builder1.show();
                                }

                                dialog.dismiss();
                            }
                        });

                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MapsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();


                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        selectTimeButton.setText(CLIENT_NAME);

                        scase = 3;

                        break;

                    case 3:

                        AlertDialog.Builder builders = new AlertDialog.Builder(MapsActivity.this);
                        builders.setTitle("Client Name");

// Set up the input
                        final EditText input = new EditText(MapsActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT );
                        builders.setView(input);

// Set up the buttons
                        builders.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                m_Text = input.getText().toString();

                                selectTimeButton.setText(TIMECONFIRM);

                                scase = 0;


                                scheduleInfo = new ScheduleInfo(finalHour,finalMin
                                        , latOrigin,lonOrigin,
                                        latDesti,lonDesti, roundedfare, m_Text,
                                        finalyear,
                                        finalmonth ,
                                        finalday, currentDate, payment, ASSIGN_DRIVER );

                                String uploadId = mDatabase.push().getKey();
                                mDatabase.child(uploadId).setValue(scheduleInfo);
                            }
                        });


                        builders.show();


                        findViewById(R.id.map).setVisibility(View.VISIBLE);
                        break;


                }

            }
        });



    }

    private void sendRequest(String ori, String des) {
        String origin = ori;
        String destination = des;

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-9.4438, 147.1803);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));



        /*
        * this statement is stoping the hybrid from showing in some phones
        *
        * */

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//           // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


                if (listpoints.size() == 0){

                    listpoints.clear();
                    mMap.clear();
                }

                listpoints.add(latLng);
                String str_origin = listpoints.get(0).latitude+ ","+ listpoints.get(0).longitude;
                String str_Base = Base.latitude + "," + Base.longitude;

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listpoints.size() == 1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

                    sendRequest(str_Base,str_origin);

                }
                else{

                    selectTimeButton.setVisibility(View.VISIBLE);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }

                mMap.addMarker(markerOptions);

                if (listpoints.size() ==2){


                    latOrigin = listpoints.get(0).latitude;
                    lonOrigin = listpoints.get(0).longitude;
                    latDesti =listpoints.get(1).latitude;
                    lonDesti = listpoints.get(1).longitude;

                    String str_dest = listpoints.get(1).latitude + "," + listpoints.get(1).longitude;

                    sendRequest(str_origin,str_dest);
                   // sendRequest(str_dest, str_Base);

                }
            }
        });


    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }
//        if (polylinePaths != null) {
//            for (Polyline polyline:polylinePaths ) {
//                polyline.remove();
//            }
//        }


    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        Double priceRide = 0.0;
        double roundTripDistance = 0;




        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 13));



//            priceRide = round(route.distance.value *m/1000.0 + y,2);
//

            distancesToTotal.add((double) route.distance.value);

 //


            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.RED).
                    width(7);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
        if (listpoints.size() == 2){

            String str_dest = listpoints.get(1).latitude + "," + listpoints.get(1).longitude;
            String str_Base = Base.latitude + "," + Base.longitude;
            sendRequest(str_dest, str_Base);
            listpoints.clear();



        }

        if (distancesToTotal.size() == 3){
            roundTripDistance = distancesToTotal.get(0)+ distancesToTotal.get(1)+ distancesToTotal.get(2);
            priceRide = roundTripDistance *y*m/1000.0;

            roundedfare = roundup(priceRide);
            ((TextView) findViewById(R.id.price)).setText("K "+Double.toString(roundedfare));

            distancesToTotal.clear();

        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int roundup(double value){

        int rounded = 0;
        int divided;
        if (value % 5 != 0){
            divided = (int) (value/5);
            rounded = divided * 5 + 5;
        }else {
            rounded = (int) value;
        }

        return rounded;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.queue:
                Intent intent = new Intent(this, QueueActivity.class);
                startActivity(intent);
                break;

            case R.id.complete:
                Intent intent1= new Intent(this, AllCompletedActivity.class);
                startActivity(intent1);
                break;

            case R.id.currentRun:
                Intent intent2 = new Intent(this, CompletedActivity.class);
                startActivity(intent2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        finalyear = year;
        finalmonth = month +1;
        finalday = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



        finalHour = hourOfDay;
        finalMin = minute;
    }
}
