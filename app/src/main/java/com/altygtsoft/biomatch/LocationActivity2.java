package com.altygtsoft.biomatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


public class LocationActivity2 extends ActionBarActivity {

    Button Bas;
    GoogleMap map;
    LocationClass2 locationClass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        locationClass2 = new LocationClass2(map);


        //setmaptype da kullanabileceðimiz map türlerini belirliyoruz.
        /*map türleri;

    MapTypeId.ROADMAP Yollar haritasý
    MapTypeId.SATELLITE uydu görüntüsü
    MapTypeId.HYBRID uydu+yollar
    MapTypeId.TERRAIN cogarafik harita(yükseltilere göre renklendirme idi yanlýs hatýrlamýyorsam
        */
        Bas=(Button)findViewById(R.id.getir);
        locationClass2.setFilePath(getIntent().getStringExtra("path"));


        Bas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationClass2.setFilePath(getIntent().getStringExtra("path"));
                locationClass2.DynamicMapUpdate();


            }
        });

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.activity_activity_custom_map_info, null);
                TextView baslik = (TextView) v.findViewById(R.id.baslik);
                TextView  latinfo = (TextView) v.findViewById(R.id.latinfo);
                TextView lnginfo = (TextView) v.findViewById(R.id.lnginfo);
                LatLng ll = marker.getPosition();

                latinfo.setText("lat: "+locationClass2.getLatFunc());
                lnginfo.setText("long: "+ locationClass2.getLonFunc());
                return v;
            }
        });

    }


}
