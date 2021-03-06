package com.altygtsoft.biomatch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.altygtsoft.biomatch.GPSTracker;
import com.altygtsoft.biomatch.PictureCache;
import com.altygtsoft.biomatch.Pictures;
import com.altygtsoft.biomatch.R;
//import com.altygtsoft.biomatch.RabbitMQConn;
import com.google.android.gms.location.LocationRequest;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomBioRecordActivity extends ActionBarActivity {

    public String lastId = "start";
    public static Camera camera;
    public static final int pictureHeight = 480;
    public static final int pictureWidth = 640;
    private SurfaceView surfaceView;
    public static ParseFile photoFile;
    private ImageButton photoButton;
    public static CustomPictures customPictures;
    public Calendar c;
    public static String fileName;
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
    public static String from;
    public static ParseGeoPoint parseGeoPoint;
    public static String SONY_XPERIA_Z2_SENSOR_SIZE = "4.55";
    public static String ASUS_ZENFONE_5_SENSOR_SIZE = "3.6";
    public static String SONY_XPERIA_SOLA_SENSOR_SIZE = "3.6";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customPictures = new CustomPictures();
        latlon = new double[2];
        super.onCreate(savedInstanceState);

        gpsTracker = new GPSTracker(CustomBioRecordActivity.this);

        if (gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longtitude = gpsTracker.getLongtitude();

            Toast.makeText(getApplicationContext(), "Konumumuzun enlemi = " + latitude + " boylamı ise = " + longtitude, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Hala konumda sıkıntı var ..", Toast.LENGTH_LONG).show();

        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latlon = getLocation();*/


        setContentView(R.layout.activity_custom_bio_record);

        startCast();
    }

   /* public void setLocation(Location loc){
        Log.d("SETLOCATIONVALUES", loc.getLatitude() + " " + loc.getLongitude() );
    }

        public double[] getLocation () {

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    setLocation(location);
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        double[] coordinates = new double[2];

        if (latitude != 0 || longtitude != 0) { //koordinatların listener'da doldurulup doldurulmadığını kontrol ediyoruz
            coordinates[0] = latitude;
            coordinates[1] = longtitude;
        } else                                    //Dolmadıysa metodu tekrar çağırıyoruz.
            Log.d("LATLONError", latitude + " " + longtitude);


        return coordinates;


    }*/


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

                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                        camera.setParameters(params);

                        camera.startPreview();
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

    public static String getCurrentTimeStamp(){
        try {

            Calendar c = Calendar.getInstance();
            int date = c.get(Calendar.DATE);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);
            int seconds = c.get(Calendar.SECOND);

            String stamp = String.valueOf(date) +"_"+ String.valueOf(month)+"_" + String.valueOf(year)
                    +"_"+ String.valueOf(hour)+"_"+ String.valueOf(minute)+"_"+"_"+String.valueOf(seconds);

            return stamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
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
        aDB.setMessage("Çektiğiniz resim kişisel tablonuza kayıt edilecek. Devam etmek istiyor musunuz ?.. ");
        aDB.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                    fileName = getCurrentTimeStamp();
                    new AsyncUpload().execute(fileName);

                    finish();

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

            customPictures.setLocation(parseGeoPoint);
            customPictures.setPhotoFile(photoFile);


            // pictures.save();// Telefon çekirdeğine göre 2 asenkron methodu desteklemiyor o yüzden sadece save yazılabilir fakat başarılı kontolü SaveCallback' te yakalanamaz.

            customPictures.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(CustomBioRecordActivity.this.getApplicationContext(), "Buluta yükleme başarılı. ", Toast.LENGTH_LONG).show();


                        if (pdialog != null) {
                            pdialog.dismiss();//Eğer işlem başarılı ise asenkron sınıfta yaratılan progressbar ı kapat.
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Hata" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Bağlantı Hatası !",Toast.LENGTH_LONG).show();
        }
    }


    /*public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.forLanguageTag("TR"));
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

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
        if(pdialog != null)pdialog.dismiss();
        super.onDestroy();
    }

    public class AsyncUpload extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(CustomBioRecordActivity.this);
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
    @Override
    public void onResume() {
        super.onResume();

        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 360000, 1000, locationListener);
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

}
