package com.altygtsoft.biomatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

   private Context context;
    private List<ParseObject> speciesObjectList;

    public ListViewAdapter(Context context, List<ParseObject> speciesObjectList)
    {
        this.context = context;
        this.speciesObjectList = speciesObjectList;
    }

    @Override
    public int getCount() {

        return this.speciesObjectList.size();

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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemViewlist = inflater.inflate(R.layout.activity_types, parent, false);

        final TextView twTurk = (TextView)itemViewlist.findViewById(R.id.txtTurk);
        final TextView twLatin = (TextView)itemViewlist.findViewById(R.id.txtLatin);

            twTurk.setText(speciesObjectList.get(position).get("name_turkish").toString());
            twLatin.setText(speciesObjectList.get(position).get("name_latin").toString());

        return itemViewlist;
        }

}
