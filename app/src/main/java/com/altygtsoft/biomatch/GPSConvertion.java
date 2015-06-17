package com.altygtsoft.biomatch;

import android.content.Context;

/**
 * Created by kerem on 17.06.2015.
 */
public class GPSConvertion {

    private final Context context;

    public GPSConvertion(Context context){
        this.context = context;


    }

    private StringBuilder stringBuilder = new StringBuilder(20);

    public String latitudeRef(final double latitude){
        return latitude < 0.0d ? "S" : "N";
    }

    public String longitudeRef(final double longitude){
        return longitude < 0.0d ? "W" : "E";
    }

    public final String convert(double latitude){
        latitude = Math.abs(latitude);
        final int degree = (int) latitude;

        latitude *= 60;
        latitude -= degree * 60.0d;

        final int minute = (int) latitude;

        latitude *= 60;
        latitude -= minute * 60.0d;

        final int second = (int) (latitude * 1000.0d);

        stringBuilder.setLength(0);
        stringBuilder.append(degree);
        stringBuilder.append("/1,");
        stringBuilder.append(minute);
        stringBuilder.append("/1,");
        stringBuilder.append(second);
        stringBuilder.append("/1000,");

        return stringBuilder.toString();


    }
}
