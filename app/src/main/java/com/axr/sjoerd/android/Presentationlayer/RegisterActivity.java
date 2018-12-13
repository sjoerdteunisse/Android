package com.axr.sjoerd.android.Presentationlayer;

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

import com.axr.sjoerd.android.Domainlayer.RegisterAccount;
import com.axr.sjoerd.android.R;
import com.axr.sjoerd.android.Service.ApiRequest;
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
        finalizeRegisterButton = findViewById(R.id.finalizeRegister);


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
                    Toast.makeText(RegisterActivity.this, "Email adress invalid", Toast.LENGTH_LONG).show();
                    return;
                }

                RegisterAccount registerAccount = new RegisterAccount(firstnameField.getText().toString(),
                        lastnameField.getText().toString(),
                        emailField.getText().toString(),
                        passwordField.getText().toString());

                ApiRequest apiRequest = new ApiRequest(RegisterActivity.this);
                apiRequest.register(registerAccount);

//                Log.d("Encryption run", "onClick: ENC" + LocalEncryption.encrypt(registerAccount.getPassword()));
//                Toast.makeText(RegisterActivity.this, LocalEncryption.encrypt("TESTTTT"), Toast.LENGTH_LONG).show();
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

}
