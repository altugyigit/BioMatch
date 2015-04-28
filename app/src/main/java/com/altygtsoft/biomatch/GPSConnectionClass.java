package com.altygtsoft.biomatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/*
 * Created by Altuğ on 29.4.2015.
 */
public class GPSConnectionClass
{

    private Context context;
    public GPSConnectionClass(Context context)
    {
       this.context = context;
    }

    public void goToTurnOnScreenGPS()
    {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    public void verifyGPS()
    {
        // Kontrolleri gerceklestir.
        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Acik mi ?
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("GPS Acik Degil");
            builder.setMessage("Lutfen aciniz ...");
            builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

}
