package com.altygtsoft.biomatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.parse.ParseQuery;


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

//custom map marker penceresi
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                final View v = getLayoutInflater().inflate(R.layout.activity_activity_custom_map_info, null);
                TextView baslik = (TextView) v.findViewById(R.id.baslik);
                TextView latinfo = (TextView) v.findViewById(R.id.latinfo);
                TextView lnginfo = (TextView) v.findViewById(R.id.lnginfo);

                ParseQuery query = new ParseQuery("Pictures");

                LatLng ll = marker.getPosition();
                latinfo.setText("    lat: " + ll.latitude);
                lnginfo.setText("    long: " + ll.longitude);
                //   final DownloadImageTask download = new DownloadImageTask((ImageView) v.findViewById(R.id.place_icon) ,arg0);
                // download.execute(iconurl);
                return v;
            }
        });

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
