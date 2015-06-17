package com.altygtsoft.biomatch;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("CustomPictures")
public class CustomPictures extends ParseObject
{
    public  CustomPictures()
    {

    }

    public void setPhotoFile(ParseFile file) {
        put("picture", file);
    }

    public ParseGeoPoint getLocation(){
        return getParseGeoPoint("location");
    }
    public void setLocation(ParseGeoPoint value){
        put("location", value);
    }

}
