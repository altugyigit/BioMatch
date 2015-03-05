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


public class WelcomeActivity extends ActionBarActivity {

    public Button btnStartAnalyze;
    public Button btnStartAnalyze2;
    public Button btnPreviousAnalyze;
    public Button btnTypes;
    public Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startCast();
    }

    void startCast()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();
        }

        btnStartAnalyze = (Button)findViewById(R.id.btnStartAnalyze);
        btnStartAnalyze2 = (Button)findViewById(R.id.btnStartAnalyze2);
        btnPreviousAnalyze = (Button)findViewById(R.id.btnPreviousAnalyze);
        btnTypes = (Button)findViewById(R.id.btnType);
        btnMap = (Button)findViewById(R.id.btnMap);

        btnStartAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,ImageManipulationsActivity.class);
                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,TakePic3Activity.class);
                startActivity(intent);

            }
        });
        btnStartAnalyze2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,PicPicker.class);
                startActivity(intent);

                /*//Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,ImageManipulationsActivity.class);
                Intent intent = new Intent(WelcomeActivity.this.getApplicationContext() ,TakePic2Activity.class);
                startActivity(intent);*/

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
    }
}
