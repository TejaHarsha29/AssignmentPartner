package com.AppsbyHarsha.assignmentpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdatesActivity extends AppCompatActivity {


    ImageView imageView2;

    TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);


        imageView2=findViewById(R.id.imageView2);

        textView=findViewById(R.id.convert_textv);

        textView2=findViewById(R.id.copy);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"assignmentpartner4u@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Please describe your issue here");
                intent.putExtra(Intent.EXTRA_TEXT, "We are glad to help you");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(UpdatesActivity.this.getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }
}