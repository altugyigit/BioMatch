package com.altygtsoft.biomatch;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altug on 15.05.2015.
 */
public class LastLocationClass {

    private Context context;
    private LatLng LOCATION_EDIRNE;
    public GoogleMap googleMap;
    public CameraUpdate update;
    public static List<ParseObject> latLonObject;
    public List<Double> latArray;
    public List<Double> lonArray;

    public LastLocationClass(Context context, GoogleMap googleMap)
    {
        this.context = context;
        this.googleMap = googleMap;
        getLatLon();
    }

    public void getLatLon() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pictures");
        query.whereExists("location");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                latLonObject = new ArrayList<>(list);
                int size = latLonObject.size();

                latArray = new ArrayList<Double>();
                lonArray = new ArrayList<Double>();

                for (int i = 0; i < size; i++) {
                    ParseObject tempObj = latLonObject.get(i);
                    ParseGeoPoint tempGeoPoint = tempObj.getParseGeoPoint("location");
                    ParseFile tacFile = (ParseFile)tempObj.get("TacYaprak");
                    String tacURL = tacFile.getUrl();
                    latArray.add(tempGeoPoint.getLatitude());
                    lonArray.add(tempGeoPoint.getLongitude());

                    LOCATION_EDIRNE = new LatLng(tempGeoPoint.getLatitude(), tempGeoPoint.getLongitude());
                    Toast.makeText(LastLocationClass.this.context, "" + tacURL ,Toast.LENGTH_LONG).show();
                    String fotoGoster = Html.fromHtml("<p><img src='"+ tacURL +"'></p>").toString();
                    googleMap.addMarker(new MarkerOptions().position(LOCATION_EDIRNE).title(fotoGoster + "\nENLEM : "+tempGeoPoint.getLatitude() +"\nBOYLAM : "+tempGeoPoint.getLongitude()));
                    update = CameraUpdateFactory.newLatLngZoom(LOCATION_EDIRNE, 16);
                    googleMap.animateCamera(update);
                }


            }

        });
    }

}
