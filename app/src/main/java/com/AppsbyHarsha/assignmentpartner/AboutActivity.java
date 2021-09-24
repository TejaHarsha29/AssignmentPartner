package com.AppsbyHarsha.assignmentpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView creditstextView;

    ImageView imageView2;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        creditstextView = findViewById(R.id.credits_text);

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
                finish();
            }
        });

        int unicode = 0x2764;
        String emoji = getEmoji(unicode);
        String text = "Made With" + emoji + "By Harsha";
        creditstextView.setText(text);


    }

    public String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }

}