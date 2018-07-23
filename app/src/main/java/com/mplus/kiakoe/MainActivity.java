package com.mplus.kiakoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_new_user:
//                    //launch register activity
//                    Intent nu = new Intent(getApplicationContext(),RegisterActivity.class);
//                    startActivity(nu);
//
//                    return true;
//                case R.id.navigation_list_user:
//                    //launch list activity
//                    Intent lu = new Intent(getApplicationContext(),ListUser.class);
//                    startActivity(lu);
//                    return true;
//            }
//            return false;
//        }
//    };

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
}
