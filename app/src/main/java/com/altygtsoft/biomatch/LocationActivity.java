package com.altygtsoft.biomatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;


public class LocationActivity extends ActionBarActivity {

    Button Bas;
    GoogleMap map;
    //LocationClass locationClass;
    LastLocationClass lastLocationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //locationClass = new LocationClass(LocationActivity.this, map);

        Bas=(Button)findViewById(R.id.getir);
        //locationClass.setFilePath(getIntent().getStringExtra("path"));


        Bas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //locationClass.setFilePath(getIntent().getStringExtra("path"));
                //locationClass.DynamicMapUpdate();
                lastLocationClass = new LastLocationClass(getApplicationContext(), map);
            }
        });
    }



}
