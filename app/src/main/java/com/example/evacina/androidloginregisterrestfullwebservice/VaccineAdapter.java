package com.example.evacina.androidloginregisterrestfullwebservice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.evacina.R;

import java.util.ArrayList;


public class VaccineAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<VaccineView> vaccinesList;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder = null;

    public VaccineAdapter(Activity activity, ArrayList<VaccineView> vaccinesList) {
        this.activity = activity;
        this.vaccinesList = vaccinesList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return vaccinesList.size();
    }

    @Override
    public Object getItem(int i) {
        return vaccinesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder{
        TextView disease_text,name_vaccine, date;
    }


    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View vi = view;
        final int pos = position;
        if(vi == null){
            // create  viewholder object
            viewHolder = new ViewHolder();
            // inflate vaccine_adapter for each row
            vi = layoutInflater.inflate(R.layout.vaccine_adapter, null);
            viewHolder.disease_text = vi.findViewById(R.id.disease_text);
            viewHolder.name_vaccine = vi.findViewById(R.id.name_vaccine);
            viewHolder.date = vi.findViewById(R.id.date);
            /*We can use setTag() and getTag() to set and get custom objects as per our requirement.
            The setTag() method takes an argument of type Object, and getTag() returns an Object.*/
            vi.setTag(viewHolder);
        }else {
            /* We recycle a View that already exists */
            viewHolder = (ViewHolder) vi.getTag();
        }

        viewHolder.disease_text.setText(vaccinesList.get(pos).getDisease());
        viewHolder.name_vaccine.setText(vaccinesList.get(pos).getName());
        viewHolder.date.setText(vaccinesList.get(pos).getDate());


        return vi;
    }
}
