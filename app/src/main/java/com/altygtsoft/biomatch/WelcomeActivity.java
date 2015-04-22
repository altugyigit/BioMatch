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

import com.parse.ParseUser;


public class WelcomeActivity extends ActionBarActivity {

    public TextView txtWelcome;
    public Button btnStartAnalyze;
    public Button btnPreviousAnalyze;
    public Button btnTypes;
    public Button btnMap;
    public Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startCast();
    }

    void startCast()
    {
        //Login olmuþ kiþiyi al.
        ParseUser currentUser = ParseUser.getCurrentUser();
        String struser = currentUser.getUsername().toString();

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();
        }

        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        btnStartAnalyze = (Button)findViewById(R.id.btnStartAnalyze);
        btnPreviousAnalyze = (Button)findViewById(R.id.btnPreviousAnalyze);
        btnTypes = (Button)findViewById(R.id.btnType);
        btnMap = (Button)findViewById(R.id.btnMap);
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        //Kullanýcý adýný ekrana yaz.
        txtWelcome.setText(struser + txtWelcome.getText().toString());
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

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,MapPicPickerActivity.class);
                //Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,ImageManipulationsActivity.class);
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
