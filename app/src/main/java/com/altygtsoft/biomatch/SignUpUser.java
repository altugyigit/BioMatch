package com.altygtsoft.biomatch;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/*
 * Created by Altuð on 22.4.2015.
 */
public class SignUpUser {

    public SignUpUser(final Context context, String userName, String passwordUser)
    {
        if (userName.equals("") && passwordUser.equals("")) {
            Toast.makeText(context.getApplicationContext() ,
                    "Alanlar Boþ Býrakýlamaz !",
                    Toast.LENGTH_LONG).show();

        } else {
            // Parse a kullanýcýyý kaydet.
            ParseUser user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(passwordUser);
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Herhangi bir hata yok ise internet durumu dahil.
                        Toast.makeText(context.getApplicationContext(),
                                "Kayýt Etme Baþarýlý .",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "HATA Oluþtu Tekrar Deneyiniz !", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }
    }

}
