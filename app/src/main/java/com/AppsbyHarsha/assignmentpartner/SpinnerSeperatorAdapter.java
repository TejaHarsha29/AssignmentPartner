package com.AppsbyHarsha.assignmentpartner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class SpinnerSeperatorAdapter extends ArrayAdapter<Integer> {
    private Integer[] images;
    private String[] text;
    private Context context;

    public SpinnerSeperatorAdapter(Context context, Integer[] images, String[] text) {
        super(context, android.R.layout.simple_spinner_item, images);
        this.images = images;
        this.text = text;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    private View getImageForPosition(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_font_back, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(text[position]);




        return row;
    }
}
