package com.mplus.kiakoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mTexList = (TextView) findViewById(R.id.tvList);
        Button newUser = (Button) findViewById(R.id.btnNewUser);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nu = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(nu);
            }
        });
        mTexList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lu = new Intent(getApplicationContext(),ListUser.class);
                startActivity(lu);
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            outApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void outApp(){
        //mobile.setText("");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Attention");
        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure to leave before add user?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
