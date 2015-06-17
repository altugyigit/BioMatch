package com.altygtsoft.biomatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.UnsupportedEncodingException;

public class MainActivity extends ActionBarActivity {

    public Button btnOffline;
    public Button btnLogin;
    public TextView txtUserName;
    public TextView txtPassword;
    public String userName;
    public String passwordUser;
    public RadioButton cobanRadio;
    public RadioButton biyologRadio;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*//SERVERA OBJECTID AT.
        Thread thread = new Thread() {

            @Override
            public void run() {
                RabbitMQConn rabbitMQConn = new RabbitMQConn();

                rabbitMQConn.rabbitMQSend("ANDROID DENEME !!!");
            }
        };
        thread.start();*/

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

    void showDialog()
    {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Doğrulanıyor...");
        progressDialog.setMessage("Lütfen bekleyin");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    void closeDialog()
    {
        if(progressDialog != null)
        {
            progressDialog.dismiss();
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

        btnOffline = (Button)findViewById(R.id.btnOffline);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtPassword = (TextView)findViewById(R.id.txtPassword);
        cobanRadio = (RadioButton)findViewById(R.id.cobanButton);
        biyologRadio = (RadioButton) findViewById(R.id.biyologButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isConnected();
                if (!txtUserName.getText().toString().isEmpty() || !txtPassword.getText().toString().isEmpty()) {
                    userName = txtUserName.getText().toString();
                    passwordUser = txtPassword.getText().toString();
                    //Önemli olan Parse tan kontrol ile gerçekleşen login işlemidir.
                    showDialog();
                    ParseUser.logInInBackground(userName, passwordUser,
                            new LogInCallback() {

                                @Override
                                public void done(ParseUser parseUser, ParseException e) {

                                    if (parseUser != null) {
                                        if (cobanRadio.isChecked()) {
                                            Toast.makeText(getApplicationContext(), "Hoşgeldin ...", Toast.LENGTH_LONG).show();
                                            Intent intentCoban = new Intent(MainActivity.this.getApplicationContext(), CobanActivity.class);
                                            startActivity(intentCoban);
                                            closeDialog();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Hoşgeldiniz ...", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(MainActivity.this.getApplicationContext(), WelcomeActivity.class);
                                            startActivity(intent);
                                            closeDialog();
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Kullanıcı adı yada şifre yanlış !", Toast.LENGTH_LONG).show();
                                        closeDialog();
                                    }
                                }
                            }
                    );
                } else {
                    Toast.makeText(getApplicationContext(), "Alanlar Boş Bırakılamaz !", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this.getApplicationContext(), TakePictureOfflineActivity.class);
                startActivity(intent);

            }
        });

    }




}
