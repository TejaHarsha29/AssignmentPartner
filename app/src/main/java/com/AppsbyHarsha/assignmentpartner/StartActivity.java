package com.AppsbyHarsha.assignmentpartner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;



public class StartActivity extends AppCompatActivity {

    TextView creditstextView, textPrev;

    ColorSeekBar colorSeekBar;

    SeekBar seekBar;

    RelativeLayout relativeLayout;

    Button create_btn;

    Spinner spinner, spinner_bg, spinner_seperator;

    String font;

    int textColor = -16777216;

    float textSize = (float) 2.5;

    ArrayList<Typeface> font1;

    ImageView imageView4;


    String subject;

    String message;

    float pdfSize;

    int siz = 60;

    int draw = R.drawable.a;

    int idea;

    String seperator = "@@@";

    ImageView imageView;


    //left navigation ki

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        creditstextView = findViewById(R.id.credits_text);

        int unicode = 0x2764;
        String emoji = getEmoji(unicode);
        String text = "Made With " + emoji + " By Harsha";
        creditstextView.setText(text);


        colorSeekBar = findViewById(R.id.color_seekbar);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(25);

        create_btn = findViewById(R.id.create_button);

        spinner = findViewById(R.id.spinner_font);

        relativeLayout = findViewById(R.id.preview_bg);


        textPrev = findViewById(R.id.text_data_preview);
        spinner_bg = findViewById(R.id.spinner_bg);

        spinner_seperator = findViewById(R.id.spinner_pageSeperator);

        imageView=findViewById(R.id.dot);


        getFonts(this);

        imageView4=findViewById(R.id.imageView4);













        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartnextActivity();
            }
        });













        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(StartActivity.this,v);
                popupMenu.inflate(R.menu.dotmenu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_manual:

                                startActivity(new Intent(StartActivity.this,UserManualActivity.class ));

                                return true;
                            case R.id.menu_rate:

                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+getPackageName())); /// here "yourpackegName" from your app packeg Name
                                    intent.setPackage("com.android.vending");
                                    startActivity(intent);

                                }catch (Exception e){

                                    startActivity( new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));

                                }





                                return true;
                            default:
                                return false;
                        }


                    }
                });
            }
        });


        textPrev.setTextSize(textSize);


        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.nav_view);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setSelected(false);





        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id)
                {
                    case R.id.assignment:

                        startActivity(new Intent(StartActivity.this,AsignmentActivity.class));



                        item.setCheckable(false);
                        break;
                    case R.id.rate:

                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+getPackageName())); /// here "yourpackegName" from your app packeg Name
                            intent.setPackage("com.android.vending");
                            startActivity(intent);

                        }catch (Exception e){

                            startActivity( new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));

                        }




                        item.setCheckable(false);



                        break;
                    case R.id.credits:
                        
                        startActivity(new Intent(StartActivity.this,CreditsActivity.class));

                        item.setCheckable(false);

                        break;
                    case R.id.bugs:

                        OpenMail("Report bugs","Please describe your issue here...");

                        item.setCheckable(false);
                        break;

                    case R.id.update:

                        startActivity(new Intent(StartActivity.this,UpdatesActivity.class));


                        item.setCheckable(false);
                        break;



                    case R.id.handWriting:

                        startActivity(new Intent(StartActivity.this,AddHandActivity.class));

                        item.setCheckable(false);
                        break;
                    case R.id.about:
                        startActivity(new Intent(StartActivity.this,AboutActivity.class));

                        item.setCheckable(false);
                        break;
                    case R.id.manual:

                        startActivity(new Intent(StartActivity.this,UserManualActivity.class ));

                        item.setCheckable(false);
                        break;
                    case R.id.share:

                        try {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            String link ="Hey,check out this cool app which prepares our assignments in seconds"+"\n"+"http://play.google.com/store/apps/details?id="+getPackageName();
                            sendIntent.putExtra(Intent.EXTRA_TEXT,link);
                                    sendIntent.setPackage("com.whatsapp");
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);





                        } catch (Exception e){
                            Toast.makeText(StartActivity.this, "Whatsapp not installed!", Toast.LENGTH_LONG).show();
                        }

                        item.setCheckable(false);
                        break;
                    case R.id.gmail:
                        OpenMail("Contact","");


                        item.setCheckable(false);
                        break;
                    case R.id.instagram:
                        Uri uri = Uri.parse("http://instagram.com/_u/assignmentpartner4u");


                        Intent i= new Intent(Intent.ACTION_VIEW,uri);

                        i.setPackage("com.instagram.android");

                        try {
                            startActivity(i);
                        } catch (Exception e) {

                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://instagram.com/xxx")));
                        }

                        item.setCheckable(false);
                        break;
                    case R.id.youtube:

                        startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("https://www.youtube.com/channel/UCkVYOoEe9MXNMpb807TSrIw")));
                        item.setCheckable(false);
                        break;
                    case R.id.telegram:

                        startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("https://t.me/joinchat/J2qPOH_ad6NlZTI9")));

                        item.setCheckable(false);
                        break;



















                    default:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;



            }
        });





        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {









                StartnextActivity();
            }
        });

        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                textColor = i;
                textPrev.setTextColor(textColor);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                siz = progress + 35;
                textSize = (float) siz / 24;
                textPrev.setTextSize(textSize);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //spinner font

        try {

            Integer[] integers = new Integer[]{null, null, null, null, null, null, null, null,null,null,null, null, null, null, null, null, null, null,null,null,null, null, null, null, null, null, null, null,null,null,null,null,null,null};
            String[] strings = new String[]{"Hello Partner", "Hello Partner", "Hello Partner","Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner","Hello Partner", "Hello Partner","Hello Partner","Hello Partner","Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner","Hello Partner", "Hello Partner", "Hello Partner","Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner", "Hello Partner","Hello Partner", "Hello Partner", "Hello Partner","Hello Partner", "Hello Partner", "Hello Partner"};
            SpinnerAdapter adapter = new
                    SpinnerAdapter(getApplicationContext(), integers, strings,font1);
            spinner.setAdapter(adapter);

            spinner.setSelection(0);



            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            font = "R.id.f1";
                            break;
                        case 1:
                            font = "R.id.f2";
                            break;
                        case 2:
                            font = "R.id.f3";
                            break;
                        case 3:
                            font = "R.id.f4";
                            break;
                        case 4:
                            font = "R.id.f5";
                            break;
                        case 5:
                            font = "R.id.f6";
                            break;
                        case 6:
                            font = "R.id.f7";
                            break;
                        case 7:
                            font = "R.id.f8";
                            break;
                        case 8:
                            font="R.id.f9";
                        case 9:
                            font = "R.id.f10";
                            break;
                        case 10:
                            font = "R.id.f11";
                            break;
                        case 11:
                            font = "R.id.f12";
                            break;
                        case 12:
                            font = "R.id.f13";
                            break;
                        case 13:
                            font = "R.id.f14";
                            break;
                        case 14:
                            font = "R.id.f15";
                            break;
                        case 15:
                            font = "R.id.f16";
                            break;
                        case 16:
                            font = "R.id.f17";
                            break;
                        case 17:
                            font="R.id.f18";
                        case 18:
                            font = "R.id.f19";
                            break;
                        case 19:
                            font = "R.id.f20";
                            break;
                        case 20:
                            font = "R.id.f21";
                            break;
                        case 21:
                            font = "R.id.f22";
                            break;
                        case 22:
                            font = "R.id.f23";
                            break;
                        case 23:
                            font = "R.id.f24";
                            break;
                        case 24:
                            font = "R.id.f25";
                            break;
                        case 25:
                            font = "R.id.f26";
                            break;
                        case 27:
                            font="R.id.f28";
                        case 28:
                            font = "R.id.f29";
                            break;
                        case 29:
                            font = "R.id.f30";
                            break;
                        case 30:
                            font = "R.id.f31";
                            break;
                        case 31:
                            font = "R.id.f32";
                            break;
                        case 32:
                            font = "R.id.f33";
                            break;
                        case 33:
                            font = "R.id.f34";
                            break;
                    }

                    int idea = getIdFromString(font);

                    Typeface typeface = ResourcesCompat.getFont(StartActivity.this, idea);

                    textPrev.setTypeface(typeface);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //Spinner_bg
        Integer[] integers_bg = new Integer[]{R.drawable.a_comp, R.drawable.b_comp, R.drawable.c_comp};
        String[] strings_bg = new String[]{"1", "2", "3"};
        SpinnerBgAdapter adapterBg = new
                SpinnerBgAdapter(getApplicationContext(), integers_bg, strings_bg);
        spinner_bg.setAdapter(adapterBg);
        spinner_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        draw = R.drawable.a;
                        break;
                    case 1:
                        draw = R.drawable.b;
                        break;
                    case 2:
                        draw = R.drawable.c;
                        break;
                }
                relativeLayout.setBackground(StartActivity.this.getResources().getDrawable(draw));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner_page seperator
        Integer[] integers_seperator = new Integer[]{null, null, null};
        String[] strings_seperator = new String[]{"@@@", "---", "~~~"};
        SpinnerSeperatorAdapter adapterSeperator = new
                SpinnerSeperatorAdapter(getApplicationContext(), integers_seperator, strings_seperator);
        spinner_seperator.setAdapter(adapterSeperator);
        spinner_seperator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        seperator = "@@@";
                        break;
                    case 1:
                        seperator = "---";
                        break;
                    case 2:
                        seperator = "~~~";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void StartnextActivity() {

        Intent intent = new Intent(StartActivity.this, ScanActivity.class);
        intent.putExtra("font", font);
        intent.putExtra("Color", String.valueOf(textColor));
        intent.putExtra("bg", String.valueOf(draw));
        intent.putExtra("seperator", seperator);

        pdfSize = (float) 4.8 * textSize;
        intent.putExtra("Size", String.valueOf(siz));
        startActivity(intent);
    }

    private void getFonts(Context context) {

        Typeface typeface = ResourcesCompat.getFont(context, R.font.f1);
        Typeface typeface1 = ResourcesCompat.getFont(context, R.font.f2);
        Typeface typeface2 = ResourcesCompat.getFont(context, R.font.f3);
        Typeface typeface3 = ResourcesCompat.getFont(context, R.font.f4);
        Typeface typeface4 = ResourcesCompat.getFont(context, R.font.f5);
        Typeface typeface5 = ResourcesCompat.getFont(context, R.font.f6);
        Typeface typeface6 = ResourcesCompat.getFont(context, R.font.f7);
        Typeface typeface7 = ResourcesCompat.getFont(context, R.font.f8);
        Typeface typeface8 = ResourcesCompat.getFont(context, R.font.f9);
        Typeface typeface9 = ResourcesCompat.getFont(context, R.font.f10);
        Typeface typeface10 = ResourcesCompat.getFont(context, R.font.f11);
        Typeface typeface11 = ResourcesCompat.getFont(context, R.font.f12);
        Typeface typeface12 = ResourcesCompat.getFont(context, R.font.f13);
        Typeface typeface13 = ResourcesCompat.getFont(context, R.font.f14);
        Typeface typeface14 = ResourcesCompat.getFont(context, R.font.f15);
        Typeface typeface15 = ResourcesCompat.getFont(context, R.font.f16);
        Typeface typeface16 = ResourcesCompat.getFont(context, R.font.f17);
        Typeface typeface17 = ResourcesCompat.getFont(context, R.font.f18);
        Typeface typeface18 = ResourcesCompat.getFont(context, R.font.f19);
        Typeface typeface19 = ResourcesCompat.getFont(context, R.font.f20);
        Typeface typeface20 = ResourcesCompat.getFont(context, R.font.f21);
        Typeface typeface21 = ResourcesCompat.getFont(context, R.font.f22);
        Typeface typeface22 = ResourcesCompat.getFont(context, R.font.f23);
        Typeface typeface23 = ResourcesCompat.getFont(context, R.font.f24);
        Typeface typeface24 = ResourcesCompat.getFont(context, R.font.f25);
        Typeface typeface25 = ResourcesCompat.getFont(context, R.font.f26);
        Typeface typeface26 = ResourcesCompat.getFont(context, R.font.f27);
        Typeface typeface27 = ResourcesCompat.getFont(context, R.font.f28);
        Typeface typeface28 = ResourcesCompat.getFont(context, R.font.f29);
        Typeface typeface29 = ResourcesCompat.getFont(context, R.font.f30);
        Typeface typeface30 = ResourcesCompat.getFont(context, R.font.f31);
        Typeface typeface31 = ResourcesCompat.getFont(context, R.font.f32);
        Typeface typeface32 = ResourcesCompat.getFont(context, R.font.f33);
        Typeface typeface33 = ResourcesCompat.getFont(context, R.font.f34);







        font1 =new ArrayList<Typeface>();
        font1.add(typeface);
        font1.add(typeface1);
        font1.add(typeface2);
        font1.add(typeface3);
        font1.add(typeface4);
        font1.add(typeface5);
        font1.add(typeface6);
        font1.add(typeface7);
        font1.add(typeface8);
        font1.add(typeface9);
        font1.add(typeface10);
        font1.add(typeface11);
        font1.add(typeface12);
        font1.add(typeface13);
        font1.add(typeface14);
        font1.add(typeface15);
        font1.add(typeface16);
        font1.add(typeface17);
        font1.add(typeface18);
        font1.add(typeface19);
        font1.add(typeface20);
        font1.add(typeface21);
        font1.add(typeface22);
        font1.add(typeface23);
        font1.add(typeface24);
        font1.add(typeface25);
        font1.add(typeface26);
        font1.add(typeface27);
        font1.add(typeface28);
        font1.add(typeface29);
        font1.add(typeface30);
        font1.add(typeface31);
        font1.add(typeface32);
        font1.add(typeface33);



    }

    private void OpenMail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"assignmentpartner4u@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(StartActivity.this.getPackageManager()) != null) {
            startActivity(intent);
        }





    }


    private int getIdFromString(String font) {
        idea = 0;

        if (font.equals("R.id.f1")) {
            idea = R.font.f1;
        } else if (font.equals("R.id.f2")) {
            idea = R.font.f2;
        } else if (font.equals("R.id.f3")) {
            idea = R.font.f3;
        } else if (font.equals("R.id.f4")) {
            idea = R.font.f4;
        } else if (font.equals("R.id.f5")) {
            idea = R.font.f5;
        } else if (font.equals("R.id.f6")) {
            idea = R.font.f6;
        } else if (font.equals("R.id.f7")) {
            idea = R.font.f7;
        } else if (font.equals("R.id.f8")) {
            idea = R.font.f8;
        } else if (font.equals("R.id.f9")) {
            idea = R.font.f9;
        }else if (font.equals("R.id.f10")) {
            idea = R.font.f10;
        } else if (font.equals("R.id.f11")) {
            idea = R.font.f11;
        } else if (font.equals("R.id.f12")) {
            idea = R.font.f12;
        } else if (font.equals("R.id.f13")) {
            idea = R.font.f13;
        } else if (font.equals("R.id.f14")) {
            idea = R.font.f14;
        } else if (font.equals("R.id.f15")) {
            idea = R.font.f15;
        } else if (font.equals("R.id.f16")) {
            idea = R.font.f16;
        }
        else if (font.equals("R.id.f17")) {
            idea = R.font.f17;
        }else if (font.equals("R.id.f18")) {
            idea = R.font.f18;
        } else if (font.equals("R.id.f19")) {
            idea = R.font.f19;
        } else if (font.equals("R.id.f20")) {
            idea = R.font.f20;
        } else if (font.equals("R.id.f21")) {
            idea = R.font.f21;
        } else if (font.equals("R.id.f22")) {
            idea = R.font.f22;
        } else if (font.equals("R.id.f23")) {
            idea = R.font.f23;
        } else if (font.equals("R.id.f24")) {
            idea = R.font.f24;
        }
        else if (font.equals("R.id.f25")) {
            idea = R.font.f25;
        }else if (font.equals("R.id.f26")) {
            idea = R.font.f26;
        } else if (font.equals("R.id.f27")) {
            idea = R.font.f27;
        } else if (font.equals("R.id.f28")) {
            idea = R.font.f28;
        } else if (font.equals("R.id.f29")) {
            idea = R.font.f29;
        } else if (font.equals("R.id.f30")) {
            idea = R.font.f30;
        } else if (font.equals("R.id.f31")) {
            idea = R.font.f31;
        } else if (font.equals("R.id.f32")) {
            idea = R.font.f32;
        }
        else if (font.equals("R.id.f33")) {
            idea = R.font.f33;
        }
        else if (font.equals("R.id.f34")) {
            idea = R.font.f34;
        }
        return idea;
    }

    public String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}