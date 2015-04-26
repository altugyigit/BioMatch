package com.altygtsoft.biomatch;

/**
 * Created by Utkuloptuk on 27.2.2015.
 */
import android.location.Location;
import android.media.ExifInterface;

import java.io.IOException;

public class ExifLocation {
    
   // çalısıyor olması gereken kısım

//-------------------------------------------------------------
    public Location exif2Loc(String flNm) {
        String sLat = "", sLatR = "", sLon = "", sLonR = "";
        try {
            ExifInterface ef = new ExifInterface(flNm);
            sLat  = ef.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            sLon  = ef.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            sLatR = ef.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            sLonR = ef.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        } catch (IOException e) {return null;}

        double lat = dms2Dbl(sLat);
        if (lat > 180.0) return null;
        double lon = dms2Dbl(sLon);
        if (lon > 180.0) return null;

        lat = sLatR.contains("S") ? -lat : lat;
        lon = sLonR.contains("W") ? -lon : lon;

        Location loc = new Location("exif");
        loc.setLatitude(lat);
        loc.setLongitude(lon);
        return loc;
    }
    //-------------------------------------------------------------------------
    double dms2Dbl(String sDMS){
        double dRV = 999.0;
        try {
            String[] DMSs = sDMS.split(",", 3);
            String s[] = DMSs[0].split("/", 2);
            dRV = (new Double(s[0])/new Double(s[1]));
            s = DMSs[1].split("/", 2);
            dRV += ((new Double(s[0])/new Double(s[1]))/60);
            s = DMSs[2].split("/", 2);
            dRV += ((new Double(s[0])/new Double(s[1]))/3600);
        } catch (Exception e) {}
        return dRV;
    }
  //  çalısıyor olması gereken kısım
//---------------------------------------------------------------------------------
/*
    private File exifFile;    //It's the file passed from constructor
    private String exifFilePath;  //file in Real Path format
    private Activity parentActivity; //Parent Activity

    private String exifFilePath_withoutext;
    private String ext;

    private ExifInterface exifInterface;
    private Boolean exifValid = false;;

    //Exif TAG
//for API Level 8, Android 2.2
    private String exif_DATETIME = "";
    private String exif_FLASH = "";
    private String exif_FOCAL_LENGTH = "";
    private String exif_GPS_DATESTAMP = "";
    private String exif_GPS_LATITUDE = "";
    private String exif_GPS_LATITUDE_REF = "";
    private String exif_GPS_LONGITUDE = "";
    private String exif_GPS_LONGITUDE_REF = "";
    private String exif_GPS_PROCESSING_METHOD = "";
    private String exif_GPS_TIMESTAMP = "";
    private String exif_IMAGE_LENGTH = "";
    private String exif_IMAGE_WIDTH = "";
    private String exif_MAKE = "";
    private String exif_MODEL = "";
    private String exif_ORIENTATION = "";
    private String exif_WHITE_BALANCE = "";

    //Constructor from path
    ExifLocation(String fileString, Activity parent){
        exifFile = new File(fileString);
        parentActivity = parent;
        exifFilePath = fileString;
        PrepareExif();
    }

    //Constructor from URI
    ExifLocation(Uri fileUri, Activity parent){
        exifFile = new File(fileUri.toString());
        parentActivity = parent;
        exifFilePath = getRealPathFromURI(fileUri);
        PrepareExif();
    }

    private void PrepareExif(){

        int dotposition= exifFilePath.lastIndexOf(".");
        exifFilePath_withoutext = exifFilePath.substring(0,dotposition);
        ext = exifFilePath.substring(dotposition + 1, exifFilePath.length());

        if (ext.equalsIgnoreCase("jpg")){
            try {
                exifInterface = new ExifInterface(exifFilePath);
                ReadExifTag();
                exifValid = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void ReadExifTag(){

        exif_DATETIME = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
        exif_FLASH = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
        exif_FOCAL_LENGTH = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
        exif_GPS_DATESTAMP = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
        exif_GPS_LATITUDE = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        exif_GPS_LATITUDE_REF = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        exif_GPS_LONGITUDE = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        exif_GPS_LONGITUDE_REF = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        exif_GPS_PROCESSING_METHOD = exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);
        exif_GPS_TIMESTAMP = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
        exif_IMAGE_LENGTH = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
        exif_IMAGE_WIDTH = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
        exif_MAKE = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
        exif_MODEL = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
        exif_ORIENTATION = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
        exif_WHITE_BALANCE = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = parentActivity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getSummary(){
        if(!exifValid){
            return ("Invalide EXIF!");
        }else{
            return( exifFilePath + " : \n" +

                    "Name without extension: " + exifFilePath_withoutext + "\n" +
                    "with extension: " + ext + "\n" +

                    //"Date Time: " + exif_DATETIME + "\n" +
                    //"Flash: " + exif_FLASH + "\n" +
                    //"Focal Length: " + exif_FOCAL_LENGTH + "\n" +
                    //"GPS Date Stamp: " + exif_GPS_DATESTAMP + "\n" +
                    "GPS Latitude: " + exif_GPS_LATITUDE + "\n" +
                    "GPS Latitute Ref: " + exif_GPS_LATITUDE_REF + "\n" +
                    "GPS Longitude: " + exif_GPS_LONGITUDE + "\n" +
                    "GPS Longitude Ref: " + exif_GPS_LONGITUDE_REF);
            //"Processing Method: " + exif_GPS_PROCESSING_METHOD + "\n" +
            //"GPS Time Stamp: " + exif_GPS_TIMESTAMP + "\n" +
            //"Image Length: " + exif_IMAGE_LENGTH + "\n" +
            //"Image Width: " + exif_IMAGE_WIDTH + "\n" +
            //"Make: " + exif_MAKE + "\n" +
            //"Model: " + exif_MODEL + "\n" +
            //"Orientation: " + exif_ORIENTATION + "\n" +
            //"White Balance: " + exif_WHITE_BALANCE + "\n");
        }
    }

    public void UpdateGeoTag(){
        //with dummy data
        final String DUMMY_GPS_LATITUDE = "22/1,21/1,299295/32768";
        final String DUMMY_GPS_LATITUDE_REF = "N";
        final String DUMMY_GPS_LONGITUDE = "114/1,3/1,207045/4096";
        final String DUMMY_GPS_LONGITUDE_REF = "E";

        exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, DUMMY_GPS_LATITUDE);
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, DUMMY_GPS_LATITUDE_REF);
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, DUMMY_GPS_LONGITUDE);
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, DUMMY_GPS_LONGITUDE_REF);
        try {
            exifInterface.saveAttributes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


  /*  public String getExif(ExifInterface exif)
    {
        String myAttribute=null;

        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
        return myAttribute;
    }

    private String getTagString(String tag, ExifInterface exif)
    {
        return exif.getAttribute(tag);
    }*/

      /*  private Float convertToDegree(String stringDMS){
            Float result = null;
            String[] DMS = stringDMS.split(",", 3);

            String[] stringD = DMS[0].split("/", 2);
            Double D0 = new Double(stringD[0]);
            Double D1 = new Double(stringD[1]);
            Double FloatD = D0/D1;

            String[] stringM = DMS[1].split("/", 2);
            Double M0 = new Double(stringM[0]);
            Double M1 = new Double(stringM[1]);
            Double FloatM = M0/M1;

            String[] stringS = DMS[2].split("/", 2);
            Double S0 = new Double(stringS[0]);
            Double S1 = new Double(stringS[1]);
            Double FloatS = S0/S1;

            result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

            return result;


        };*/


    }


