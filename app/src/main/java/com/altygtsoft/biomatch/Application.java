package com.altygtsoft.biomatch;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * Created by Altu on 1.5.2015.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "HgrrtDO2dnazkQCPY59MR82ERhiamS5b1LTXBit8", "FS2hiyTi5uYVqz392tA39aXHYxubPdsGv28IiJ5Y");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParseObject.registerSubclass(Pictures.class);
        ParseObject.registerSubclass(CustomPictures.class);

    }

}
