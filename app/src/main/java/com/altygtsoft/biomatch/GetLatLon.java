package com.altygtsoft.biomatch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerem on 02.05.2015.
 */
public class GetLatLon extends Application {

    private final Context context;
    public static final double SOUTH_WEST_LAT = 40.634671;
    public static final double SOUTH_WEST_LON = 26.095247;
    public static final double NORTH_EAST_LAT = 40.634671;
    public static final double NORTH_EAST_LON = 26.095247;
    public static List<ParseObject> latLonObject;
    public double[] latLonArray;
    public static int forSize;
    public GetLatLon(Context context){

        this.context = context;
        getLatLon();


    }



    public double[] getLatLon() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pictures");
        query.whereExists("location");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                latLonObject = new ArrayList<>(list);
                double lat;
                double lon;
                forSize = 0;
                int size = latLonObject.size();
                size = size + 1;
                forSize = latLonObject.size();
                latLonArray = new double[size];
                //latLonArray = new ArrayList<Double>(10);

                for (int i = 0; i < forSize; i++) {
                    ParseObject tempObj = latLonObject.get(i);
                    ParseGeoPoint tempGeoPoint = tempObj.getParseGeoPoint("location");
                    lat = tempGeoPoint.getLatitude();
                    lon = tempGeoPoint.getLongitude();
                    latLonArray[i] = lat;
                    latLonArray[i+1] = lon;
                    Log.d("****LATLONPARSE****", "ENLEM = " + latLonArray[i] + " BOYLAM = " + latLonArray[i+1]);
                }


            }

        });

        return latLonArray;
    }

    public void parseInit(){

        Parse.initialize(this, "HgrrtDO2dnazkQCPY59MR82ERhiamS5b1LTXBit8", "FS2hiyTi5uYVqz392tA39aXHYxubPdsGv28IiJ5Y");



    }




}
