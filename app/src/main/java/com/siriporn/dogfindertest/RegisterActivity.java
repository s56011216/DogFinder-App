package com.siriporn.dogfindertest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;
import com.siriporn.dogfindertest.Models.User;

public class RegisterActivity extends AppCompatActivity {

    User profile;
    EditText email, password, confirmPassword, firstname, lastname, tel, address, facebookname, lineid;
    String tEmail, tPassword, tFirstname, tLastname, tTel, tAddress, tFacebook, tLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        tel = (EditText) findViewById(R.id.telNumber);
        address = (EditText) findViewById(R.id.address);
        facebookname = (EditText) findViewById(R.id.facebookName);
        lineid = (EditText) findViewById(R.id.lineID);


    }

    public void onClickRegister(View v) {
        boolean isPasswordMatch = password.getText().toString().equals(confirmPassword.getText().toString());

        if (isPasswordMatch) {
            tEmail = email.getText().toString();
            tPassword = password.getText().toString();
            tFirstname = firstname.getText().toString();
            tLastname = lastname.getText().toString();
            tTel = tel.getText().toString();
            tAddress = address.getText().toString();
            tFacebook = facebookname.getText().toString();
            tLine = lineid.getText().toString();
            profile = new User(tEmail, tPassword, tFirstname, tLastname, tTel, tAddress, tFacebook, tLine);
        } else {
            AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
            dialog.setTitle("Password is not match!");
            dialog.setMessage("Please re-type your password");
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();


        }

    }

    public void onClickCancel(View v){

        onBackPressed();
    }
}
