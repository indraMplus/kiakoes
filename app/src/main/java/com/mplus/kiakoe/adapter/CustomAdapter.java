package com.mplus.kiakoe.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.kiakoe.ListUser;
import com.mplus.kiakoe.Model.User;
import com.mplus.kiakoe.R;
import com.mplus.kiakoe.RegisterActivity;
import com.mplus.kiakoe.UpdateActivity;
import com.mplus.kiakoe.database.SqlDatabase;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> userModelArrayList;
    private SqlDatabase sqlDatabase;

    public CustomAdapter(Context context, ArrayList<User> userModelArrayList) {

        this.context = context;
        this.userModelArrayList = userModelArrayList;
        sqlDatabase = new SqlDatabase(context);
    }


    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_user_item, null, true);
            holder.surname = (TextView) convertView.findViewById(R.id.textViewSureName);
            holder.firstname = (TextView) convertView.findViewById(R.id.textViewFirstName);
            holder.gender = (TextView) convertView.findViewById(R.id.textViewGender);
            holder.datebirth = (TextView) convertView.findViewById(R.id.textViewDateBirth);
            holder.username = (TextView) convertView.findViewById(R.id.textViewUsername);
            holder.email = (TextView) convertView.findViewById(R.id.textViewEmail);
            holder.password = (TextView) convertView.findViewById(R.id.textViewPassword);
            holder.country = (TextView) convertView.findViewById(R.id.textViewCountry);
            holder.constituency = (TextView) convertView.findViewById(R.id.txtCons);
            holder.mobile = (TextView) convertView.findViewById(R.id.textViewMobileNo);
            holder.edituser = convertView.findViewById(R.id.buttonEditUser);
            holder.deleteuser = convertView.findViewById(R.id.buttonDeleteUser);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("user",userModelArrayList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteuser.getTag(position);
        holder.deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        // current activity
                        sqlDatabase.deleteUSer(userModelArrayList.get(position).getId());
                        Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();

                Intent intent = new Intent(context,ListUser.class);
//                    intent.putExtra("user",userModelArrayList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.surname.setText(userModelArrayList.get(position).getSurname());
        holder.firstname.setText(userModelArrayList.get(position).getFirstname());
        holder.gender.setText(userModelArrayList.get(position).getGender());
        holder.datebirth.setText(userModelArrayList.get(position).getDatebirth());
        holder.username.setText(userModelArrayList.get(position).getUsername());
        holder.email.setText(userModelArrayList.get(position).getEmail());
        holder.password.setText(userModelArrayList.get(position).getPassword());
        holder.country.setText(userModelArrayList.get(position).getCountry());
        holder.constituency.setText(userModelArrayList.get(position).getConstituency());
        holder.mobile.setText(userModelArrayList.get(position).getMobileno());

        return convertView;
    }

    private class ViewHolder {
        TextView surname,firstname,gender,datebirth,username,email,password,country,constituency,mobile;
        ImageButton edituser,deleteuser;
    }
}
