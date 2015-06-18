package com.altygtsoft.biomatch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PicPicker extends ActionBarActivity {

    public String lastId = "start";
    public ParseGeoPoint parseGeoPoint;
    public static double latitude = 0;
    public static double longtitude = 0;
    public ImageView imgSelectedTac;
    public Button btnSelectTac;
    public ImageView imgSelectedCanak;
    public Button btnSelectCanak;
    public Button btnSend;
    public boolean imgTac;
    public ProgressDialog pdialog;
    public ParseFile parseFileTac;
    public ParseFile parseFileCanak;
    public Bitmap bmTac;
    public Bitmap bmCanak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_picker);

        imgTac = true;

        startCast();

    }

    private void startCast() {

        imgSelectedTac = (ImageView)findViewById(R.id.imageSelected1);
        imgSelectedCanak = (ImageView)findViewById(R.id.imageSelected2);

        btnSelectTac = (Button)findViewById(R.id.btnSelect1);
        btnSelectCanak = (Button)findViewById(R.id.btnSelect2);

        btnSend = (Button)findViewById(R.id.btnSend);

        btnSelectTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
                /*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
*/
            }
        });

        btnSelectCanak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
                /*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);*/
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imgSelectedTac.getDrawable() == null || imgSelectedCanak == null) {

                    Toast.makeText(getApplicationContext(), "Fotoğraflar Seçilmeli !", Toast.LENGTH_LONG).show();

                }
                else
                {

                    new AsyncUpload().execute("");

                }

            }
        });
    }

    public void openFolder()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        String path = Environment.getExternalStorageDirectory().getPath()
                + "/BioMatch/";
        Uri uri = Uri.parse(path);
        intent.setDataAndType(uri, "image/jpeg");
        startActivity(Intent.createChooser(intent, "Klasör Aç"));
    }
    @Override
    public void onDestroy() {
        pdialog.dismiss();
        //SERVERA OBJECTID AT.
        Thread thread = new Thread() {

            @Override
            public void run() {
                RabbitMQConn rabbitMQConn = new RabbitMQConn();

                rabbitMQConn.rabbitMQSend(lastId);
            }
        };
        thread.start();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && data != null)
        {

            try {

                Uri imageeURI = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageeURI);

                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                if(imgTac)
                {
                    imgSelectedTac.setImageBitmap(selectedImage);
                    imgTac = false;
                }
                else
                {
                    imgSelectedCanak.setImageBitmap(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



        }
    }

    public void startUpload() {

        ParseObject picturesParseObject = ParseObject.create("Pictures");

        bmTac = ((BitmapDrawable)imgSelectedTac.getDrawable()).getBitmap();
        ByteArrayOutputStream streamTac = new ByteArrayOutputStream();
        bmTac.compress(Bitmap.CompressFormat.JPEG, 100, streamTac);
        byte[] byteArrayTac = streamTac.toByteArray();
        parseFileTac = new ParseFile("Tac", byteArrayTac);

        bmCanak = ((BitmapDrawable)imgSelectedCanak.getDrawable()).getBitmap();
        ByteArrayOutputStream streamCanak = new ByteArrayOutputStream();
        bmTac.compress(Bitmap.CompressFormat.JPEG, 100, streamCanak);
        byte[] byteArrayCanak = streamCanak.toByteArray();
        parseFileCanak = new ParseFile("Canak", byteArrayCanak);

        parseGeoPoint = new ParseGeoPoint(latitude, longtitude);

        picturesParseObject.put("location",parseGeoPoint);
        picturesParseObject.put("TacYaprak", parseFileTac);
        picturesParseObject.put("CanakYaprak",parseFileCanak);

        picturesParseObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Buluta yükleme başarılı. ", Toast.LENGTH_LONG).show();
                    //SON KAYDI GETIR.

                    if (lastId.equals("start")) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pictures");
                        query.whereExists("location");
                        query.orderByDescending("createdAt");

                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {

                                List<ParseObject> arrayList = new ArrayList<>(list);

                                lastId = arrayList.get(0).getObjectId();

                                Toast.makeText(getApplicationContext(), "Son ID =" + lastId, Toast.LENGTH_LONG).show();

                            }
                        });

                    }

                    if (pdialog != null) {
                        pdialog.dismiss();//Eğer işlem başarılı ise asenkron sınıfta yaratılan progressbar ı kapat.
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Hata" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class AsyncUpload extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(PicPicker.this);
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

            startUpload();

            super.onPostExecute(name);

        }

}}
