package com.altygtsoft.biomatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Turler> turlerList;

    public ListViewAdapter(Context context) {

        this.context = context;

    }

    @Override
    public int getCount() {

        return turlerList.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            //View itemViewdersplist = inflater.inflate(R.layout.ders_list, parent, false);


            /*ParseQuery<Turler> ogrenciDersParseQuery = ParseQuery.getQuery(Turler.class);

            List<Turler> turlerParseList = null;
            try {

                ogrencidersParseList = ogrenciDersParseQuery.find();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(ogrencidersParseList == null)
                    {
                        Toast.makeText(context2, "Kayıt Yok !", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        for(OgrenciDers o: ogrencidersParseList)
                        {
                            txtOgrenciNo.setText(o.getogrencino());
                            txtOgrenciAdi.setText(o.getogrenciadi());
                            txtOgrenciSoyadi.setText(o.getogrencisoyadi());
                        }

                    }

            return itemViewyoklama;*/

        return null;
        }

}