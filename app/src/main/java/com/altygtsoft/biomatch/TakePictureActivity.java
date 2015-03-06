package com.altygtsoft.biomatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseFile;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class TakePictureActivity extends ActionBarActivity {

    private Camera camera;
    private SurfaceView surfaceView;
    private ParseFile photoFile;
    private ImageButton photoButton;
    private Pictures pictures;
    public PictureCache pictureCache;

    public boolean isTac = true;
    public boolean isCanak = false;
    public boolean isYaprak = false;

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
        setContentView(R.layout.activity_take_picture);

        startCast();
    }

    private void startCast()
    {
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
                // nothing to do here
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // nothing here
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
        Bitmap mealImage = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap mealImageScaled = Bitmap.createScaledBitmap(mealImage, 1600, 900, false);
        pictureCache = new PictureCache();
        // Override Android default landscape orientation and save portrait
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
                0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
                matrix, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        final byte[] scaledData = bos.toByteArray();

        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        aDB.setCancelable(false);
        aDB.setTitle("Emin misiniz ?");
        aDB.setMessage("Çektiğiniz resim analizde kullanılacaktır. Devam etmek istiyor musunuz ?");
        aDB.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(isTac)
                {
                    pictureCache.setByteArrayTac(scaledData);
                    isTac = false;
                    isCanak = true;
                }
                else if(isCanak)
                {
                    pictureCache.setByteArrayTac(scaledData);
                    isCanak = false;
                    isYaprak = true;
                }
                else if(isYaprak)
                {
                    pictureCache.setByteArrayTac(scaledData);
                    isYaprak = false;
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

        /****************************************************************************************************************************************/
        // Save the scaled image to Parse
        photoFile = new ParseFile("photo1.jpg", scaledData);

        pictures.setPhotoFile12(photoFile);
        try
        {
            pictures.saveInBackground();
            Toast.makeText(getApplicationContext(),"Başarılı ",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Bağlantı Hatası !",Toast.LENGTH_LONG).show();
        }

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


        Toast.makeText(getApplicationContext(),"RESİMLER AYNI !",Toast.LENGTH_LONG).show();





        /*************************************************************************************************************************************/
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

        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}