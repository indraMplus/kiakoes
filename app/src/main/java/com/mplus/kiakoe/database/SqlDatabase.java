package com.mplus.kiakoe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mplus.kiakoe.Model.User;

import java.util.ArrayList;


public class SqlDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kiakoe.db";
    private static final String TABLE_NAME = "user";
    //private static final String COL_1 = "ID";
//    private static final String COL_2 = "SURENAME";
//    private static final String COL_3 = "FIRSTNAME";
//    private static final String COL_4 = "GENDER";
//    private static final String COL_5 = "DATEBIRTH";
//    private static final String COL_6 = "USERNAME";
//    private static final String COL_7 = "EMAIL";
//    private static final String COL_8 = "EMAILCONFIRM";
//    private static final String COL_9 = "PASSWORD";
//    private static final String COL_10 = "PASSWORDCONFIRM";
//    private static final String COL_11 = "PASSWORDHINT";
//    private static final String COL_12 = "COUNTRY";
//    private static final String COL_13 = "CONSTITUENCY";
//    private static final String COL_14 = "MOBILE";
//    private static final String COL_15 = "IMAGES";
    //new code
//    private static final String KEY_ID = "ID";
//    private static final String KEY_SURENAME = "SURENAME";
//    private static final String KEY_FIRSTNAME = "FIRSTNAME";
//    private static final String KEY_GENDER = "GENDER";
//    private static final String KEY_DATEBIRTH = "DATEBIRTH";
//    private static final String KEY_USERNAME = "USERNAME";
//    private static final String KEY_EMAIL = "EMAIL";
//    private static final String KEY_EMAILCONFIRM = "EMAILCONFIRM";
//    private static final String KEY_PASSWORD = "PASSWORD";
//    private static final String KEY_PASSWORDCONFIRM = "PASSWORDCONFIRM";
//    private static final String KEY_PASWORDHINT = "PASSWORDHINT";
//    private static final String KEY_COUNTRY = "COUNTRY";
//    private static final String KEY_CONSTITUENCY = "CONSTITUENCY";
//    private static final String KEY_MOBILE = "MOBILE";
//    private static final String KEY_IMAGES = "IMAGES";
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
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SURENAME TEXT,FIRSTNAME TEXT,GENDER TEXT,DATEBIRTH TEXT,USERNAME TEXT,EMAIL TEXT,EMAILCONFIRM TEXT,PASSWORD TEXT,PASSWORDCONFIRM TEXT,PASSWORDHINT TEXT,COUNTRY TEXT,CONSTITUENCY TEXT,MOBILE TEXT,IMAGES BLOB)");
//    }
    //NEW
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
//        onCreate(db);
//    }
    //NEW
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }
    //new
    public boolean addUser(String surename, String firstname, String gender, String datebirth,
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
        values.put(KEY_IMAGES, imageuser);
        // insert row in students table
        long insert = db.insert(TABLE_NAME, null, values);

        return insert != -1;
    }

//    public boolean insertData(String sure, String first
//            , String gend, String datebirth, String user, String email, String emailConfirm, String password, String passwordConfrim, String passwordhint,
//                              String country, String constituency, String mobileno, String imageuser) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2,sure);
//        contentValues.put(COL_3,first);
//        contentValues.put(COL_4,gend);
//        contentValues.put(COL_5,datebirth);
//        contentValues.put(COL_6,user);
//        contentValues.put(COL_7,email);
//        contentValues.put(COL_8,emailConfirm);
//        contentValues.put(COL_9,password);
//        contentValues.put(COL_10,passwordConfrim);
//        contentValues.put(COL_11,passwordhint);
//        contentValues.put(COL_12,country);
//        contentValues.put(COL_13,constituency);
//        contentValues.put(COL_14,mobileno);
//        contentValues.put(COL_15,imageuser);
//        long result = db.insert(TABLE_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

    //new
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userModelArrayList = new ArrayList<User>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
         if (c.moveToFirst()) {
                 //userModelArrayList.clear();
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
                 c.close();
         }
        return userModelArrayList;
    }

//    public Cursor getAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
//        return res;
//    }
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
