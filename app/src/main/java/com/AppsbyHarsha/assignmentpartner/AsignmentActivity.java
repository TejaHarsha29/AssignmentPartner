package com.AppsbyHarsha.assignmentpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AsignmentActivity extends AppCompatActivity {

    ImageView img1, img2, img3, img4;

    ImageView imageView2;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignment);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        imageView2 = findViewById(R.id.imageView2);

        textView = findViewById(R.id.convert_textv);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())); /// here "yourpackegName" from your app packeg Name
                    intent.setPackage("com.android.vending");
                    startActivity(intent);

                } catch (Exception e) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));

                }

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedIntent();
            }
        });


        Glide.with(this)
                .load(R.drawable.ass1)
                .into(img1);
        Glide.with(this)
                .load(R.drawable.ass2)
                .into(img2);
        Glide.with(this)
                .load(R.drawable.ass3)
                .into(img3);
        Glide.with(this)
                .load(R.drawable.ass4)
                .into(img4);


    }

    @Override
    public void onBackPressed() {
        proceedIntent();
    }

    private void proceedIntent() {


        startActivity(new Intent(AsignmentActivity.this, StartActivity.class));
        finish();

    }
}