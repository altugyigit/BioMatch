package com.altygtsoft.biomatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;


public class activity_custom_map_info extends ActionBarActivity {
    ImageView iv;
    Bitmap image ;
    ProgressDialog pd;
    String rank;
    String country;
    String population;
    String flag;
    String position;
    ImageLoader imageLoader = new ImageLoader(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_custom_map_info);



        // Locate the TextViews in singleitemview.xml


        // Locate the ImageView in singleitemview.xml




        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

      /*  Intent i = getIntent();
        // Get the result of rank

        // Get the result of flag
        flag = i.getStringExtra("TacYaprak");

        // Locate the TextViews in singleitemview.xml


        // Locate the ImageView in singleitemview.xml
        ImageView imgflag = (ImageView) findViewById(R.id.leafimage);

        // Set results to the TextViews


        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(flag, imgflag);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_custom_map_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   /* public class TheTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // pd.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try
            {
                //URL url = new URL( "http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png");


                image = downloadBitmap("http://files.parsetfss.com/a8b3b5d9-bfd6-4a4c-bc27-f1e950988ff4/tfss-3ed40709-f04f-4c46-a5fc-1d600d8fe0cd-TacYaprak");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            super.onPostExecute(result);

            // pd.dismiss();
            if(image!=null)
            {
                iv.setImageBitmap(image);
            }

        }
    }
    private Bitmap downloadBitmap(String url) {
        // initilize the default HTTP client object
        final DefaultHttpClient client = new DefaultHttpClient();

        //forming a HttoGet request
        final HttpGet getRequest = new HttpGet(url);
        try {

            HttpResponse response = client.execute(getRequest);

            //check 200 OK for success
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {

                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;

            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    // getting contents from the stream
                    inputStream = entity.getContent();

                    // decoding stream data back into image Bitmap that android understands
                    image = BitmapFactory.decodeStream(inputStream);


                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // You Could provide a more explicit error message for IOException
            getRequest.abort();
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + url + e.toString());
        }

        return image;
    }*/
}
