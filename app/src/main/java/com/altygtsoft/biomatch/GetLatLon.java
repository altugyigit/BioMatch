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
    public List<Double> latArray;
    public List<Double> lonArray;
    public static int forSize;
    public GetLatLon(Context context){

        this.context = context;
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
                    latArray.add(tempGeoPoint.getLatitude());
                    lonArray.add(tempGeoPoint.getLongitude());
                }


            }

        });


    }

    public void parseInit(){

        Parse.initialize(this, "HgrrtDO2dnazkQCPY59MR82ERhiamS5b1LTXBit8", "FS2hiyTi5uYVqz392tA39aXHYxubPdsGv28IiJ5Y");



    }




}
