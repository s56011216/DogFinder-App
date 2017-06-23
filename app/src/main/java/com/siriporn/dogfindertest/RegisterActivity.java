package com.siriporn.dogfindertest;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.siriporn.dogfindertest.Models.User;

public class RegisterActivity extends AppCompatActivity {

    User profile;
    EditText email, password, confirmPassword, name, tel, tel2, address, facebookname, lineid;
    String tEmail, tPassword, tConfirmPassword, tName, tTel, tTel2, tAddress, tFacebook, tLine;

    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        res = getResources();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        name = (EditText) findViewById(R.id.name);

        tel = (EditText) findViewById(R.id.telNumber);
        tel2 = (EditText) findViewById(R.id.telNumber2);
        address = (EditText) findViewById(R.id.address);
        facebookname = (EditText) findViewById(R.id.facebookName);
        lineid = (EditText) findViewById(R.id.lineID);


    }

    public void onClickRegister(View v) {

        tEmail = email.getText().toString();
        tPassword = password.getText().toString();
        tConfirmPassword = confirmPassword.getText().toString();
        tName = name.getText().toString();
        tTel = tel.getText().toString();
        tTel2 = tel2.getText().toString();
        tAddress = address.getText().toString();
        tFacebook = facebookname.getText().toString();
        tLine = lineid.getText().toString();

        boolean isAllFiled = !(tEmail.isEmpty()
                | tPassword.isEmpty()
                | tConfirmPassword.isEmpty()
                | tName.isEmpty()
                | tTel.isEmpty()
                | tTel2.isEmpty()
                | tAddress.isEmpty()
                | tFacebook.isEmpty()
                | tLine.isEmpty());

        boolean isPasswordMatch = tPassword.equals(tConfirmPassword);
        boolean isPasswordMoreThan24Char = tPassword.length() > 24;
        boolean isPasswordLessThan8Char = tPassword.length() < 8;

        boolean isPasswordRight = isPasswordMatch | !isPasswordLessThan8Char | !isPasswordMoreThan24Char;

        AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        if (isAllFiled) {
            if (isPasswordRight){
                profile = new User(tEmail, tPassword, tName, tTel, tTel2, tAddress, tFacebook, tLine);
                dialog.setTitle(res.getString(R.string.activity_register_alert_regis_success_title));
                dialog.setMessage(res.getString(R.string.activity_register_alert_regis_success));
            }else{
                dialog.setTitle(res.getString(R.string.activity_register_alert_password_not_match_title));
                dialog.setMessage(res.getString(R.string.activity_register_alert_password_not_match));
            }

        } else {
            dialog.setTitle(res.getString(R.string.activity_register_alert_fill_all_title));
            dialog.setMessage(res.getString(R.string.activity_register_alert_fill_all));
        }
        dialog.show();
    }

    public void onClickCancel(View v) {
        onBackPressed();
    }
}
