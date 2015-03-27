package com.altygtsoft.biomatch;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TakePictureOfflineActivity extends ActionBarActivity {

    public static Camera camera;
    public int pictureHeight = 2592;
    public int pictureWidth = 1944;
    private SurfaceView surfaceView;
    public static ParseFile photoFile;
    private ImageButton photoButton;
    public static Pictures pictures;
    public PictureCache pictureCache;
    public Calendar c;
    public static String fileName;
    public static byte[] scaledData;
    public static ProgressDialog pdialog;

    public static boolean isTac = true;
    public static boolean isCanak = false;
    public static boolean isYaprak = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pictures = new Pictures();
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_take_picture_offline);

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

        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (camera == null)
                    return;

                camera.takePicture(new Camera.ShutterCallback() {

                    @Override
                    public void onShutter() {
                        // nothing to do
                    }

                }, null, new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        saveScaledPhoto(data);
                        if (camera != null)
                        {
                            camera.startPreview();
                        }
                    }

                });

            }
        });

        surfaceView = (SurfaceView)findViewById(R.id.camera_surface_view);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (camera != null) {
                        camera.setDisplayOrientation(90);
                        camera.setPreviewDisplay(holder);
                        camera.startPreview();
                    }
                } catch (IOException e) {
                    Log.e("ERROR", "Error setting up preview", e);
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {

                Camera.Parameters params = camera.getParameters();
                //params.setPictureFormat(PixelFormat.JPEG);

                camera.setParameters(params);
                camera.startPreview();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {

                camera.stopPreview();
                camera.release();
            }

        });
    }

    /*
     * ParseQueryAdapter loads ParseFiles into a ParseImageView at whatever size
     * they are saved. Since we never need a full-size image in our app, we'll
     * save a scaled one right away.
     */
    private void saveScaledPhoto(byte[] data) {

        // Resize photo from camera byte array
        pictureWidth = camera.getParameters().getPictureSize().width;
        pictureHeight = camera.getParameters().getPictureSize().height;
        Bitmap mealImage = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap mealImageScaled = Bitmap.createScaledBitmap(mealImage, pictureWidth, pictureHeight, false);
        pictureCache = new PictureCache();
        // Override Android default landscape orientation and save portrait
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
                0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
                matrix, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rotatedScaledMealImage.compress(Bitmap.CompressFormat.PNG, 100, bos);

        scaledData = bos.toByteArray();

        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        aDB.setCancelable(false);
        aDB.setTitle("Emin misiniz ?");
        aDB.setMessage("Çektiğiniz resim analizde kullanılacaktır. Devam etmek istiyor musunuz ?..");
        aDB.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(isTac)
                {

                    pictureCache.setByteArrayTac(scaledData);
                    isTac = false;
                    isCanak = true;
                    Toast.makeText(getApplicationContext(), "Taç yaprak görüntüsü alındı.", Toast.LENGTH_LONG).show();
                    String currentTimeStamp = getCurrentTimeStamp();
                    fileName = "TacYaprak";

                    new AsyncUpload().execute(fileName);

                }
                else if(isCanak)
                {


                    pictureCache.setByteArrayCanak(scaledData);
                    isCanak = false;
                    isYaprak = true;
                    Toast.makeText(getApplicationContext(), "Çanak yaprak görüntüsü alındı.", Toast.LENGTH_LONG).show();
                    String currentTimeStamp = getCurrentTimeStamp();
                    fileName = "CanakYaprak";

                    new AsyncUpload().execute(fileName);
                }
                else if(isYaprak)
                {

                    String plantTag = "A_Y";
                    pictureCache.setByteArrayYaprak(scaledData);
                    isYaprak = false;
                    Toast.makeText(getApplicationContext(), "Ağaç yaprağı görüntüsü alındı.", Toast.LENGTH_LONG).show();
                    String currentTimeStamp = getCurrentTimeStamp();
                    fileName = "AgacYapragi";

                    new AsyncUpload().execute(fileName);
                }

                if(!isTac && !isCanak && !isYaprak)
                {
                    finish();
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

        try
        {
            photoFile = new ParseFile(fileName, scaledData);
            if (isTac) {
                pictures.setPhotoFile12(photoFile);
            }
            else if (isCanak){
                pictures.setPhotoFile22(photoFile);
            }
            else if (isYaprak){
                pictures.setPhotoFile32(photoFile);
            }


            //pictures.save();// Telefon çekirdeğine göre 2 asenkron methodu desteklemiyor o yüzden sadece save yazılabilir fakat başarılı kontolü SaveCallback' te yakalanamaz.

            pictures.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Toast.makeText(getApplicationContext(),"Buluta yükleme başarılı. " , Toast.LENGTH_LONG).show();
                        if(pdialog != null)
                        {
                            pdialog.dismiss();//Eğer işlem başarılı ise asenkron sınıfta yaratılan progressbar ı kapat.
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Hata" +e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Bağlantı Hatası !",Toast.LENGTH_LONG).show();
        }
    }

    /****************************************************************************************************************************************/



    /*************************************************************************************************************************************/


    //KARŞILAŞTIRMA İÇİN YAZILACAK KOD

        /*Mat img1 = Highgui.imread(Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.png");
        Mat img2 = Highgui.imread(Environment.getExternalStorageDirectory().getAbsolutePath() + "/2.png");

        Mat hist0 = new Mat();
        Mat hist1 = new Mat();

        int hist_bins = 30;           //number of histogram bins
        int hist_range[]= {0,180};//histogram range
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Arrays.asList(img1), new MatOfInt(0), new Mat(), hist0, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(img2), new MatOfInt(0), new Mat(), hist1, histSize, ranges);

        Toast.makeText(getApplicationContext()," HIGUI =" + Highgui.imread(Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.png"), Toast.LENGTH_LONG).show();
         Toast.makeText(getApplicationContext()," CANNY = " + Imgproc.compareHist(img1 , img2, Imgproc.CV_CANNY_L2_GRADIENT),Toast.LENGTH_LONG).show();*/


    // Toast.makeText(getApplicationContext(),"RESİMLER AYNI !",Toast.LENGTH_LONG).show();





    /*************************************************************************************************************************************/


    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date());

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class AsyncUpload extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(TakePictureOfflineActivity.this);
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
