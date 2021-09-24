package com.AppsbyHarsha.assignmentpartner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private List<String> dataHand;
    private int font;
    private int textColor;
    private float textSize;
    private int bg;





    public Adapter(Context context, List<String> dataHand, int font, int textColor, float textSize, int bg) {
        this.context = context;
        this.dataHand = dataHand;
        this.font = font;
        this.textColor = textColor;
        this.textSize = textSize;
        this.bg = bg;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_back, parent, false);



        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.mTxtView.setText(dataHand.get(position));


        Typeface typeface = ResourcesCompat.getFont(context, font);

        holder.mTxtView.setTypeface(typeface);

        holder.mTxtView.setTextColor(textColor);

        holder.mTxtView.setTextSize(textSize);

        holder.relativeLayout.setBackground(context.getResources().getDrawable(bg));











    }

    @Override
    public int getItemCount() {
        return dataHand.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView mTxtView;
        LinearLayout image;

        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.layout_image);
            mTxtView = itemView.findViewById(R.id.textdata);
            relativeLayout = itemView.findViewById(R.id.relativePdf);

            imageView=itemView.findViewById(R.id.imagesss);



        }
    }


}
