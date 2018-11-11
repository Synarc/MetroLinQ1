package com.example.isaac.metrolinq;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.isaac.metrolinq.FirebaseRecyclerViewClasses.JourneyInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsPendingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView date, time, fare, driver;

    JourneyInfo journeyInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pending);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        date = findViewById(R.id.dateMap3);
        time = findViewById(R.id.timeMap3);
        fare = findViewById(R.id.farePending);
        driver = findViewById(R.id.driverMap3);


        journeyInfo = (JourneyInfo) intent.getSerializableExtra("JI");

        time.setText("Pick Up Time: "+ journeyInfo.getHour() + ":"+ journeyInfo.getMin());
        date.setText(journeyInfo.getDay() + "/"+ journeyInfo.getMonth()+"/"+journeyInfo.getYear());
        fare.setText("K"+journeyInfo.getFare());
        driver.setText(journeyInfo.getDriver());
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



        LatLng sydney = new LatLng(-9.4438, 147.1803);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        LatLng origin = new LatLng(journeyInfo.getOriLat(),journeyInfo.getOriLon());
        LatLng destin = new LatLng(journeyInfo.getDesLat(),journeyInfo.getDelLon());


        MarkerOptions markerOri = new MarkerOptions();
        MarkerOptions markerDes = new MarkerOptions();

        markerDes.position(destin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOri.position(origin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        mMap.addMarker(markerOri);
        mMap.addMarker(markerDes);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MapsPendingActivity.this, CompletedActivity.class);
        startActivity(intent);
    }
}
