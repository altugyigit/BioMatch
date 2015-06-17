package com.altygtsoft.biomatch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

import org.w3c.dom.Text;


public class WelcomeActivity extends ActionBarActivity {

    public Button btnCustomBioRecord;
    public TextView txtWelcome;
    public TextView txtUserName;
    public Button btnStartAnalyze;
    public Button btnPreviousAnalyze;
    public Button btnTypes;
    public Button btnMap;
    public Button btnSignUp;
    public Button btnLogOut;
    public Button btnTrain;
    public GPSConnectionClass gpsConnectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        gpsConnectionClass = new GPSConnectionClass(WelcomeActivity.this);
        startCast();
        verifyGPS();
    }

    void verifyGPS()
    {
        gpsConnectionClass.verifyGPS();//GPS Acildi mi ? acilmadiysa actir.
    }

    void startCast()
    {
        Parse.initialize(WelcomeActivity.this, "HgrrtDO2dnazkQCPY59MR82ERhiamS5b1LTXBit8", "FS2hiyTi5uYVqz392tA39aXHYxubPdsGv28IiJ5Y");
        //Login olmus kisiyi al.
        ParseUser currentUser = ParseUser.getCurrentUser();
        String struser = currentUser.getUsername();

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();
        }

        btnCustomBioRecord = (Button)findViewById(R.id.btnCustomBioRecord);
        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        btnStartAnalyze = (Button)findViewById(R.id.btnStartAnalyze);
        btnPreviousAnalyze = (Button)findViewById(R.id.btnPreviousAnalyze);
        btnTypes = (Button)findViewById(R.id.btnType);
        btnMap = (Button)findViewById(R.id.btnMap);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        btnTrain = (Button)findViewById(R.id.btnTrain);
        //Kullanıcı adını ekrana yaz.
        txtUserName.setText(struser.toUpperCase());

        btnCustomBioRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext(), CustomBioRecordActivity.class);
                startActivity(intent);

            }
        });

        btnStartAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,ImageManipulationsActivity.class);
                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext(), PicPicker.class);
                startActivity(intent);

            }
        });

        btnPreviousAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,PreviousAnalyzeActivity.class);
                startActivity(intent);

            }
        });
        btnTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,TypesActivity.class);
                startActivity(intent);

            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,MapActivty.class);
                //Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,ImageManipulationsActivity.class);
                startActivity(intent);

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,SignUpActivity.class);
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
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext(), DialogActivity.class);
                startActivity(intent);
            }
        });
    }
}
