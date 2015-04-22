package com.altygtsoft.biomatch;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Pictures")
public class Pictures extends ParseObject {

    public  Pictures()
    {

    }

    public void setPhotoFileTac(ParseFile file) {
        put(file.getName(), file);
    }

    public void setPhotoFileCanak(ParseFile file) {
        put(file.getName(), file);
    }

    public void setPhotoFileYaprak(ParseFile file) {
        put(file.getName(), file);
    }

}
