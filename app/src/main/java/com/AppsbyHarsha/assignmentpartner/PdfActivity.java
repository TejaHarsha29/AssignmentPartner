package com.AppsbyHarsha.assignmentpartner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class PdfActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Adapter myAdapter;

    TextView getPdfTextview;

    int textSize;

    ImageView imageView;

    float finalTextSize;

    ProgressDialog progressDialog;


    EditText editText;


    String textData;

    String dataWoSeperator;
    String name;


    private InterstitialAd mInterstitialAd;

    private AdView mAdView;

    CheckBox checkBox;

    ArrayList<Bitmap> bitmaps;


    static {
        System.loadLibrary("NativeImageProcessor");
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        bitmaps = new ArrayList<Bitmap>();


        String fonts = getIntent().getStringExtra("fonts");
        String color = getIntent().getStringExtra("Color");
        String size = getIntent().getStringExtra("Size");
        String bg = getIntent().getStringExtra("background");

        dataWoSeperator = getIntent().getStringExtra("datawithoutSperator");

        final int textBackground = Integer.parseInt(bg);

        int textColor = Integer.parseInt(color);

        textSize = Integer.parseInt(size);

        finalTextSize = (float) textSize / 5;

        getPdfTextview = findViewById(R.id.getPdf_text);

        imageView = findViewById(R.id.imageView2);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textData = getIntent().getStringExtra("data");

        String sep = getIntent().getStringExtra("scansep");

        String br = sep.replace("@@@@@@", "@@@");

        final String[] words = textData.split(br);

        final List<String> wordList = Arrays.asList(words);

        int id = getIdFromString(fonts);


        recyclerView = findViewById(R.id.recycler);


        createPersonalizedAd();


        getPdfTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recyclerView.smoothScrollToPosition(0);
                ViewGroup viewGroup = findViewById(android.R.id.content);

                TextView textView_continue;
                TextView textView_cancel;
                TextView textView_copy_continue;


                AlertDialog.Builder builder = new AlertDialog.Builder(PdfActivity.this);
                View view1 = LayoutInflater.from(PdfActivity.this).inflate(R.layout.alert_edittext, viewGroup, false);
                builder.setCancelable(true);
                builder.setView(view1);

                editText = view1.findViewById(R.id.copy);
                textView_cancel = view1.findViewById(R.id.cancel);
                textView_continue = view1.findViewById(R.id.contine);
                textView_copy_continue = view1.findViewById(R.id.copy_cont);
                checkBox = view1.findViewById(R.id.checkBox);

                final AlertDialog alertDialog = builder.create();

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                textView_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        StartDownloading();


                    }
                });
                textView_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                textView_copy_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(PdfActivity.this.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", dataWoSeperator);
                        if (clipboard == null || clip == null) return;
                        clipboard.setPrimaryClip(clip);


                        StartDownloading();
                    }
                });


                alertDialog.show();

            }
        });

        myAdapter = new Adapter(this, wordList, id, textColor, finalTextSize, textBackground);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);


    }

    private void createPersonalizedAd() {


        AdRequest adRequest = new AdRequest.Builder().build();

        createInterstitialAd(adRequest);


    }


    private void createInterstitialAd(AdRequest adRequest) {
        InterstitialAd.load(this, "ca-app-pub-7003445918152600/5510371388", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("onAdloaded", "onAdLoaded");

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.");

                        Intent inten = new Intent(PdfActivity.this, StartActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        inten.putExtra("downloadtoggle", "true");
                        startActivity(inten);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {

                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("adError", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    private int getIdFromString(String font) {
        int id = 0;

        if (font.equals("R.id.f1")) {
            id = R.font.f1;
        } else if (font.equals("R.id.f2")) {
            id = R.font.f2;
        } else if (font.equals("R.id.f3")) {
            id = R.font.f3;
        }
        if (font.equals("R.id.f4")) {
            id = R.font.f4;
        } else if (font.equals("R.id.f5")) {
            id = R.font.f5;
        } else if (font.equals("R.id.f6")) {
            id = R.font.f6;
        }
        if (font.equals("R.id.f7")) {
            id = R.font.f7;
        } else if (font.equals("R.id.f8")) {
            id = R.font.f8;
        } else if (font.equals("R.id.f9")) {
            id = R.font.f9;
        } else if (font.equals("R.id.f10")) {
            id = R.font.f10;
        } else if (font.equals("R.id.f11")) {
            id = R.font.f11;
        } else if (font.equals("R.id.f12")) {
            id = R.font.f12;
        } else if (font.equals("R.id.f13")) {
            id = R.font.f13;
        } else if (font.equals("R.id.f14")) {
            id = R.font.f14;
        } else if (font.equals("R.id.f15")) {
            id = R.font.f15;
        } else if (font.equals("R.id.f16")) {
            id = R.font.f16;
        } else if (font.equals("R.id.f17")) {
            id = R.font.f17;
        } else if (font.equals("R.id.f18")) {
            id = R.font.f18;
        } else if (font.equals("R.id.f19")) {
            id = R.font.f19;
        } else if (font.equals("R.id.f20")) {
            id = R.font.f20;
        } else if (font.equals("R.id.f21")) {
            id = R.font.f21;
        } else if (font.equals("R.id.f22")) {
            id = R.font.f22;
        } else if (font.equals("R.id.f23")) {
            id = R.font.f23;
        } else if (font.equals("R.id.f24")) {
            id = R.font.f24;
        } else if (font.equals("R.id.f25")) {
            id = R.font.f25;
        } else if (font.equals("R.id.f26")) {
            id = R.font.f26;
        } else if (font.equals("R.id.f27")) {
            id = R.font.f27;
        } else if (font.equals("R.id.f28")) {
            id = R.font.f28;
        } else if (font.equals("R.id.f29")) {
            id = R.font.f29;
        } else if (font.equals("R.id.f30")) {
            id = R.font.f30;
        } else if (font.equals("R.id.f31")) {
            id = R.font.f31;
        } else if (font.equals("R.id.f32")) {
            id = R.font.f32;
        } else if (font.equals("R.id.f33")) {
            id = R.font.f33;
        } else if (font.equals("R.id.f34")) {
            id = R.font.f34;
        }
        return id;
    }

    private void StartDownloading() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {


                name = editText.getText().toString() + ".pdf";


                PdfDocument mypdfDocument = new PdfDocument();
                Paint mypaint = new Paint();
                mypaint.setColor(Color.parseColor("#FFFFFF"));


                recyclerView.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                Bitmap bitmap = Bitmap.createBitmap(recyclerView.getWidth(), recyclerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                recyclerView.draw(new Canvas(bitmap));

                Bitmap finalbitmap = null;

                if (checkBox.isChecked()) {


                    Filter myFilter = new Filter();
                    myFilter.addSubFilter(new ContrastSubFilter(1.6f));

                    myFilter.addSubFilter(new SaturationSubFilter(1.2f));


                    Point[] rgbKnots;
                    rgbKnots = new Point[3];
                    rgbKnots[0] = new Point(0, 0);
                    rgbKnots[1] = new Point(175, 139);
                    rgbKnots[2] = new Point(255, 255);

                    myFilter.addSubFilter(new ToneCurveSubFilter(rgbKnots, null, null, null));


                    Bitmap image = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                    finalbitmap = myFilter.processFilter(image);

                } else {

                    finalbitmap = bitmap;
                }


                PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(finalbitmap.getWidth(), finalbitmap.getHeight(), 1).create();
                PdfDocument.Page myPage1 = mypdfDocument.startPage(pageInfo1);
                Canvas canvas = myPage1.getCanvas();

                finalbitmap = Bitmap.createScaledBitmap(finalbitmap, finalbitmap.getWidth(), finalbitmap.getHeight(), true);


                canvas.drawBitmap(finalbitmap, 0, 0, null);

                mypdfDocument.finishPage(myPage1);


                String path = getExternalFilesDir(null).getAbsolutePath().toString() + "/" + name;

                File file = new File(path);


                try {
                    if (file.exists()) {

                        Toast.makeText(PdfActivity.this, "file with same name already exist or invalid name", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                    } else {
                        mypdfDocument.writeTo(new FileOutputStream(file));
                        Toast.makeText(PdfActivity.this, "Pdf Downloaded Successfully", Toast.LENGTH_SHORT).show();


                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ShowNotification();
                        }


                        mypdfDocument.close();


                        proceedWithIntent();

                    }


                } catch (Exception e) {
                    Toast.makeText(PdfActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, 500);

        progressDialog = new ProgressDialog(PdfActivity.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        progressDialog.show();


        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ShowNotification() {

        createNotificationChannel();

        Intent intent = new Intent(this, AsignmentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Harsha")
                .setSmallIcon(R.drawable.cr_final)
                .setContentTitle("Pdf downloaded successfully")
                .setContentText("Click here to know the pdf location")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Click here to know the pdf location"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent).setAutoCancel(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);


// notificationId is a unique int for each notification that you must define
        notificationManager.notify(100, builder.build());


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Harsha", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void proceedWithIntent() {


        if (mInterstitialAd != null) {
            mInterstitialAd.show(PdfActivity.this);
        } else {


            Intent inten = new Intent(PdfActivity.this, StartActivity.class);
            inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            inten.putExtra("downloadtoggle", "true");
            startActivity(inten);
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }


    }


}