package com.altygtsoft.biomatch;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Pictures")
public class Pictures extends ParseObject {

    public  Pictures()
    {

    }

    public ParseFile getPhotoFile11() {
        return getParseFile("photo");
    }

    public void setPhotoFile11(ParseFile file) {
        put("photo", file);
    }

    public void setPhotoFile12(ParseFile file) {
        put("photo", file);
    }

    public ParseFile getPhotoFile21() {
        return getParseFile("photo");
    }

    public void setPhotoFile21(ParseFile file) {
        put("photo", file);
    }

    public ParseFile getPhotoFile22() {
        return getParseFile("photo");
    }

    public void setPhotoFile22(ParseFile file) {
        put("photo", file);
    }

    public ParseFile getPhotoFile31() {
        return getParseFile("photo");
    }

    public void setPhotoFile31(ParseFile file) {
        put("photo", file);
    }

    public ParseFile getPhotoFile32() {
        return getParseFile("photo");
    }

    public void setPhotoFile32(ParseFile file) {
        put("photo", file);
    }

}
