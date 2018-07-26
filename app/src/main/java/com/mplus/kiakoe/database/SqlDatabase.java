package com.mplus.kiakoe.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mplus.kiakoe.Model.User;

import java.util.ArrayList;


public class SqlDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kiakoe.db";
    private static final String TABLE_NAME = "user";
    //new2
    private static final String KEY_ID = "id";
    private static final String KEY_SURENAME = "surename";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DATEBIRTH = "datebirth";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PASSWORDCONFIRM = "passwordconfirm";
    private static final String KEY_PASWORDHINT = "passwordhint";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_CONSTITUENCY = "constituency";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_IMAGES = "images";



    //new code
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_NAME + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_SURENAME + " TEXT,"
            + KEY_FIRSTNAME + " TEXT, "
            + KEY_GENDER + " TEXT,"
            + KEY_DATEBIRTH + " TEXT, "
            + KEY_USERNAME + " TEXT,"
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_PASSWORDCONFIRM + " TEXT,"
            + KEY_PASWORDHINT + " TEXT, "
            + KEY_COUNTRY + " TEXT,"
            + KEY_CONSTITUENCY + " TEXT, "
            + KEY_MOBILE + " TEXT, "
            + KEY_IMAGES + " BLOB );";

    public SqlDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //NEW
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        //Make default value for data user list
        String sql = "INSERT INTO user (id, surename, firstname, gender, datebirth, username,email,password,passwordconfirm,passwordhint,country,constituency,mobile) VALUES ('1', 'Test', 'Testing', 'Male','00/00/0000','testerusername', 'test@mail.com', '12345', '12345','number','Vanuatu', 'Efate','876645432');";
        db.execSQL(sql);
    }
    //NEW
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }
    //new
    public long addUser(String surename, String firstname, String gender, String datebirth,
                        String username, String email, String password,
                        String passwordconfirm, String passwordhint, String country, String constituency,
                        String mobile, String imageuser) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_SURENAME, surename);
        values.put(KEY_FIRSTNAME, firstname);
        values.put(KEY_GENDER, gender);
        values.put(KEY_DATEBIRTH, datebirth);
        values.put(KEY_USERNAME, username);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_PASSWORDCONFIRM, passwordconfirm);
        values.put(KEY_PASWORDHINT, passwordhint);
        values.put(KEY_COUNTRY, country);
        values.put(KEY_CONSTITUENCY, constituency);
        values.put(KEY_MOBILE, mobile);
//        values.put(KEY_IMAGES, imageuser);
        // insert row in user table

        Log.d("Total :",""+values);
        return db.insert(TABLE_NAME, null, values);


    }

    //new
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
         c.moveToFirst();
         if (c != null) {
                 userModelArrayList.clear();
                 do {
                     User userModel = new User();
                     userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                     userModel.setSurname(c.getString(c.getColumnIndex(KEY_SURENAME)));
                     userModel.setFirstname(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));
                     userModel.setGender(c.getString(c.getColumnIndex(KEY_GENDER)));
                     userModel.setDatebirth(c.getString(c.getColumnIndex(KEY_DATEBIRTH)));
                     userModel.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
                     userModel.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                     userModel.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                     userModel.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY)));
                     userModel.setConstituency(c.getString(c.getColumnIndex(KEY_CONSTITUENCY)));
                     userModel.setMobileno(c.getString(c.getColumnIndex(KEY_MOBILE)));

                     userModelArrayList.add(userModel);
                 } while (c.moveToNext());
         }
        return userModelArrayList;
    }
    //new
    public int updateUser(int id, String surename, String firstname, String gender, String datebirth,
                           String username, String email, String password, String country, String constituency,
                           String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_SURENAME, surename);
        values.put(KEY_FIRSTNAME, firstname);
        values.put(KEY_GENDER, gender);
        values.put(KEY_DATEBIRTH, datebirth);
        values.put(KEY_USERNAME, username);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_COUNTRY, country);
        values.put(KEY_CONSTITUENCY, constituency);
        values.put(KEY_MOBILE, mobile);
        // update row in students table base on students.is value
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteUSer(int id) {
        // delete row in user table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
    public Cursor getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME + "",null);
        return res;
    }
}
