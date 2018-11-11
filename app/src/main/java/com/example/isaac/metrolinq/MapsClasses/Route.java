package com.example.isaac.metrolinq.MapsClasses;

import com.example.isaac.metrolinq.MapsClasses.Distance;
import com.example.isaac.metrolinq.MapsClasses.Duration;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
