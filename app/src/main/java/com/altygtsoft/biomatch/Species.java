package com.altygtsoft.biomatch;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Altuğ on 27.3.2015.
 */
@ParseClassName("Species")
public class Species extends ParseObject {

    private String name_turkish = "name_turkish";
    private String name_latin = "name_turkish";

    public Species()
    {

    }
    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
    public String getName_id() {

        return getObjectId();

    }

    public String getName_turkish() {

        return getString(name_turkish);

    }

    public void setName_turkish(String turkish) {

        put(name_turkish, turkish);

    }

    public String getName_latin() {

        return getString(name_latin);

    }

    public void setName_latin(String latin) {

        put(name_latin,latin);

    }
}
