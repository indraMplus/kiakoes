package com.mplus.kiakoe.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.kiakoe.Model.User;
import com.mplus.kiakoe.R;

import java.util.List;
import java.util.Objects;

public class UserAdapter extends ArrayAdapter<User> {

    private Context mCtx;
    private int listLayoutRes;
    private List<User> userList;
    private SQLiteDatabase mDatabase;

    public UserAdapter(Context mCtx, int listLayoutRes, List<User> userList) {
        super(mCtx, listLayoutRes, userList);
        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.userList = userList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);
        //getting user of the specified position
        User userModel = userList.get(position);
        // Set the border of View (ListView Item)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackground(getContext().getDrawable(R.drawable.border_list));
        }

        //getting views
        //TextView textViewID = view.findViewById(R.id.textID);
        TextView textViewSurname = view.findViewById(R.id.textViewSureName);
        TextView textViewFirstName = view.findViewById(R.id.textViewFirstName);
        TextView textViewGender = view.findViewById(R.id.textViewGender);
        TextView textViewDateBirth = view.findViewById(R.id.textViewDateBirth);
        TextView textViewUsername = view.findViewById(R.id.textViewUsername);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        TextView textViewCountry = view.findViewById(R.id.textViewCountry);
        TextView textViewCosnti = view.findViewById(R.id.txtCons);
        TextView textViewMobileno = view.findViewById(R.id.textViewMobileNo);
        //ImageView imageViewUser = view.findViewById(R.id.imgUser);

        //adding data to views

        textViewSurname.setText(userModel.getSurname());
        textViewFirstName.setText(userModel.getFirstname());
        textViewGender.setText(String.valueOf(userModel.getGender()));
        textViewDateBirth.setText(userModel.getDatebirth());
        textViewUsername.setText(userModel.getUsername());
        textViewEmail.setText(userModel.getEmail());
        textViewCountry.setText(String.valueOf(userModel.getCountry()));
        textViewCosnti.setText(userModel.getConstituency());
        textViewMobileno.setText(userModel.getMobileno());
        //imageViewUser.setImageBitmap();

        //we will use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteUser);
        Button buttonEdit = view.findViewById(R.id.buttonEditUser);
        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateUser(userModel);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    //update user
//    private void updateUser(final User userss) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
//
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
//        View view = inflater.inflate(R.layout.dialog_update, null);
//        builder.setView(view);
//
//        final EditText editTextuserame = view.findViewById(R.id.editTextUsername);
//        final EditText editTextsure = view.findViewById(R.id.editTextSurename);
//        final EditText editTextemail = view.findViewById(R.id.editTextEmail);
//        final Spinner spinnerGend = view.findViewById(R.id.spinnerGender);
//        final Spinner spinnerDateBirth = view.findViewById(R.id.spinnerDatebirth);
//        final Spinner spinnerCount = view.findViewById(R.id.spinnerCountry);
//        final Spinner spinnerConst = view.findViewById(R.id.spinnerConstituency);
//        final EditText editTextmobile = view.findViewById(R.id.editTextPhone);
//
//        editTextuserame.setText(userss.getUsername());
//        editTextsure.setText(userss.getSurname());
//        editTextemail.setText(userss.getEmail());
//        editTextmobile.setText(String.valueOf(userss.getMobileno()));
//
//
//        final AlertDialog dialog = builder.create();
//        Objects.requireNonNull(dialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.show();
//        view.findViewById(R.id.buttonEditUser).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String uname = editTextuserame.getText().toString().trim();
//                String surnam = editTextsure.getText().toString().trim();
//                String pone = editTextmobile.getText().toString().trim();
//                String email = editTextemail.getText().toString().trim();
//                String gen = spinnerGend.getSelectedItem().toString();
//                String date = spinnerDateBirth.getSelectedItem().toString();
//                String con = spinnerCount.getSelectedItem().toString();
//                String cons = spinnerConst.getSelectedItem().toString();
//
//                if (uname.isEmpty()) {
//                    editTextuserame.setError("Username can't be blank");
//                    editTextuserame.requestFocus();
//                    return;
//                }
//
//                if (surnam.isEmpty()) {
//                    editTextsure.setError("Surename can't be blank");
//                    editTextsure.requestFocus();
//                    return;
//                }
//                if (email.isEmpty()) {
//                    editTextemail.setError("Surename can't be blank");
//                    editTextemail.requestFocus();
//                    return;
//                }
//                if (pone.isEmpty()) {
//                    editTextmobile.setError("Mobile no can't be blank");
//                    editTextmobile.requestFocus();
//                    return;
//                }
//
//                String sql = "UPDATE user \n" +
//                        "SET SURENAME = ?, \n" +
//                        " GENDER = ?, \n" +
//                        " DATEBIRTH = ?, \n" +
//                        " USERNAME = ?, \n" +
//                        " PHONE = ?, \n" +
//                        " COUNTRY = ?, \n" +
//                        " CONSTITUENCY = ? \n" +
//                        "WHERE id = ?;\n";
//
//                mDatabase.execSQL(sql, new String[]{uname, surnam, email, pone, gen, date, con, cons, String.valueOf(userss.getId())});
//                Toast.makeText(mCtx, "User Updated", Toast.LENGTH_SHORT).show();
//                reloadUserFromDatabase();
//
//                dialog.dismiss();
//            }
//        });
//    }

//    private void reloadUserFromDatabase() {
//        Cursor cursorUpdate = mDatabase.rawQuery("SELECT * FROM user", null);
//        if (cursorUpdate.moveToFirst()) {
//            userList.clear();
//            do {
//                userList.add(new User(
//                        cursorUpdate.getInt(0),
//                        cursorUpdate.getString(1),
//                        cursorUpdate.getString(2),
//                        cursorUpdate.getString(3),
//                        cursorUpdate.getString(4),
//                        cursorUpdate.getString(5),
//                        cursorUpdate.getString(13),
//                        cursorUpdate.getString(11),
//                        cursorUpdate.getString(12),
//                        cursorUpdate.getString(7)
//                ));
//            } while (cursorUpdate.moveToNext());
//        }
//        cursorUpdate.close();
//        notifyDataSetChanged();
}
