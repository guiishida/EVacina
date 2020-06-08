package com.example.evacina.androidloginregisterrestfullwebservice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evacina.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.vaccineViewHolder> {
    private ArrayList<VaccineView> vaccinesList;

    public VaccineAdapter(ArrayList<VaccineView> vaccinesList) {
        this.vaccinesList = vaccinesList;
    }

    static class vaccineViewHolder extends RecyclerView.ViewHolder {
        private TextView name_vaccine;
        private TextView date;
        private TextView location_name;

        vaccineViewHolder(View itemView) {
            super(itemView);

            name_vaccine = itemView.findViewById(R.id.name_vaccine);
            date = itemView.findViewById(R.id.date);
            location_name = itemView.findViewById(R.id.locationName);
        }

        void setName_vaccine(String name, int position){
            this.name_vaccine.setText(name);
            if (position == 0){
                this.name_vaccine.setHighlightColor(Color.DKGRAY);
                this.name_vaccine.setTypeface(null, Typeface.BOLD);
            }
        }

        void setLocation_name(String location) {
            this.location_name.setText(location);
        }

        void setDate(String d) {
            this.date.setText(d);
        }
    }

    @NonNull
    @Override
    public vaccineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.vaccine_adapter, parent, false);
        return new vaccineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(vaccineViewHolder holder, int position) {
        final VaccineView vaccine = vaccinesList.get(position);
        holder.setName_vaccine(vaccine.getName(),position);
        holder.setDate(vaccine.getDate());
        holder.setLocation_name(vaccine.getLocation());

        if (position ==0) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.width = 150;
            holder.itemView.setLayoutParams(params);
            holder.location_name.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return vaccinesList.size();
    }
}