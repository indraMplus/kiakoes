package com.mplus.kiakoe;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.mplus.kiakoe.Model.User;
import com.mplus.kiakoe.adapter.CustomAdapter;
import com.mplus.kiakoe.database.SqlDatabase;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListUser extends AppCompatActivity {

    ListView listViewUser;
    ArrayList<User> userModelArrayList;
    CustomAdapter customAdapter;
    SqlDatabase databaseHelper;
    User userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        //customAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        userModel = (User) intent.getSerializableExtra("user");

        listViewUser = (ListView) findViewById(R.id.listViewUser);
        databaseHelper = new SqlDatabase(getApplicationContext());
        //users = new ArrayList<>();
        Button home = (Button)findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backhome = new Intent(ListUser.this,MainActivity.class);
                startActivity(backhome);
            }
        });
        final Button export = (Button)findViewById(R.id.btnExport);
        export.setOnClickListener(new View.OnClickListener() {
            //new test
            SQLiteDatabase sqldb = databaseHelper.getReadableDatabase();
            Cursor c = null;
            //param date
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            @Override
            public void onClick(final View v) {
                //parameter for make file path
                try {
                    c = sqldb.rawQuery("select surename,firstname,gender,datebirth,username,email,password,passwordconfirm,passwordhint,country,constituency,mobile from user", null);
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = new File(Environment.getExternalStorageDirectory().getPath() + "/Backup/");
                    String filename = date +"_kiakoe.csv";

                    if (!sdCardDir.exists()) {
                    sdCardDir.mkdirs();
                    }
                    // the name of the file to export with
                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);

                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();
                    if (rowcount > 0) {
                        c.moveToFirst();

                        for (int i = 0; i < colcount; i++) {
                            if (i != colcount - 1) {

                                bw.write(c.getColumnName(i) + ",");
                            } else {

                                bw.write(c.getColumnName(i));
                            }
                        }
                        bw.newLine();

                        for (int i = 0; i < rowcount; i++) {
                            c.moveToPosition(i);

                            for (int j = 0; j < colcount; j++) {
                                if (j != colcount - 1)
                                    bw.write(c.getString(j) + ",");
                                else
                                    bw.write(c.getString(j));
                            }
                            bw.newLine();
                        }
                        bw.flush();
                        Toast.makeText(getApplicationContext(),"YOUR FILE HAS BEEN EXPORTED",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (sqldb.isOpen()) {
                        sqldb.close();
                        //infotext.setText(ex.getMessage().toString());
                    }
                }
            }
        });
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
        customAdapter.notifyDataSetChanged();
        listViewUser.setAdapter(customAdapter);
        Log.d("list","total list" +userModelArrayList);

        //customAdapter.notifyDataSetChanged();
    }
}