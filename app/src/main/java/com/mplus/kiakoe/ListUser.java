package com.mplus.kiakoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.mplus.kiakoe.Model.User;
import com.mplus.kiakoe.adapter.CustomAdapter;
import com.mplus.kiakoe.adapter.UserAdapter;
import com.mplus.kiakoe.database.SqlDatabase;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


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
            @Override
            public void onClick(final View v) {
//                String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
//                File file = new File(directory_path);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                // Export SQLite DB as EXCEL FILE
//                SQLiteToExcel sqliteToExcel = new SQLiteToExcel(ListUser.this, databaseHelper.getDatabaseName(), directory_path);
//                sqliteToExcel.exportSingleTable("user","users.xls", new SQLiteToExcel.ExportListener() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onCompleted(String filePath) {
//                    Toast.makeText(ListUser.this,"YOUR FILE HAS BEEN EXPORTED",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        //Toast.makeText(ListUser.this,"YOUR FILE HAS BEEN FAILED TO EXPORTED",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                //Toast.makeText(ListUser.this,"Is under Programming",Toast.LENGTH_SHORT).show();


                try {

                    c = sqldb.rawQuery("select * from user", null);
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = new File(Environment.getExternalStorageDirectory().getPath() + "/Backup/");
                    String filename = "kiakoe_userslist.csv";

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
    private void exportFile() throws WriteException {
        final Cursor cursor = databaseHelper.getUser();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "UserData.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "Surename"));
            sheet.addCell(new Label(1, 1, "firstname"));
            sheet.addCell(new Label(2, 2, "Gender"));
            sheet.addCell(new Label(3, 3, "Date of Birth"));
            sheet.addCell(new Label(4, 4, "Username"));
            sheet.addCell(new Label(5, 5, "Email"));
            sheet.addCell(new Label(6, 6, "Password"));
            sheet.addCell(new Label(7, 7, "Password Confirm"));
            sheet.addCell(new Label(8, 8, "Password Hint"));
            sheet.addCell(new Label(9, 9, "Country"));
            sheet.addCell(new Label(10, 10, "Constituency"));
            sheet.addCell(new Label(11, 11, "Mobile No"));

            if (cursor.moveToFirst()) {
                do {
                    String sure = cursor.getString(cursor.getColumnIndex("surename"));
                    String first = cursor.getString(cursor.getColumnIndex("firstname"));
                    String gend = cursor.getString(cursor.getColumnIndex("gend"));
                    String date = cursor.getString(cursor.getColumnIndex("datebirth"));
                    String uname = cursor.getString(cursor.getColumnIndex("username"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    String pass = cursor.getString(cursor.getColumnIndex("password"));
                    String passconf = cursor.getString(cursor.getColumnIndex("passwordconfirm"));
                    String passhint = cursor.getString(cursor.getColumnIndex("passwordhint"));
                    String country = cursor.getString(cursor.getColumnIndex("country"));
                    String consti = cursor.getString(cursor.getColumnIndex("constituency"));
                    String mobile = cursor.getString(cursor.getColumnIndex("mobile"));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, sure));
                    sheet.addCell(new Label(1, i, first));
                    sheet.addCell(new Label(2, i, gend));
                    sheet.addCell(new Label(3, i, date));
                    sheet.addCell(new Label(4, i, uname));
                    sheet.addCell(new Label(5, i, email));
                    sheet.addCell(new Label(6, i, pass));
                    sheet.addCell(new Label(7, i, passconf));
                    sheet.addCell(new Label(8, i, passhint));
                    sheet.addCell(new Label(9, i, country));
                    sheet.addCell(new Label(10, i, consti));
                    sheet.addCell(new Label(11, i, mobile));

                } while (cursor.moveToNext());
            }

            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(),
                    "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            e.printStackTrace();
        }
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

