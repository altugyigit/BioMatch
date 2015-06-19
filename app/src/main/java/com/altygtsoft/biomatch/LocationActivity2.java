package com.altygtsoft.biomatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


public class LocationActivity2 extends ActionBarActivity {

    Button Bas;
    GoogleMap map;
    LocationClass2 locationClass2;

    Bellek_Activity bellekac;
    public static final int SelectedImage=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        locationClass2 = new LocationClass2(map);
        bellekac=new Bellek_Activity();


        //setmaptype da kullanabilece�imiz map t�rlerini belirliyoruz.
        /*map t�rleri;

    MapTypeId.ROADMAP Yollar haritas�
    MapTypeId.SATELLITE uydu g�r�nt�s�
    MapTypeId.HYBRID uydu+yollar
    MapTypeId.TERRAIN cogarafik harita(y�kseltilere g�re renklendirme idi yanl�s hat�rlam�yorsam
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
                ImageView iv =(ImageView) v.findViewById(R.id.leafimage);
                Bitmap SelectedImage= BitmapFactory.decodeFile(bellekac.filePath);
                latinfo.setText("lat: "+locationClass2.getLatFunc());
                lnginfo.setText("long: "+ locationClass2.getLonFunc());
                Drawable d=new BitmapDrawable(SelectedImage);
                iv.setBackground(d);
                return v;
            }
        });

    }


}
