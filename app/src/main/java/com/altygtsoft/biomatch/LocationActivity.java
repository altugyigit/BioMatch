package com.altygtsoft.biomatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;


public class LocationActivity extends ActionBarActivity {
     Context con;
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
            public View getInfoContents(final Marker marker) {
                final View v = getLayoutInflater().inflate(R.layout.activity_activity_custom_map_info, null);
                TextView baslik = (TextView) v.findViewById(R.id.baslik);
                TextView latinfo = (TextView) v.findViewById(R.id.latinfo);
                TextView lnginfo = (TextView) v.findViewById(R.id.lnginfo);
               final  ImageView im=(ImageView) v.findViewById(R.id.leafimage);
               final ParseImageView piw=(ParseImageView) v.findViewById(R.id.pif);
              /*  ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Pictures");
                // Locate the objectId from the class
               //Eðer parse yapýsý deðiþecek olursa bu kodla imageview yada parseimageviewe e fotograf çekilebilir bi tek eksiði elle objectid giriþi o düzeltildiði zaman sýkýntý olmayacaktýr.
                query.getInBackground("WsfFydQier", new GetCallback<ParseObject>() {
                    public void done(ParseObject object,ParseException e) {
                        // TODO Auto-generated method stub
                        // Locate the column named "ImageName" and set
                        // the string
                        ParseFile fileObject = (ParseFile) object.get("TacYaprak");
                        fileObject.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data,ParseException e) {
                                if (e == null) {
                                    Log.d("test","We've got data in data.");
                                    // Decode the Byte[] into
                                    // Bitmap
                                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    // Get the ImageView from main.xml

                                    // Set the Bitmap into the
                                    // ImageView
                                    im.setImageBitmap(bmp);
                                    piw.setImageBitmap(bmp);
                                    // Close progress dialog


                                } else {
                                    Log.d("test",
                                            "There was a problem downloading the data.");
                                }
                            }
                        });
                    }
                });
*/

        im.setImageResource(R.drawable.ic_launcher);
                String s=marker.getTitle();

                Toast.makeText(getApplicationContext(),""+marker.hashCode(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();

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
