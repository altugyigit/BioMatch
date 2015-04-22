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

import com.parse.ParseUser;


public class CobanActivity extends ActionBarActivity {

    public TextView txtWelcome;
    public Button takePicCoban;
    public RadioButton radioOffline;
    public RadioButton radioOnline;
    public Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coban);
        startCast();
    }

    private void startCast() {

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
        takePicCoban = (Button)findViewById(R.id.takePictureCoban);
        radioOffline = (RadioButton)findViewById(R.id.yerelkayitButton);
        radioOnline = (RadioButton)findViewById(R.id.cicikayitButton);
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        //Kullanýcý adýný ekrana yaz.
        txtWelcome.setText(struser + txtWelcome.getText().toString());
        takePicCoban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioOnline.isChecked()) {
                    Intent intent = new Intent(CobanActivity.this.getApplicationContext(), TakePictureActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CobanActivity.this.getApplicationContext(), TakePictureOfflineActivity.class);
                    startActivity(intent);
                }

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
