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
import android.widget.Toast;


public class MapActivty extends ActionBarActivity {

    //Button bellek;
    Button haritayaGit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_activty);

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
        //bellek=(Button)findViewById(R.id.bellek);
        haritayaGit=(Button)findViewById(R.id.haritayaGit);
        /*bellek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivty.this,Bellek_Activity.class);
                startActivity(i);

            }
        });*/
        haritayaGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MapActivty.this, LocationActivity.class);
                i.setClass(getApplicationContext(), LocationActivity.class);
                startActivity(i);

            }
        });
    }
}
