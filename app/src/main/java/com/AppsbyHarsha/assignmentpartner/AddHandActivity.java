package com.AppsbyHarsha.assignmentpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class AddHandActivity extends AppCompatActivity {

    ImageView img5;

    ImageView imageView2;

    TextView textView;

    TextView textView2;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hand);

        img5 = findViewById(R.id.img5);

        imageView2 = findViewById(R.id.imageView2);

        textView = findViewById(R.id.convert_textv);

        textView2 = findViewById(R.id.copy);


        mAdView = findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.

                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);


            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"assignmentpartner4u@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Please add your image here");
                intent.putExtra(Intent.EXTRA_TEXT, "please be patient we will make your font and release an update soon,it takes about month or few days as i am a solo developer..so please be patient and cooperate with us");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(AddHandActivity.this.getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

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
                finish();
            }
        });


        Glide.with(this)
                .load(R.drawable.img5)
                .into(img5);


    }
}