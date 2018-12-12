package com.axr.sjoerd.android.Presentationlayer;

import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axr.sjoerd.android.Domainlayer.RegisterAccount;
import com.axr.sjoerd.android.R;
import com.axr.sjoerd.android.Service.LocalEncryption;

public class RegisterActivity extends AppCompatActivity {


    private AnimationDrawable animationDrawable;

    private TextView firstnameField;
    private TextView lastnameField;
    private TextView emailField;
    private TextView passwordField;
    private CheckBox confirmAccount;

    private Button  finalizeRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ConstraintLayout c = findViewById(R.id.registerLayout);



        firstnameField = findViewById(R.id.firstnameField);
        lastnameField = findViewById(R.id.lastnameField);
        emailField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        confirmAccount = findViewById(R.id.confirmAgree);
        finalizeRegisterButton = findViewById(R.id.finalizeRegisterButton);


        confirmAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                finalizeRegisterButton.setEnabled(isChecked);
            }
        });


        finalizeRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccount registerAccount = new RegisterAccount(firstnameField.getText().toString(),
                        lastnameField.getText().toString(),
                        emailField.getText().toString(),
                        passwordField.getText().toString());


//                ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
//                mDialog.setMessage("Please wait...");
//                mDialog.show();


                ;
                Log.d("Encryption run", "onClick: ENC" + LocalEncryption.encrypt(registerAccount.getPassword()));
                Toast.makeText(RegisterActivity.this, LocalEncryption.encrypt("TESTTTT"), Toast.LENGTH_LONG).show();
            }
        });

        animationDrawable = (AnimationDrawable)c.getBackground();
        animationDrawable.setEnterFadeDuration(0);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }



}
