package com.altygtsoft.biomatch;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SignUpActivity extends ActionBarActivity {

    public Button btnSignUp;
    public TextView txtUserName;
    public TextView txtPassword;
    public TextView txtPassword2;
    public String userName;
    public String passwordUser;
    public String passwordUser2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
            adialog.setTitle("Baðlantý Hatasý");
            adialog.setMessage("Baðlantý bulunamadý !");
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

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtPassword = (TextView)findViewById(R.id.txtPassword);
        txtPassword2 = (TextView)findViewById(R.id.txtPassword2);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = txtUserName.getText().toString();
                passwordUser = txtPassword.getText().toString();
                passwordUser2 = txtPassword2.getText().toString();

                if(passwordUser.equals(passwordUser2))
                {
                    SignUpUser signUpUser = new SignUpUser(getApplicationContext(), userName, passwordUser);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Þifreler eþleþmiyor !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
