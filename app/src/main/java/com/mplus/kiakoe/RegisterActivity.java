package com.mplus.kiakoe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.kiakoe.database.SqlDatabase;
import com.mplus.kiakoe.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends Activity implements View.OnClickListener{

    public final static String SURNAME = "surename";
    public final static String FIRSTNAME = "first";
    public final static String GENDER = "gender";
    public final static String DATEBIRTH = "datebirth";
    public static String IMAGES = "imageuser";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    Spinner spGender;
    EditText surename,firstname,datebirth;
    ImageView imageViewUser;
    SqlDatabase myDb;



    private int mYear, mMonth, mDay;
    private static int REQ_CODE = 155;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_prev:

                    Intent main = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(main);

                    return true;
                case R.id.navigation_next:
                    addUserTemp();

                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new SqlDatabase(this);

        //data from register 2
//        Intent intent = getIntent();
//        String usr2 = intent.getStringExtra(RegisterActivity2.USERNAME);
//        String em2 = intent.getStringExtra(RegisterActivity2.EMAIL);
//        String emc2 = intent.getStringExtra(RegisterActivity2.EMAIL_CONFIRM);
//        String pa2 = intent.getStringExtra(RegisterActivity2.PASSWORD);
//        String pac2 = intent.getStringExtra(RegisterActivity2.PASSWORD_CONFIRM);
//        String pah2 = intent.getStringExtra(RegisterActivity2.PASSWORD_HINT);

//        String usr3 = intent.getStringExtra(RegisterDone.USERNAME);
//        String em3 = intent.getStringExtra(RegisterDone.EMAIL);
//        String emc3 = intent.getStringExtra(RegisterDone.EMAIL_CONFIRM);
//        String pa3 = intent.getStringExtra(RegisterDone.PASSWORD);
//        String pac3 = intent.getStringExtra(RegisterDone.PASSWORD_CONFIRM);
//        String pah3 = intent.getStringExtra(RegisterDone.PASSWORD_HINT);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationReg);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        surename = (EditText)findViewById(R.id.etSureName);
        firstname = (EditText)findViewById(R.id.etFirstName);
        datebirth = (EditText)findViewById(R.id.etDateBirth);
        imageViewUser =(ImageView)findViewById(R.id.imgNewUser);

        spGender = findViewById(R.id.spGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_gen, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        datebirth.setOnClickListener(this);
        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public void onClick(View v){
        if(v == datebirth){
            //get Curent Date
            final Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            datebirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        surename =(EditText) findViewById(R.id.etSureName);
        firstname = (EditText)findViewById(R.id.etFirstName);
        datebirth = (EditText)findViewById(R.id.etDateBirth);
        String sr = surename.getText().toString();
        String fn = firstname.getText().toString().trim();
        String gd = spGender.getSelectedItem().toString().trim();
        String dt = datebirth.getText().toString().trim();

        outState.putString(SURNAME, sr);
        outState.putString(FIRSTNAME, fn);
        outState.putString(SURNAME, gd);
        outState.putString(SURNAME, dt);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(RegisterActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    public void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] byteArray = bytes.toByteArray();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("Encode","eee " +encodedImage);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageViewUser.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageViewUser.setImageBitmap(bm);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IMAGES,encodeToBase64(bm));
        edit.apply();
    }
    //add for temporary
    public void addUserTemp(){
        String surname = surename.getText().toString().trim();
        String fname = firstname.getText().toString().trim();
        String gdr  = spGender.getSelectedItem().toString().trim();
        String dtirth = datebirth.getText().toString().trim();
//        myDb.insertData(null,null,null,null,null,null,null,null,null,
//                null,null,null,null,img);
//        Log.d("aaaa","gambar :" +myDb);

//        String imguser = imageViewUser.getDrawingCache().toString();


        Intent bundle = new Intent(this,RegisterActivity2.class);
//        bundle.putExtra(SURNAME,surname);
//        bundle.putExtra(FIRSTNAME,fname);
//        bundle.putExtra(GENDER,gdr);
//        bundle.putExtra(DATEBIRTH,dtirth);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(SURNAME,surname);
        edit.putString(FIRSTNAME,fname);
        edit.putString(GENDER,gdr);
        edit.putString(DATEBIRTH,dtirth);
//        edit.putString(IMAGES,encodeToBase64(imageViewUser));
        edit.apply();

        startActivity(bundle);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putString("images",encodedImage);
//        edit.apply();
    }
    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
