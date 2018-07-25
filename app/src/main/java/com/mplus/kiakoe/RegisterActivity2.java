package com.mplus.kiakoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity2 extends AppCompatActivity {

    public final static String USERNAME = "username";
    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";
    public final static String PASSWORD_CONFIRM = "password_confirm";
    public final static String PASSWORD_HINT = "password_hint";
    //inisial
    EditText userName,email,password,passwordConfirm,passwordHint;
    CheckBox cbp;
    Button prev2,next2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        userName = (EditText)findViewById(R.id.etUsername);
        email   = (EditText)findViewById(R.id.etEmail);
        password =(EditText)findViewById(R.id.etPassword);
        passwordConfirm = (EditText)findViewById(R.id.etConfirmPassword);
        passwordHint = (EditText)findViewById(R.id.etHintPass);
        prev2 = (Button)findViewById(R.id.btnPrev2);
        prev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next2 = (Button)findViewById(R.id.btnNext2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserTemp2();
            }
        });

        //email

        cbp = (CheckBox)findViewById(R.id.cbPass);
        //password hide show
        cbp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

        passwordHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.equals(password.getText(),passwordConfirm.getText())){
                    Toast.makeText(RegisterActivity2.this, "Password Same", Toast.LENGTH_LONG).show();
                }else{
                    passwordConfirm.setError("Password not same");

                    Toast.makeText(RegisterActivity2.this, "Password not Same", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        userName =(EditText) findViewById(R.id.etUsername);
        email = (EditText)findViewById(R.id.etEmail);
        password =(EditText) findViewById(R.id.etPassword);
        passwordConfirm = (EditText)findViewById(R.id.etConfirmPassword);
        passwordHint = (EditText)findViewById(R.id.etHintPass);

        String ur = userName.getText().toString();
        String em = email.getText().toString().trim();
        String ps = password.getText().toString().trim();
        String pc = passwordConfirm.getText().toString().trim();
        String ph = passwordHint.getText().toString().trim();

        outState.putString(USERNAME, ur);
        outState.putString(EMAIL, em);
        outState.putString(PASSWORD,ps);
        outState.putString(PASSWORD_CONFIRM, pc);
        outState.putString(PASSWORD_HINT, ph);
    }
    private void addUserTemp2(){

        String uname = userName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pas =  password.getText().toString().trim();
        String pac  = passwordConfirm.getText().toString().trim();
        String pah = passwordHint.getText().toString().trim();
        //send value to another activity
        Intent bundle = new Intent(RegisterActivity2.this,RegisterDone.class);
        bundle.putExtra(USERNAME,uname);
        bundle.putExtra(EMAIL,em);
        bundle.putExtra(PASSWORD,pas);
        bundle.putExtra(PASSWORD_CONFIRM,pac);
        bundle.putExtra(PASSWORD_HINT,pah);
        startActivity(bundle);
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
