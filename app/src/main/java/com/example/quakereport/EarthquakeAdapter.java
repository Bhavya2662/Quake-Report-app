package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.security.identity.EphemeralPublicKeyNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Earthquake earthquake = getItem(position);
        String []fullLocation = split(earthquake.getLocation());
        Date dateAndTime = new Date(earthquake.getTimeInMilliseconds());

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        TextView magnitude = convertView.findViewById(R.id.magnitude);
        TextView locationOffset = convertView.findViewById(R.id.location_offset);
        locationOffset.setText(fullLocation[1]);;
        TextView location = convertView.findViewById(R.id.primary_location);
        location.setText(fullLocation[0] + " of");
        TextView date = convertView.findViewById(R.id.date);
        date.setText(formatDate(dateAndTime));
        TextView time = convertView.findViewById(R.id.time);
        date.setText(formatTime(dateAndTime));
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        magnitudeCircle.setColor(MagnitudeColor(earthquake.getMagnitude()));
        magnitude.setText(formatMagnitude(earthquake.getMagnitude()));

        return convertView;
    }
    private String formatTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h: mm a");
        return dateFormat.format(date);
    }
    private String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM DD, yyyy");
        return dateFormat.format(date);
    }
    private String formatMagnitude(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }
    private String[] split(String string){
        String[] array = {};
        if (string.contains("of")){
            array = string.split("of");
            return array;
        }
        else {
            array = new String[]{"Near",string};
            return array;
        }
    }
    private int MagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId= R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
}
