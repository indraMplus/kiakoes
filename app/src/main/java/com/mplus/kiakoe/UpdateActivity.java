package com.mplus.kiakoe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mplus.kiakoe.Model.User;
import com.mplus.kiakoe.database.SqlDatabase;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    //inisial update
    private User userModel;
    private EditText etsure;
    private EditText etuser;
    private EditText etemail;
    private EditText etphone;
    private EditText etDatebirth;
    private EditText etPass;
    private EditText etfirst;
    private Spinner spGend,spConst,spCon;
    Button btnupdate;
    ImageButton  btndelete;
    private SqlDatabase databaseHelper;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        userModel = (User) intent.getSerializableExtra("user");

        databaseHelper = new SqlDatabase(this);

        etsure = (EditText) findViewById(R.id.editTextSurename);
        etuser = (EditText) findViewById(R.id.editTextUsername);
        etemail = (EditText) findViewById(R.id.editTextEmail);
        etPass = (EditText)findViewById(R.id.editTextpassword);
        etphone = (EditText) findViewById(R.id.editTextPhone);
        etfirst = (EditText) findViewById(R.id.editTextFirstname);
        etDatebirth = (EditText) findViewById(R.id.editTextBirth);
        spGend = (Spinner)findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_gen, R.layout.spinner_item);
        spGend.setAdapter(adapter);
        etDatebirth.setOnClickListener(this);
        spCon = (Spinner)findViewById(R.id.spinnerCountry);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.array_country, R.layout.spinner_item);
        spCon.setAdapter(adapter3);

        spConst = (Spinner)findViewById(R.id.spinnerConstituency);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.array_constituency, R.layout.spinner_item);
        spConst.setAdapter(adapter4);

        btndelete = (ImageButton) findViewById(R.id.buttonDeleteUser);
        btnupdate = (Button) findViewById(R.id.buttonUpdateUser);

        etsure.setText(userModel.getSurname());
        etuser.setText(userModel.getUsername());
        etfirst.setText(userModel.getFirstname());
        etemail.setText(userModel.getEmail());
        etPass.setText(userModel.getPassword());
        etphone.setText(userModel.getMobileno());
        etDatebirth.setText(userModel.getDatebirth());

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateUser(userModel.getId(),etsure.getText().toString(),etphone.getText().toString()
                        ,etuser.getText().toString()
                        ,etemail.getText().toString(),etPass.getText().toString(),etphone.getText().toString()
                        ,spGend.getSelectedItem().toString(),etDatebirth.getText().toString()
                        ,spCon.getSelectedItem().toString(),spConst.getSelectedItem().toString());
                Toast.makeText(UpdateActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UpdateActivity.this,ListUser.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v){
        if(v == etDatebirth){
            //get Curent Date
            final Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            etDatebirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
