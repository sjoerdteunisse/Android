package com.axr.sjoerd.android.Presentationlayer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axr.sjoerd.android.R;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    private TextView usernameField;
    private TextView passwordField;
    private Button   loginButton;
    private Button   registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout c = findViewById(R.id.mainActivityLayout);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.finalizeRegisterButton);
        registerButton = findViewById(R.id.finalizeRegisterButton);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        animationDrawable = (AnimationDrawable)c.getBackground();
        animationDrawable.setEnterFadeDuration(0);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
