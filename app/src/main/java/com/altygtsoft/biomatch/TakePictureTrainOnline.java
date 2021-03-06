package com.altygtsoft.biomatch;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import com.altygtsoft.biomatch.Devices;

public class TakePictureTrainOnline extends ActionBarActivity {

    public static Camera camera;
    public static final int pictureHeight = 480;
    public static final int pictureWidth = 640;
    private SurfaceView surfaceView;
    public static ParseFile photoFile;
    private ImageButton photoButton;
    public PictureCache pictureCache;
    public Calendar c;
    public static String filenameIntent;
    public static byte[] scaledData;
    public static ProgressDialog pdialog;
    public static Location location;
    public static String focalLength;
    public static LocationManager locationManager;
    public static LocationRequest locationRequest;
    public static double latitude = 0;
    public static double longtitude = 0;
    public static double[] latlon;
    public static LocationListener locationListener;
    public static boolean isTac;
    public static boolean isCanak;
    public static String from;
    public static ParseGeoPoint parseGeoPoint;
    public static String SONY_XPERIA_Z2_SENSOR_SIZE = "4.55";
    public static String ASUS_ZENFONE_5_SENSOR_SIZE = "3.6";
    public static String SONY_XPERIA_SOLA_SENSOR_SIZE = "3.6";
    public String fileName;
    public ParseObject picturesParseObject = new ParseObject("Train");

    GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        latlon = new double[2];
        super.onCreate(savedInstanceState);

        isTac = true;
        isCanak = false;

        Intent i = getIntent();
        filenameIntent = i.getStringExtra("FILENAME");
        Toast.makeText(getApplicationContext(), "filename: "+filenameIntent,Toast.LENGTH_LONG).show();
        gpsTracker = new GPSTracker(TakePictureTrainOnline.this);

        if (gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longtitude = gpsTracker.getLongtitude();

            Toast.makeText(getApplicationContext(), "Konumumuzun enlemi = " + latitude + " boylamı ise = " + longtitude,Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Hala sıkıntı var konumda..", Toast.LENGTH_LONG).show();

        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latlon = getLocation();*/


        setContentView(R.layout.activity_take_picture_train_online);

        startCast();
    }

    private void startCast()
    {

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();
        }

        photoButton = (ImageButton) findViewById(R.id.camera_photo_button);
        if (camera == null) {
            try {
                camera = Camera.open();
                photoButton.setEnabled(true);
            } catch (Exception e) {

                photoButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Kamera Bulunamadı !",
                        Toast.LENGTH_LONG).show();
            }
        }
        try {
            photoButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (camera == null )
                        return;
                    try {
                        camera.autoFocus(new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                if (success) {

                                    camera.takePicture(new Camera.ShutterCallback() {

                                        @Override
                                        public void onShutter() {
                                            // nothing to do
                                        }

                                    }, null, new Camera.PictureCallback() {

                                        @Override
                                        public void onPictureTaken(byte[] data, Camera camera) {
                                            saveScaledPhoto(data);
                                            if (camera != null) {
                                                camera.getParameters();

                                                camera.startPreview();
                                            }
                                        }

                                    });

                                }
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Log.d("AUTOFOCUSCALLBACK", e.getMessage());
                    }


                    Log.d("HEIGHT / WIDTH = ",
                            +camera.getParameters().getPreviewSize().height + " " + camera.getParameters().getPreviewSize().width + " " +
                                    camera.getParameters().getPictureSize().height + " " + camera.getParameters().getPictureSize().width);

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("photoButtonOnClick", e.getMessage());
        }
        surfaceView = (SurfaceView)findViewById(R.id.camera_surface_view);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (camera != null) {

                        camera.setPreviewDisplay(holder);
                        camera.setDisplayOrientation(90);

                        Camera.Parameters params = camera.getParameters();
                        focalLength = String.valueOf(params.getFocalLength());
                        picturesParseObject.put("focalLength", focalLength);
                        getDeviceName();
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                        camera.setParameters(params);

                        camera.startPreview();
                        Log.d("Focal Length: ", "focal : " + focalLength);

                    }
                } catch (IOException e) {
                    Log.e("ERROR", "Error setting up preview", e);
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {

                camera.startPreview();
                camera.autoFocus(null);
            }

            public void surfaceDestroyed(SurfaceHolder holder) {

                camera.stopPreview();
                //camera.release();

            }

        });
    }

    public void getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String hardware = Build.HARDWARE;
        String device = Build.DEVICE;
        String brand = Build.BRAND;

        Log.d("DEVICEEEE : ", "manu : "+manufacturer +" model : "+model+" hardware : "+hardware+" device : "+device+" brand : "+brand);

        if (manufacturer.startsWith("Sony")){
            if (model.startsWith("D6503")) {
                picturesParseObject.put("SensorSize",SONY_XPERIA_Z2_SENSOR_SIZE);
                Log.d("SENSOR SIZE : ", "SONY : " + SONY_XPERIA_Z2_SENSOR_SIZE);
            }
        }
        else
        if (manufacturer.startsWith("Sony")){
            if (model.startsWith("Xperia")) {
                picturesParseObject.put("SensorSize", SONY_XPERIA_SOLA_SENSOR_SIZE);
                Log.d("SENSOR SIZE : ", "SONY : " + SONY_XPERIA_SOLA_SENSOR_SIZE);
            }
        }
        else
        if (manufacturer.startsWith("asus")){
            picturesParseObject.put("SensorSize", ASUS_ZENFONE_5_SENSOR_SIZE);
            Log.d("SENSOR SIZE : ", "ASUS : " + ASUS_ZENFONE_5_SENSOR_SIZE);
        }
    }


    private void saveScaledPhoto(byte[] data) {

        try {
            //Burada telefonların desteklediği çözünürlükleri alabiliyoruz lakin yukarıda statik olarak
            //640x480 tanımlanması kararına vardım çünkü her telefonun desteklediği aralıklar
            //farklı olmakta bu da OutOfMemory gibi hatalara sebebiyet vermekte. (Kerem)
            List<Camera.Size> sizes = camera.getParameters().getSupportedPictureSizes();
            Camera.Size result;

            for(int i = 0; i < sizes.size(); i++){
                result = sizes.get(i);

                Log.i("Size", "Supported Width = " + result.width + "Supported Height = " + result.height);
                float carpim = ((result.width * result.height) / 1024000);

                /*if (Math.round(carpim) == 1){
                    pictureWidth = result.width;
                    pictureHeight = result.height;
                    break;
                }*/
            }

            //pictureWidth ile pictureHeight en üstte statik tanımlandı. (width = 640 ,height = 480 )

            Bitmap plantImage = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap plantImageScaled = Bitmap.createScaledBitmap(plantImage, pictureWidth, pictureHeight, false);


            pictureCache = new PictureCache();
            // Android'in varsayılan landscape fotoğraf biçimlendirmesi matrisin 90 derece döndürülmesi
            // ile ekarte ediliyor. Dolayısı ile width = 480, height = 640 oluyor.
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedScaledPlantImage = Bitmap.createBitmap(plantImageScaled, 0,
                    0, plantImageScaled.getWidth(), plantImageScaled.getHeight(),
                    matrix, true);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            rotatedScaledPlantImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            scaledData = bos.toByteArray();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.d("SaveScaledError", e.getMessage());

        }

        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        aDB.setCancelable(false);
        aDB.setTitle("Emin misiniz ?");
        aDB.setMessage("Çektiğiniz resim analizde kullanılacaktır. Devam etmek istiyor musunuz ?.. ");
        aDB.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    if (isTac) {

                        pictureCache.setByteArrayTac(scaledData);
                        isTac = false;
                        isCanak = true;
                        Toast.makeText(getApplicationContext(), "Taç yaprak görüntüsü alındı.", Toast.LENGTH_LONG).show();
                        //String currentTimeStamp = getCurrentTimeStamp();
                        fileName = "TacYaprak";

                        new AsyncUpload().execute(fileName);

                    } else if (isCanak) {

                        pictureCache.setByteArrayCanak(scaledData);
                        isCanak = false;
                        Toast.makeText(getApplicationContext(), "Çanak yaprak görüntüsü alındı.", Toast.LENGTH_LONG).show();
                        //String currentTimeStamp = getCurrentTimeStamp();
                        fileName = "CanakYaprak";

                        new AsyncUpload().execute(fileName);

                    }
                    if (!isTac && !isCanak)
                    {

                        finish();
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.d("ERROR IF", e.getMessage());
                }
            }


        });
        aDB.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = aDB.create();
        alertDialog.show();
    }

    public void startUpload(String fileName) {

        try {

            photoFile = new ParseFile(fileName, scaledData);
            parseGeoPoint = new ParseGeoPoint(latitude, longtitude);
            //ExifInterface exif = new ExifInterface()
            picturesParseObject.put("location", parseGeoPoint);
            picturesParseObject.put(fileName, photoFile);
            picturesParseObject.put("specy", filenameIntent);

            picturesParseObject.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Buluta yükleme başarılı. ", Toast.LENGTH_LONG).show();

                    if (pdialog != null) {
                        pdialog.dismiss();//Eğer işlem başarılı ise asenkron sınıfta yaratılan progressbar ı kapat.

                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Hata" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }});

        } catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Bağlantı Hatası !", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onResume() {
        super.onResume();


        if (camera == null) {
            try {

                camera = Camera.open();
                photoButton.setEnabled(true);
            } catch (Exception e) {
                photoButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Kamera Bulunamadı !",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {

        super.onPause();

        /*if(camera != null)
        {
            camera.stopPreview();
            camera.release();
        }*/
    }

    @Override
    public void onDestroy() {

        if(pdialog!=null)pdialog.dismiss();
        super.onDestroy();
    }

    public class AsyncUpload extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(TakePictureTrainOnline.this);
            pdialog.setMessage("Yükleniyor...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... fileNames) {

            return fileNames[0];

        }

        @Override
        protected void onPostExecute(String name) {

            startUpload(name);

            super.onPostExecute(name);

        }
    }

}
