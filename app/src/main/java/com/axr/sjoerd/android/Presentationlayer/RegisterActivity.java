package com.axr.sjoerd.android.Presentationlayer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.axr.sjoerd.android.Applicationlayer.AuthorizationCallback;
import com.axr.sjoerd.android.Domainlayer.RegisterAccount;
import com.axr.sjoerd.android.R;
import com.axr.sjoerd.android.Service.ApiRequest;

public class RegisterActivity extends AppCompatActivity implements AuthorizationCallback {


    private AnimationDrawable animationDrawable;

    private TextView firstnameField;
    private TextView lastnameField;
    private TextView emailField;
    private TextView passwordField;
    private TextView statusMessage;
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
        finalizeRegisterButton = findViewById(R.id.finalizeRegister);
        statusMessage = findViewById(R.id.statusMessage);


        confirmAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    finalizeRegisterButton.setEnabled(isChecked);
            }
        });


        finalizeRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isValidEmail(emailField.getText())) {
                    statusMessage.setText("Email address invalid");
                    return;
                }

                RegisterAccount registerAccount = new RegisterAccount(firstnameField.getText().toString(),
                        lastnameField.getText().toString(),
                        emailField.getText().toString(),
                        passwordField.getText().toString());

                ApiRequest apiRequest = new ApiRequest(RegisterActivity.this);
                apiRequest.register(registerAccount);
            }
        });

        animationDrawable = (AnimationDrawable)c.getBackground();
        animationDrawable.setEnterFadeDuration(0);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void HandleResponse() {
        Intent listViewIntent = new Intent(RegisterActivity.this, ListView.class);
        startActivity(listViewIntent);
    }
}
