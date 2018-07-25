package com.mplus.kiakoe;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.mplus.kiakoe.database.SqlDatabase;
import com.mplus.kiakoe.utils.Utility;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class RegisterActivity extends AppCompatActivity {

    public final static String SURNAME = "surename";
    public final static String FIRSTNAME = "first";
    public final static String GENDER = "gender";
    public final static String DATEBIRTH = "datebirth";
    public static String IMAGES = "imageuser";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private String regex = "[a-zA-Z ]+";

    Spinner spGender;
    EditText surename, firstname, datebirth;
    ImageView imageViewUser;
    SqlDatabase myDb;
    Button prev, next;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new SqlDatabase(this);

        back = (TextView) findViewById(R.id.txtBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });
        prev = (Button) findViewById(R.id.btnPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });
        next = (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserTemp();
            }
        });
        surename = (EditText) findViewById(R.id.etSureName);
        firstname = (EditText) findViewById(R.id.etFirstName);
        datebirth = (EditText) findViewById(R.id.etDateBirth);
        imageViewUser = (ImageView) findViewById(R.id.imgNewUser);

        spGender = findViewById(R.id.spGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_gen, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        datebirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(datebirth.getText().toString().contains(" ")){ datebirth.setText(datebirth.getText().toString().replaceAll(" " , "/"));
                    datebirth.setSelection(datebirth.getText().length());
                }
            }
        });

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        surename = (EditText) findViewById(R.id.etSureName);
        firstname = (EditText) findViewById(R.id.etFirstName);
        datebirth = (EditText) findViewById(R.id.etDateBirth);
        //dpDate = (DatePicker)findViewById(R.id.dpDate);
        String sr = surename.getText().toString();
        String fn = firstname.getText().toString().trim();
        String gd = spGender.getSelectedItem().toString().trim();
        String dt = datebirth.getText().toString().trim();
        //String db = dpDate.toString();

        outState.putString(SURNAME, sr);
        outState.putString(FIRSTNAME, fn);
        outState.putString(GENDER, gd);
        outState.putString(DATEBIRTH, dt);
        //outState.putString(DATEBIRTH,db);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegisterActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
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
        Log.d("Encode", "eee " + encodedImage);


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
        edit.putString(IMAGES, encodeToBase64(bm));
        edit.apply();
    }

    //add for temporary
    public void addUserTemp() {
        String surname = surename.getText().toString().trim();
        String fname = firstname.getText().toString().trim();
        String gdr = spGender.getSelectedItem().toString().trim();
        String dtirth = datebirth.getText().toString().trim();
        //String dtb = dpDate.toString();


        //validate input
//        if (surname.length() == 0) {
//            surename.requestFocus();
//            surename.setError("Surname cannot be empty");
//        } else if (!surname.matches(regex)) {
//            surename.requestFocus();
//            surename.setError("Enter only alphabetical");
//        } else if (fname.length() == 0) {
//            firstname.requestFocus();
//            firstname.setError("Firstname cannot be empty");
//        } else if (dtirth.length() == 0) {
//            datebirth.requestFocus();
//            datebirth.setError("Date of birth cannot be empty");
//        } else {
            Intent bundle = new Intent(this, RegisterActivity2.class);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(SURNAME, surname);
            edit.putString(FIRSTNAME, fname);
            edit.putString(GENDER, gdr);
            edit.putString(DATEBIRTH, dtirth);
            //edit.putString(DATEBIRTH,dtb);
//        edit.putString(IMAGES,encodeToBase64(imageViewUser));
            edit.apply();
            startActivity(bundle);
        //}
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
