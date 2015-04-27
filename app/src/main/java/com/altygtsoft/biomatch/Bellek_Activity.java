package com.altygtsoft.biomatch;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Bellek_Activity extends ActionBarActivity {

    Button buttonresimsec;
    Button buttonmap;
    ImageView iv;
    private static final int SELECTED_IMAGE=1;
    public static String filePath;
    public static boolean isSelectedPath = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bellek_);

        startCast();
    }

    void startCast() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();

            buttonresimsec = (Button) findViewById(R.id.Sec);
            buttonmap = (Button) findViewById(R.id.harita);
            iv = (ImageView) findViewById(R.id.imageView);

            buttonmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Bellek_Activity.this, LocationActivity.class);


                    if (isSelectedPath) {
                        i.putExtra("path", filePath);
                    }

                    i.setClass(getApplicationContext(), LocationActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "PATH =" + filePath, Toast.LENGTH_LONG).show();

                }
            });
            buttonresimsec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECTED_IMAGE);

                    isSelectedPath = true;

                }
            });
        }
    }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch(requestCode){
                case SELECTED_IMAGE:
                    if(resultCode==RESULT_OK){
                        Uri uri=data.getData();
                        String[]projection={MediaStore.Images.Media.DATA};
                        Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
                        cursor.moveToFirst();
                        int columnIndex=cursor.getColumnIndex(projection[0]);
                        filePath=cursor.getString(columnIndex);
                        cursor.close();
                        Bitmap SelectedImage= BitmapFactory.decodeFile(filePath);
                        Drawable d=new BitmapDrawable(SelectedImage);
                        iv.setBackground(d);


                        Context c=getApplicationContext();

                        Toast.makeText(getApplicationContext(), "PATH ="+filePath, Toast.LENGTH_LONG).show();

                    }
            }


        }

}
