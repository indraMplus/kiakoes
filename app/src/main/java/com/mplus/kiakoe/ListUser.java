package com.mplus.kiakoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mplus.kiakoe.Model.User;
import com.mplus.kiakoe.adapter.CustomAdapter;
import com.mplus.kiakoe.adapter.UserAdapter;
import com.mplus.kiakoe.database.SqlDatabase;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class ListUser extends AppCompatActivity {

    //List<User> users;
//    SQLiteDatabase mDatabase;
    ListView listViewUser;
    //UserAdapter adapter;
//    Context mCtx;
    private ArrayList<User> userModelArrayList;
    CustomAdapter customAdapter;
    SqlDatabase databaseHelper;
    User userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        Intent intent = getIntent();
        userModel = (User) intent.getSerializableExtra("user");

        listViewUser = (ListView) findViewById(R.id.listViewUser);
        databaseHelper = new SqlDatabase(this);
        //users = new ArrayList<>();

        //opening the database
        //mDatabase = openOrCreateDatabase(SqlDatabase.DATABASE_NAME, MODE_PRIVATE, null);

        //new
        userModelArrayList = databaseHelper.getAllUsers();
        //this method will display the employees in the list
        //showUserFromDatabase();


        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListUser.this, UpdateActivity.class);
                intent.putExtra("user", userModelArrayList.get(position));
                startActivity(intent);
            }
        });
        //new
        customAdapter = new CustomAdapter(this, userModelArrayList);
        //customAdapter.notifyDataSetChanged();
        listViewUser.setAdapter(customAdapter);
    }
}


//    private void showUserFromDatabase() {
//        //we used rawQuery(sql, selectionargs) for fetching all the user
//        Cursor cursorUser = mDatabase.rawQuery("SELECT * FROM user", null);
//
//        //if the cursor has some data
//        if (cursorUser.moveToFirst()) {
//            //looping through all the records
//
//            do {
//                //pushing each record in the user list
//                users.add(new User(
//                        cursorUser.getInt(0),
//                        cursorUser.getString(1),
//                        cursorUser.getString(2),
//                        cursorUser.getString(3),
//                        cursorUser.getString(4),
//                        cursorUser.getString(5),
//                        cursorUser.getString(13),
//                        cursorUser.getString(11),
//                        cursorUser.getString(12),
//                        cursorUser.getString(7)
//                ));
//            } while (cursorUser.moveToNext());
//        }
//        //closing the cursor
//        cursorUser.close();
//
//        //creating the adapter object
//        adapter = new UserAdapter(getApplicationContext(), R.layout.list_user_item, users);
//
//        //adding the adapter to listview
//        listViewUser.setAdapter(adapter);
//    }

