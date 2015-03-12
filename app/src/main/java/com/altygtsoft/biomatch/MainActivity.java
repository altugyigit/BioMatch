package com.altygtsoft.biomatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends ActionBarActivity {

    public Button btnLogin;
    public TextView txtUserName;
    public TextView txtPassword;
    public RadioButton cobanRadio;
    public RadioButton biyologRadio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            isConnected();

            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "NJatHHRn60LHWQGuhgdqPpk3bCTlglQVJkYx5dmG", "7o2tdVTybQF66WXnyqgwg6FcCUQEam4cQ63528qw");
            ParseObject.registerSubclass(Pictures.class);

            startCast();
    }

    private void isConnected()
    {
        ConnectivityManager connmanager = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connmanager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {

        }
        else
        {
            AlertDialog adialog = new AlertDialog.Builder(this).create();
            adialog.setCancelable(false);
            adialog.setTitle("Bağlantı Hatası");
            adialog.setMessage("Bağlantı bulunamadı !");
            adialog.setButton("Tamam",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                    System.exit(0);

                }
            });
            adialog.show();
        }
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

        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtPassword = (TextView)findViewById(R.id.txtPassword);
        cobanRadio = (RadioButton)findViewById(R.id.cobanButton);
        biyologRadio = (RadioButton)findViewById(R.id.biyologButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!txtUserName.getText().toString().isEmpty() || !txtPassword.getText().toString().isEmpty())
                {
                    if (txtUserName.getText().toString().equals("admin"))
                    {
                        if (txtPassword.getText().toString().equals("123"))
                        {
                            if(cobanRadio.isChecked()) {
                                Toast.makeText(getApplicationContext(), "Hoşgeldin", Toast.LENGTH_LONG).show();
                                Intent intentCoban = new Intent(MainActivity.this.getApplicationContext(), CobanActivity.class);
                                startActivity(intentCoban);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Hoşgeldiniz", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this.getApplicationContext(), WelcomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Kullanıcı adı yada şifre yanlış !", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Alanlar Boş Bırakılamaz !", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
