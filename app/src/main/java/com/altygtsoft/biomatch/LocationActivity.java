package com.altygtsoft.biomatch;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.opencv.android.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//--------
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.altygtsoft.biomatch.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
public class LocationActivity extends ActionBarActivity  {

    public static final int bmp = 1;
    static String FLAG = "flag";
    Button Bas;
    GoogleMap map;
    ImageView iv;
    public Dialog progressDialog;
    private Marker marker;
    private Hashtable<String, String> markers;
   // private ImageLoader imageLoader;
    private DisplayImageOptions options;

    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp = new HashMap<String, String>();
    ImageView image;
    ProgressDialog pd;

    public CustomPictures cp;
    //public ParseObject parseObject;
    //LocationClass locationClass;
    Context context;

    LastLocationClass lastLocationClass ;



    ImageLoader imageLoader = new ImageLoader(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Get the intent from ListViewAdapter


        // Load image into the ImageView



     /*   ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Pictures");

                  // Locate the objectId from the class

       // Toast.makeText(getApplicationContext(),lastLocationClass.tacURL.get(1),Toast.LENGTH_LONG).show();
                  query.getInBackground("0H0U8NbW4l",
                          new GetCallback<ParseObject>() {

                              public void done(ParseObject object,
                                               ParseException e) {
                                  // TODO Auto-generated method stub

                                  // Locate the column named "ImageName" and set
                                  // the string
                                  ParseFile fileObject = (ParseFile) object.get("TacYaprak");


                                  fileObject.getDataInBackground(new GetDataCallback() {

                                      public void done(byte[] data,
                                                       ParseException e) {
                                          if (e == null) {
                                              Log.d("test",
                                                      "We've got data in data.");
                                              // Decode the Byte[] into
                                              // Bitmap
                                              Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                              // Get the ImageView from
                                              // main.xml
                                              image = (ImageView) findViewById(R.id.image);

                                              // Set the Bitmap into the
                                              // ImageView
                                              image.setImageBitmap(bmp);

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

                  Bas = (Button) findViewById(R.id.getir);

                  if (map != null) {
                      map.setInfoWindowAdapter(new CustomInfoWindowAdapter());
                  }
                  Bas.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View view) {


                                                 //locationClass.setFilePath(getIntent().getStringExtra("path"));
                                                 //locationClass.DynamicMapUpdate();
                                                 lastLocationClass = new LastLocationClass(getApplicationContext(), map);
                                             }
                                         }

                  );
              }

              private class CustomInfoWindowAdapter implements InfoWindowAdapter {

                  private View view;
                  public CustomInfoWindowAdapter() {
                      view = getLayoutInflater().inflate(R.layout.activity_activity_custom_map_info,
                              null);
                  }

                  @Override
                  public View getInfoContents(final Marker marker) {

                      LocationActivity.this.marker = marker;


                      //------------------------

                      // Locate the class table named "ImageUpload" in Parse.com
                      ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                              "Pictures");
                      LatLng ll = marker.getPosition();

                      String ty = lastLocationClass.getObjectId(lastLocationClass.karsilastir(ll));

                      Toast.makeText(getApplicationContext(),ty,Toast.LENGTH_LONG).show();
                      // Locate the objectId from the class
                      //"0H0U8NbW4l"
                      query.getInBackground(ty,
                              new GetCallback<ParseObject>() {

                                  public void done(ParseObject object,
                                                   ParseException e) {
                                      // TODO Auto-generated method stub

                                      // Locate the column named "ImageName" and set
                                      // the string
                                      final ParseFile fileObject = (ParseFile) object.get("TacYaprak");

                                      fileObject.saveInBackground(new SaveCallback() {
                                          public void done(ParseException e) {
                                              // Handle success or failure here ...
                                          }
                                      }, new ProgressCallback() {
                                          public void done(Integer percentDone) {
                                              // Update your progress spinner here. percentDone will be between 0 and 100.
                                          }
                                      });
                                //      Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                                      fileObject.getDataInBackground(new GetDataCallback() {

                                          public void done(byte[] data,
                                                           ParseException e) {
                                              if (e == null) {
                                                  Log.d("test",
                                                          "We've got data in data.");
                                                  // Decode the Byte[] into
                                                  // Bitmap
                                                  Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                                  // Get the ImageView from
                                                  // main.xml
                                                  image = (ImageView) view.findViewById(R.id.leafimage);

                                                  // Set the Bitmap into the
                                                  // ImageView
                                                  image.setImageBitmap(bmp);

                                                  // Close progress dialog


                                              } else {
                                                  Log.d("test",
                                                          "There was a problem downloading the data.");
                                              }
                                          }
                                      });
                                  }
                              });

                      // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                      TextView latinfo = (TextView) view.findViewById(R.id.latinfo);
                      TextView lnginfo = (TextView) view.findViewById(R.id.lnginfo);


                      latinfo.setText("    lat: " + ll.latitude);
                      lnginfo.setText("    long: " + ll.longitude);


                      return view;


                  }

                  @Override
                  public View getInfoWindow(final Marker marker) {

                      return null;
                  }
              }


          }