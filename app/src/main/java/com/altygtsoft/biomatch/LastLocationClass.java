package com.altygtsoft.biomatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by altug on 15.05.2015.
 */
public class LastLocationClass {

    private Context context;
    public static String obid   = "objectid";
    private String strobid = null;
    private LatLng LOCATION_EDIRNE;
    public GoogleMap googleMap;
    public CameraUpdate update;
    public static List<ParseObject> latLonObject;
    public List<Double> latArray;
    public List<Double> lonArray;
    public int iobid=0;
   // public List<String> TacURL;
    //public List<String> CanakURL;
   public List<String> tacURL=null;
  /*public LastLocationClass ()
  {
      getLatLon();
  }*/
    public LastLocationClass(Context context, GoogleMap googleMap)
    {
        this.context = context;
        this.googleMap = googleMap;
        getLatLon();
        //Toast.makeText();

    }

    public void getLatLon() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pictures");
        query.whereExists("location");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                latLonObject = new ArrayList<>(list);
                int size = latLonObject.size();
                tacURL = new ArrayList<String>();
                latArray = new ArrayList<Double>();
                lonArray = new ArrayList<Double>();

                for (int i = 0; i < size; i++) {
                    ParseObject tempObj = latLonObject.get(i);
                    ParseGeoPoint tempGeoPoint = tempObj.getParseGeoPoint("location");
                    ParseFile tacFile = (ParseFile) tempObj.get("TacYaprak");

                    String tac = tempObj.getObjectId();
                    tacURL.add(tac);


                    latArray.add(tempGeoPoint.getLatitude());
                    lonArray.add(tempGeoPoint.getLongitude());


                    LOCATION_EDIRNE = new LatLng(tempGeoPoint.getLatitude(), tempGeoPoint.getLongitude());

                    String fotoGoster = Html.fromHtml("<p><img src='" + tac + "'></p>").toString();
                    googleMap.addMarker(new MarkerOptions().position(LOCATION_EDIRNE).title("\nENLEM : " + tempGeoPoint.getLatitude() + "\nBOYLAM : " + tempGeoPoint.getLongitude() + fotoGoster));
                    update = CameraUpdateFactory.newLatLngZoom(LOCATION_EDIRNE, 16);
                    googleMap.animateCamera(update);
                }

                Toast.makeText(LastLocationClass.this.context, "", Toast.LENGTH_LONG).show();
            }

        });
    }
    public String getObjectId(int i) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pictures");
        query.whereExists("position");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                latLonObject = new ArrayList<>(list);
                int size = latLonObject.size();
                tacURL = new ArrayList<String>();
                latArray = new ArrayList<Double>();
                lonArray = new ArrayList<Double>();

                for (int i = 0; i < size; i++) {
                    ParseObject tempObj = latLonObject.get(i);
                    ParseGeoPoint tempGeoPoint = tempObj.getParseGeoPoint("location");
                    ParseFile tacFile = (ParseFile) tempObj.get("TacYaprak");

                    String tac = tempObj.getObjectId();
                    tacURL.add(tac);
                    ParseFile canakFile = (ParseFile) tempObj.get("CanakYaprak");

                    latArray.add(tempGeoPoint.getLatitude());
                    lonArray.add(tempGeoPoint.getLongitude());
                }
            }
        });
            return tacURL.get(i);
    }
    public int karsilastir(final LatLng latlng){
       ParseQuery<ParseObject> query = ParseQuery.getQuery("Pictures");
        query.whereExists("position");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                latLonObject = new ArrayList<>(list);
                int size = latLonObject.size();


                for (int j = 0; j < size; j++) {
                    ParseObject tempObj = latLonObject.get(j);
                    ParseGeoPoint tempGeoPoint = tempObj.getParseGeoPoint("location");

                    if (tempGeoPoint.getLatitude() == latlng.latitude && tempGeoPoint.getLongitude() == latlng.longitude) {
                         iobid= tempObj.getInt("position");

                    }
                }
            }
        });
        return iobid;
    }
}
