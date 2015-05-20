package com.altygtsoft.biomatch;

import android.content.Context;
import android.media.ExifInterface;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocationClass
{


    private LatLng LOCATION_EDIRNE;
    private double lat;
    private double lon;
    private String filePath;
    public ExifLocation exifLocation;
    public  GoogleMap googleMap;
    public CameraUpdate update;
    private Context context;
    public GetLatLon getLatLon = new GetLatLon(context);

    public static double tempLat;
    public static double tempLon;
    public static List<Double> latLonDoubleList;
    public LocationClass(Context context, GoogleMap googleMap)
    {

        this.context = context;
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
        File file = new File(filePath);
        if(file.exists())
        {
            this.filePath = filePath;
        }
        else
        {
            Toast.makeText(context, "Fotograf bulunamadi !", Toast.LENGTH_LONG).show();
        }
    }

    public double getLatFunc()
    {
        try
        {
            this.lat = exifLocation.exif2Loc(this.filePath).getLatitude();
            return  this.lat;
        }
        catch (Throwable ex)
        {
            Toast.makeText(context, "Fotografta konum bilgisi mevut degil !", Toast.LENGTH_LONG).show();
            return 0.0;
        }

    }
    public double getLonFunc()
    {
        try
        {
            this.lon = exifLocation.exif2Loc(this.filePath).getLongitude();
            return this.lon;
        }
        catch (Throwable ex)
        {
            return 0.0;
        }

    }
    public void DynamicMapUpdate()
    {
        this.lat = getLatFunc();
        this.lon = getLonFunc();
        List<Double> latArrayLC = new ArrayList<Double>();
        List<Double> lonArrayLC = new ArrayList<Double>();
        latArrayLC = getLatLon.latArray;
        lonArrayLC = getLatLon.lonArray;

        for (int i=0; i<latArrayLC.size(); i++) {
                tempLat = latArrayLC.get(i);
                Log.d("LocationClassMessage", "ENLEM : "+tempLat);
                tempLon = lonArrayLC.get(i);
                Log.d("LocationClassMessage", "BOYLAM : "+tempLon);

            this.LOCATION_EDIRNE = new LatLng(tempLat, tempLon);
            this.googleMap.addMarker(new MarkerOptions().position(LOCATION_EDIRNE)
                    .title("ENLEM : "+tempLat +"\nBOYLAM : "+tempLon));
            this.update = CameraUpdateFactory.newLatLngZoom(LOCATION_EDIRNE, 16);
            this.googleMap.animateCamera(update);
        }
    }
}
