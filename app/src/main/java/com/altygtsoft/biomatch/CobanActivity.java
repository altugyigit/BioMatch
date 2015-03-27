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


public class CobanActivity extends ActionBarActivity {

    public Button takePicCoban;
    public RadioButton radioOffline;
    public RadioButton radioOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coban);
        startCast();
    }

    private void startCast() {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(205, 200, 177)));
            actionBar.setTitle((Html.fromHtml("<font color=\"#3b414a\">" + getString(R.string.app_name) + "</font>")));

            actionBar.show();
        }

        takePicCoban = (Button)findViewById(R.id.takePictureCoban);
        radioOffline = (RadioButton)findViewById(R.id.yerelkayitButton);
        radioOnline = (RadioButton)findViewById(R.id.cicikayitButton);

        takePicCoban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioOnline.isChecked())
                {
                    Intent intent = new Intent(CobanActivity.this.getApplicationContext(), TakePictureActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(CobanActivity.this.getApplicationContext(), TakePictureOfflineActivity.class);
                    startActivity(intent);
                }

            }
        });



    }

}
