package com.mplus.kiakoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class RegisterActivity2 extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_prev2:

                    Intent re2 = new Intent(RegisterActivity2.this,RegisterActivity.class);
                    startActivity(re2);

                    return true;
                case R.id.navigation_next2:

                    Intent nu3 = new Intent(RegisterActivity2.this,RegisterDone.class);
                    startActivity(nu3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationReg2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
