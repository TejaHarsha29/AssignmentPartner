package com.AppsbyHarsha.assignmentpartner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScanActivity extends AppCompatActivity {


    EditText mEditText;
    TextView convert_text;

    ImageView scanButton,back;


    String textFrom;

    String cameraPermisions[];
    String storagePermision[];

    ListView listView;


    int size;


    int progressIncrement;


    private static final int REQUEST_CODE_FOR_CAMERA = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int GALLERY_CODE = 700;
    private static final int PICK_CAMERA_CODE = 1000;

    Uri imageUril;
    String font;
    String textColor;
    String textSize;

    SeekBar seekBar;

    String textBg;


    float sizeAfter;

    String scanSeperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        scanButton = findViewById(R.id.ScanButton);

        convert_text = findViewById(R.id.convert_textv);


        mEditText = findViewById(R.id.edit);

        back=findViewById(R.id.imageView2);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertDialog();
            }
        });


        font = getIntent().getStringExtra("font");
        textColor = getIntent().getStringExtra("Color");
        textSize = getIntent().getStringExtra("Size");
        textBg=getIntent().getStringExtra("bg");
        scanSeperator=getIntent().getStringExtra("seperator");

        size = Integer.parseInt(textSize);
        progressIncrement = size;


        sizeAfter = (float) (size / 5.15);

        mEditText.setTextSize(sizeAfter);

        seekBar = findViewById(R.id.seek);
        seekBar.setProgress(size - 35);







        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressIncrement = progress +35;
                sizeAfter = (float) (progressIncrement / 5.15);
                mEditText.setTextSize(sizeAfter);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        convert_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tb;


                String data = mEditText.getText().toString();


                if(data.equals("")){

                    tb="You text input is empty :( \n please go back and enter some text :)";



                }else{
                    tb= data + scanSeperator;

                }






                Intent intent = new Intent(ScanActivity.this, PdfActivity.class);
                intent.putExtra("data", tb);
                intent.putExtra("datawithoutSperator", data);

                intent.putExtra("fonts", font);
                intent.putExtra("Color", textColor);
                intent.putExtra("Size", String.valueOf(progressIncrement));
                intent.putExtra("background", textBg);
                intent.putExtra("scansep", scanSeperator);



                startActivity(intent);


            }
        });

        cameraPermisions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermision = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageImportDialog();
            }
        });


    }

    @Override
    public void onBackPressed() {

        ShowAlertDialog();



    }

    private void ShowAlertDialog() {
        ViewGroup viewGroup=findViewById(android.R.id.content);

        TextView txtcopy,txtcancel;

        AlertDialog.Builder builder=new AlertDialog.Builder(ScanActivity.this);
        View view1= LayoutInflater.from(ScanActivity.this).inflate(R.layout.alertdialog,viewGroup,false);
        builder.setCancelable(true);
        builder.setView(view1);

        txtcopy=view1.findViewById(R.id.copy);
        txtcancel=view1.findViewById(R.id.cancel);

        final AlertDialog alertDialog=builder.create();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        txtcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(ScanActivity.this.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", mEditText.getText().toString());
                if (clipboard == null || clip == null) return;
                clipboard.setPrimaryClip(clip);
                proceedbackIntent();

            }
        });
        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        alertDialog.show();





    }

    private void proceedbackIntent() {

        finish();





    }

    private void showImageImportDialog() {
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select image to scan.");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestPermissions();
                    } else {
                        pickCamera();
                    }
                }
                if (which == 1) {// mkstake here!!
                    if (!checkStoragePermission()) {
                        requestStoragePermissions();
                    } else {
                        pickGalery();
                    }
                }
            }
        });
        dialog.create().show();
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPhoto");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");
        imageUril = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUril);
        startActivityForResult(cameraIntent, PICK_CAMERA_CODE);
    }

    private void pickGalery() {
        Intent myIntent = new Intent(Intent.ACTION_PICK);
        myIntent.setType("image/*");
        startActivityForResult(myIntent, GALLERY_CODE);
    }


    private void requestStoragePermissions() {
        ActivityCompat.requestPermissions(this, storagePermision, STORAGE_REQUEST_CODE);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, cameraPermisions, REQUEST_CODE_FOR_CAMERA);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_FOR_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAcceptedb = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAcceptedb) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAcceptedb = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAcceptedb) {
                        pickGalery();
                    } else {
                        Toast.makeText(this, "permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_CODE) {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_CAMERA_CODE) {
                CropImage.activity(imageUril).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                    if (!recognizer.isOperational()) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> items = recognizer.detect(frame);
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < items.size(); j++) {
                            TextBlock myItems = items.valueAt(j);
                            sb.append(myItems.getValue());
                            sb.append("\n");

                        }
                        mEditText.append(sb.toString());
                        mEditText.append("\n"+scanSeperator);


                    }


                } catch (Exception e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }


}