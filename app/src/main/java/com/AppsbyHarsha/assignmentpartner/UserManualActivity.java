package com.AppsbyHarsha.assignmentpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserManualActivity extends AppCompatActivity {


    ImageView img6,img7,img8,img9,img11,img12;

    ImageView imageView2;

    TextView textView,textView2;


    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manual);

        img6=findViewById(R.id.img6);
        img7=findViewById(R.id.img7);
        img8=findViewById(R.id.img8);
        img9=findViewById(R.id.img9);
        img11=findViewById(R.id.img11);
        img12=findViewById(R.id.img12);

        image=findViewById(R.id.imagef);

        Glide.with(this)
                .load(R.drawable.imgf)
                .into(image);


        imageView2=findViewById(R.id.imageView2);

        textView=findViewById(R.id.convert_textv);

        textView2=findViewById(R.id.copy);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserManualActivity.this,AsignmentActivity.class));
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())); /// here "yourpackegName" from your app packeg Name
                    intent.setPackage("com.android.vending");
                    startActivity(intent);

                }catch (Exception e){

                    startActivity( new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));

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
                .load(R.drawable.img6)
                .into(img6);
        Glide.with(this)
                .load(R.drawable.img7)
                .into(img7);
        Glide.with(this)
                .load(R.drawable.img8)
                .into(img8);
        Glide.with(this)
                .load(R.drawable.img9)
                .into(img9);Glide.with(this)
                .load(R.drawable.img11)
                .into(img11);
        Glide.with(this)
                .load(R.drawable.img12)
                .into(img12);












    }
}