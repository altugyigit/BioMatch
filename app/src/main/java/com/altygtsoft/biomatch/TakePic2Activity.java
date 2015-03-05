package com.altygtsoft.biomatch;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;


public class TakePic2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic2);

        compareImage();
    }

    private void compareImage() {

        try
        {
            Mat img1 = Highgui.imread("mnt/sdcard/1.png");
            Mat img2 = Highgui.imread("mnt/sdcard/2.png");

            if(img1 == null || img2 == null)
            {

                Toast.makeText(getApplicationContext(),"Yol BULUNAMADI !",Toast.LENGTH_LONG).show();


            }

            Mat result = new Mat();

            Core.compare(img1, img2, result, Core.CMP_NE);

            int val = Core.countNonZero(result);

            if(val == 0) {

                Toast.makeText(getApplicationContext(),"Resimler Aynı !",Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(),"Resimler Farklı !",Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"HATA !" + ex.toString(),Toast.LENGTH_LONG).show();
        }


    }


}
