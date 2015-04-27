package com.altygtsoft.biomatch;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationClass
{
    private LatLng LOCATION_EDIRNE;
    private double lat;
    private double lon;
    private String filePath;
    public ExifLocation exifLocation;
    public  GoogleMap googleMap;
    public CameraUpdate update;
    public LocationClass(GoogleMap googleMap)
    {
        this.googleMap = googleMap;
        this.exifLocation = new ExifLocation();
    }
    public LatLng getLOCATION_EDIRNE() {
        return LOCATION_EDIRNE;
    }

    public void setLOCATION_EDIRNE(LatLng LOCATION_EDIRNE) {
        this.LOCATION_EDIRNE = LOCATION_EDIRNE;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getLatFunc()
    {
        this.lat = exifLocation.exif2Loc(this.filePath).getLatitude();
        return  this.lat;
    }
    public double getLonFunc()
    {
        this.lon = exifLocation.exif2Loc(this.filePath).getLongitude();
        return this.lon;
    }
    public void DynamicMapUpdate()
    {
        this.lat = getLatFunc();
        this.lon = getLonFunc();

        this.LOCATION_EDIRNE = new LatLng(lat, lon);
        this.googleMap.addMarker(new MarkerOptions().position(LOCATION_EDIRNE));
        this.update = CameraUpdateFactory.newLatLngZoom(LOCATION_EDIRNE, 16);
        this.googleMap.animateCamera(update);
    }
}
