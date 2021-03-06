package com.altygtsoft.biomatch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class CobanActivity extends ActionBarActivity {

    public TextView txtWelcome;
    public TextView txtUserName;
    public Button takePicCoban;
    public Button btnLogOut;
    public Button btnuploadOffline;
    public Button btnSec;
    public GPSConnectionClass gpsConnectionClass;
    public GetLatLon getLatLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coban);
        getLatLon = new GetLatLon(this);
        gpsConnectionClass = new GPSConnectionClass(CobanActivity.this);
        startCast();
        verifyGPS();
    }

    void verifyGPS()
    {
        gpsConnectionClass.verifyGPS();//GPS Acildi mi ? acilmadiysa actir.
    }

    private void startCast() {

        //Login olmuş kişiyi al.
        Parse.initialize(CobanActivity.this, "HgrrtDO2dnazkQCPY59MR82ERhiamS5b1LTXBit8", "FS2hiyTi5uYVqz392tA39aXHYxubPdsGv28IiJ5Y");
        ParseUser currentUser = ParseUser.getCurrentUser();
        String struser = currentUser.getUsername().toUpperCase();
        getLatLon.getLatLon();
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();
        }

        btnSec = (Button)findViewById(R.id.btnSec);
        btnuploadOffline = (Button)findViewById(R.id.btnUploadOffline);
        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        takePicCoban = (Button)findViewById(R.id.takePictureCoban);
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        //Kullanıcı adını ekrana yaz.
        txtUserName = (TextView)findViewById(R.id.txtCobanUserName);
        txtUserName.setText(struser);
        takePicCoban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CobanActivity.this.getApplicationContext(), TakePictureActivity.class);
                intent.putExtra("from", "picture");
                startActivity(intent);
            }
        });
        btnuploadOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CobanActivity.this.getApplicationContext(), TakePictureOfflineActivity.class);
                startActivity(intent);

            }
        });
        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CobanActivity.this.getApplicationContext(), PicPicker.class);
                startActivity(intent);

            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logOut();
                finish();
            }
        });
    }
}
