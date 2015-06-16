package com.altygtsoft.biomatch;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class TypesActivity extends ActionBarActivity {

    ListView lwSpecies;
    ArrayList<ParseObject> speciesObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);

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

        lwSpecies = (ListView)findViewById(R.id.lwSpecies);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Species");
        query.whereExists("name_turkish");
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                speciesObjectList = new ArrayList<>(list);

                ListViewAdapter lwAdapter = new ListViewAdapter(TypesActivity.this, speciesObjectList);

                lwSpecies.setAdapter(lwAdapter);

            }
        });



    }

}
