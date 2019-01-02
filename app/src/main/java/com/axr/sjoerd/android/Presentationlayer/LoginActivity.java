package com.axr.sjoerd.android.Presentationlayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axr.sjoerd.android.Applicationlayer.AuthorizationCallback;
import com.axr.sjoerd.android.R;
import com.axr.sjoerd.android.Service.ApiRequest;

public class LoginActivity extends AppCompatActivity implements AuthorizationCallback {

    private TextView usernameField;
    private TextView passwordField;
    private Button   loginButton;
    private Button   registerButton;
    private TextView statusMessage;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        CheckToken();

        ConstraintLayout c = findViewById(R.id.mainActivityLayout);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        statusMessage = findViewById(R.id.statusMessage);

        Log.i("Before", "onClick: ");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequest api = new ApiRequest(LoginActivity.this);
                 api.login(usernameField.getText().toString(), passwordField.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        animationDrawable = (AnimationDrawable)c.getBackground();
        animationDrawable.setEnterFadeDuration(0);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    private void CheckToken() {
        ApiRequest apiRequest = new ApiRequest(this);
        apiRequest.me();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void HandleResponse() {
        Intent listViewIntent = new Intent(LoginActivity.this, ListView.class);
        startActivity(listViewIntent);
    }
}
