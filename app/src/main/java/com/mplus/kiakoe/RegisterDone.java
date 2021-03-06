package com.mplus.kiakoe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.mplus.kiakoe.database.SqlDatabase;

public class RegisterDone extends AppCompatActivity {

    public final static String PREF = "kiakoepref";
    public final static String LOCATION = "location";
    public final static String CONSTITUENCY = "cons";
    public final static String MOBILENO = "mobileno";
    public static String IMAGES = "imageuser";
    private SqlDatabase databaseHelper;
    Spinner spDone,spCons;
    EditText mobile;
    Button saveSql;
    ImageView imgUserReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_done);

        databaseHelper = new SqlDatabase(this);
        imgUserReg = (ImageView)findViewById(R.id.imgView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterDone.this);
        final String sures = preferences.getString(RegisterActivity.SURNAME,"");
        final String first = preferences.getString(RegisterActivity.FIRSTNAME,"");
        final String gends = preferences.getString(RegisterActivity.GENDER,"");
        final String dates = preferences.getString(RegisterActivity.DATEBIRTH,"");
        final String prevImg = preferences.getString(RegisterActivity.IMAGES,"");
        if( !prevImg.equals(RegisterActivity.IMAGES) ){
            byte[] b = Base64.decode(prevImg, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imgUserReg.setImageBitmap(bitmap);
        }

        Intent intent = getIntent();
        final String usr2 = intent.getStringExtra(RegisterActivity2.USERNAME);
        final String em2 = intent.getStringExtra(RegisterActivity2.EMAIL);
        final String pa2 = intent.getStringExtra(RegisterActivity2.PASSWORD);
        final String pac2 = intent.getStringExtra(RegisterActivity2.PASSWORD_CONFIRM);
        final String pah2 = intent.getStringExtra(RegisterActivity2.PASSWORD_HINT);


        mobile = (EditText)findViewById(R.id.etMobileNo);
        spDone = (Spinner)findViewById(R.id.spLocation);
        saveSql = (Button)findViewById(R.id.btnSaveSql);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_country, R.layout.spinner_item);

        spCons = (Spinner)findViewById(R.id.spCons);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.array_constituency,R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDone.setAdapter(adapter);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCons.setAdapter(adapter2);

        saveSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add into sqlite database
                databaseHelper.addUser(sures,first,gends,dates,usr2,em2,pa2,pac2,pah2,spDone.getSelectedItem().toString(),spCons.getSelectedItem().toString()
                        ,mobile.getText().toString(), prevImg);
                Log.d("aaa","gambar" +prevImg);

                    Intent home = new Intent(RegisterDone.this,MainActivity.class);
                    startActivity(home);
                    Toast.makeText(RegisterDone.this, "Data Inserted", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void addUserDone(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("location",LOCATION);
        editor.putString("const",CONSTITUENCY);
        editor.putString("mobile",MOBILENO);
        editor.apply();
        clearValue();
    }
    //clear all
    private void clearValue(){
        //mobile.setText("");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Attention");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to add more user?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // if this button is clicked, close
                // current activity
                Intent back = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(back);
            }
        })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        Intent back = new Intent(getApplicationContext(),RegisterActivity.class);
                        startActivity(back);
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
